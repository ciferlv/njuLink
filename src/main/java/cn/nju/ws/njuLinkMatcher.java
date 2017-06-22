package cn.nju.ws;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.fileParser.AlignFileParser.parseAlignFile;
import static cn.nju.ws.utility.fileParser.InstFileParser.parseInstFile;
import static cn.nju.ws.utility.finder.AlignmentFinder.findResultAlignWithoutThread;
import static cn.nju.ws.utility.finder.InfoGainCalculator.calInfoGainWithoutThread;
import static cn.nju.ws.utility.finder.PredPairFinder.findPredPairWithoutThread;
import static cn.nju.ws.utility.nlp.FormatData.getStopWords;

/**
 * Created by ciferlv on 17-6-21.
 */
public class njuLinkMatcher {

    private static Logger logger = LoggerFactory.getLogger(njuLinkMatcher.class);

    private  void init() throws IOException {

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

    private  void getRefAlign() {

        parseAlignFile(refPath, refAlign);

    }

    private  void getSouAndTar() {

        souDoc.setTarType(souClassFilterSet);
        tarDoc.setTarType(tarClassFilterSet);

        Model souModel = ModelFactory.createDefaultModel();
        Model tarModel = ModelFactory.createDefaultModel();
        parseInstFile(souPath, souDoc, souModel);
        parseInstFile(tarPath, tarDoc, tarModel);

        souDoc.processGraph();
        tarDoc.processGraph();


    }

    private  void getPosAndNeg() {

        refAlign.generatePositives();
        refAlign.generateNegetives();
    }

    private  void getResAlign() {

        findPredPairWithoutThread();

        calInfoGainWithoutThread();

        findResultAlignWithoutThread();
    }

    public  String align(URI sourceURI, URI targetURI) throws IOException {

        init();

        alignBuffer.append(alignHead);

        tarPath = targetURI.getPath();
        souPath = sourceURI.getPath();

        getRefAlign();

        getSouAndTar();

        getPosAndNeg();

        getResAlign();

        alignBuffer.append(alignTail);

        return String.valueOf(alignBuffer);
    }
}
