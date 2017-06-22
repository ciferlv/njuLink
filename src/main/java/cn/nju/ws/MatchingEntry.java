package cn.nju.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

import static cn.nju.ws.utility.nlp.FormatData.getStopWords;

/**
 * Created by ciferlv on 17-6-6.
 */
public class MatchingEntry {

    private static Logger logger = LoggerFactory.getLogger(MatchingEntry.class);

    public static void init() {

        getStopWords();
    }

    public static void main(String[] args) throws IOException {

//        init();
//
//        Set<String> tarType = new HashSet<>();
//        tarType.add("http://erlangen-crm.org/efrbroo/f22_self-contained_expression");
//
//        souDoc.setTarType(tarType);
//        tarDoc.setTarType(tarType);
//
//        parseAlignFile("DOREMUS/FPT/refalign.rdf", refAlign);
//
//        Model model1 = ModelFactory.createDefaultModel();
//        Model model2 = ModelFactory.createDefaultModel();
//        parseInstFile("src/main/resources/DOREMUS/FPT/source.ttl", souDoc, model1);
//        parseInstFile("src/main/resources/DOREMUS/FPT/target.ttl", tarDoc, model2);
//        souDoc.processGraph();
//        tarDoc.processGraph();
//
//        printToFile("./Inst1.txt", souDoc.graphToString());
//        printToFile("./Inst2.txt", tarDoc.graphToString());
//
//        refAlign.generatePositives();
//        refAlign.generateNegetives();
//
//        findPredPairWithoutThread();
//
//        calInfoGainWithoutThread();
//
////        printToFile("./PredPair2.txt", ppl.toString());
//        findResultAlignWithoutThread();
////        calMetrics();
//
////        printToFile("./InstComp.txt", alignsStr);

        njuLinkMatcher nlm = new njuLinkMatcher();

        URI sourceURI = URI.create("src/main/resources/DOREMUS/FPT/source.ttl");
        URI targetURI = URI.create("src/main/resources/DOREMUS/FPT/target.ttl");

        String res = nlm.align(sourceURI, targetURI);

        logger.info(res);
    }

}
