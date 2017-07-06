package cn.nju.ws;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.eval.Metrics.calMetrics;
//import static cn.nju.ws.utility.fileParser.AlignFileParser.parseAlignFile;
import static cn.nju.ws.utility.fileParser.InstFileApacheJenaParser.parseInstFileByApacheJena;
import static cn.nju.ws.utility.assistanceTool.FileWriter.printToFile;
import static cn.nju.ws.utility.finder.AlignmentFinder.findResultAlignWithoutThread;
import static cn.nju.ws.utility.finder.InfoGainCalculator.calInfoGainWithoutThread;
import static cn.nju.ws.utility.finder.PredPairFinder.findPredPairWithoutThread;
import static cn.nju.ws.utility.nlp.FormatData.getStopWords;

/**
 * Created by ciferlv on 17-6-21.
 */
class Object{

    String value;
    String type;
    String languate = null;
}
public class njuLinkMatcher {

    private static Logger logger = LoggerFactory.getLogger(njuLinkMatcher.class);

    private void init() throws IOException {

        Properties pro = new Properties();
        FileInputStream in = new FileInputStream("njuLink.properties");
        pro.load(in);
        in.close();

        refPath = pro.getProperty("refPath");
        alignHead = pro.getProperty("alignHead");
        alignTail = pro.getProperty("alignTail");
        infoGainThreshold = Double.parseDouble(pro.getProperty("infoGainThreshold"));
        predPairSimiThreshold = Double.parseDouble(pro.getProperty("predPairSimiThreshold"));
        predPairNumNeededThreshold = Integer.parseInt(pro.getProperty("predPairNumNeededThreshold"));

        String[] souClassFilter = pro.getProperty("souClassFilter").split(";");
        for (int i = 0; i < souClassFilter.length; i++) {

            souClassFilterSet.add(souClassFilter[i]);
        }

        String[] tarClassFilter = pro.getProperty("tarClassFilter").split(";");
        for (int i = 0; i < tarClassFilter.length; i++) {

            tarClassFilterSet.add(tarClassFilter[i]);
        }

        useReinforce = Boolean.parseBoolean(pro.getProperty("useReinforce"));
        useAverageSimi = Boolean.parseBoolean(pro.getProperty("useAverageSimi"));

        getStopWords();
    }


    public String align(URI sourceURI, URI targetURI) throws IOException {

        logger.info("njuLink version 3");
        init();

        alignBuffer.append(alignHead);

        tarPath = targetURI.getPath();
        souPath = sourceURI.getPath();

        souDoc.setTarType(souClassFilterSet);
        tarDoc.setTarType(tarClassFilterSet);

        Model souModel = null, tarModel = null;
        try {
            souModel = ModelFactory.createDefaultModel();
            tarModel = ModelFactory.createDefaultModel();
        } catch (Exception e) {

            logger.info(e.getMessage());
        }

        if (souModel == null) {

            logger.info("souModel is null");
        }

        parseInstFileByApacheJena(souPath, souDoc, souModel);
        parseInstFileByApacheJena(tarPath, tarDoc, tarModel);

        souDoc.processGraph();
        tarDoc.processGraph();

//        parseAlignFile(refPath, refAlign);
        refAlign.generatePositives();
        refAlign.generateNegetives();

        findPredPairWithoutThread();
        calInfoGainWithoutThread();
        findResultAlignWithoutThread();

        alignBuffer.append(alignTail);

        return String.valueOf(alignBuffer);
    }

    public static void main(String[] args) {

        njuLinkMatcher nlm = new njuLinkMatcher();

        URI sourceURI = URI.create("./DOREMUS/FPT/source.ttl");
        URI targetURI = URI.create("./DOREMUS/FPT/target.ttl");

        String res = "";

        try {

            res = nlm.align(sourceURI, targetURI);
        } catch (IOException e) {
            e.printStackTrace();
        }

        calMetrics();


        try {

            File dir = new File("./result");
            if (!dir.exists()) dir.mkdir();

            printToFile("./result/PredPair.txt", ppl.toString());
            printToFile("./result/InstComp.txt", alignsStr);
            printToFile("./result/Source.txt", souDoc.graphToString());
            printToFile("./result/Target.txt", tarDoc.graphToString());
            printToFile("./result/RefAlign.txt", refAlign.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
