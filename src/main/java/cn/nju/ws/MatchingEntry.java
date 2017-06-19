package cn.nju.ws;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import static cn.nju.ws.utility.eval.Metrics.calMetrics;
import static cn.nju.ws.utility.nlp.FormatData.getStopWords;
import static cn.nju.ws.utility.fileParser.AlignFileParser.parseAlignFile;
import static cn.nju.ws.utility.finder.AlignmentFinder.findResultAlignWithoutThread;
import static cn.nju.ws.utility.fileWriter.FileWriter.printToFile;
import static cn.nju.ws.utility.finder.InfoGainCalculator.calInfoGainWithoutThread;
import static cn.nju.ws.utility.fileParser.InstFileParser.parseInstFile;
import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.finder.PredPairFinder.findPredPairWithoutThread;

/**
 * Created by ciferlv on 17-6-6.
 */
public class MatchingEntry {

    private static Logger logger = LoggerFactory.getLogger(MatchingEntry.class);

    public static void init() {

        getStopWords();
    }

    public static void main(String[] args) throws FileNotFoundException {

        init();

        Set<String> tarType = new HashSet<>();
        tarType.add("http://erlangen-crm.org/efrbroo/f22_self-contained_expression");

        doc1.setTarType(tarType);
        doc2.setTarType(tarType);

        parseAlignFile("src/main/resources/DOREMUS/FPT/refalign.rdf", refAlign);

        Model model1 = ModelFactory.createDefaultModel();
        Model model2 = ModelFactory.createDefaultModel();
        parseInstFile("src/main/resources/DOREMUS/FPT/source.ttl", doc1, model1);
        parseInstFile("src/main/resources/DOREMUS/FPT/target.ttl", doc2, model2);
        doc1.processGraph();
        doc2.processGraph();

        printToFile("./Inst1.txt", doc1.graphToString());
        printToFile("./Inst2.txt", doc2.graphToString());

        refAlign.generatePositives();
        refAlign.generateNegetives();

        findPredPairWithoutThread();

        calInfoGainWithoutThread();

        printToFile("./PredPair2.txt", ppl.toString());
        findResultAlignWithoutThread();
        calMetrics();

        printToFile("./InstComp.txt", alignsStr);
    }

}
