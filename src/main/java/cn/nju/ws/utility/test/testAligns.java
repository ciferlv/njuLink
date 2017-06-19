package cn.nju.ws.utility.test;

import cn.nju.ws.MatchingEntry;
import cn.nju.ws.unit.alignSet.CounterPart;
import cn.nju.ws.unit.instSet.Inst;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cn.nju.ws.nlp.FormatData.getStopWords;
import static cn.nju.ws.unit.alignSet.AlignFileParser.parseAlignFile;
import static cn.nju.ws.utility.FileWriter.printToFile;
import static cn.nju.ws.utility.InfoGainCalculator.calInfoGainWithoutThread;
import static cn.nju.ws.utility.InstFileParser.parseInstFile;
import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.PredPairFinder.findPredPairWithoutThread;

/**
 * Created by ciferlv on 17-6-13.
 */
public class testAligns {

    private static Logger logger = LoggerFactory.getLogger(MatchingEntry.class);

    public static void main(String[] args) throws FileNotFoundException {

        getStopWords();

        Set<String> tarType = new HashSet<>();
        tarType.add("http://erlangen-crm.org/efrbroo/f22_self-contained_expression");

        doc1.setTarType(tarType);
        doc2.setTarType(tarType);

        Model model1 = ModelFactory.createDefaultModel();
        Model model2 = ModelFactory.createDefaultModel();
        parseInstFile("src/main/resources/DOREMUS/HT/source.ttl", doc1, model1);
        parseInstFile("src/main/resources/DOREMUS/HT/target.ttl", doc2, model2);
        doc1.processGraph();
        doc2.processGraph();

        printToFile("./Inst1", doc1.graphToString());
        printToFile("./Inst2", doc2.graphToString());
        parseAlignFile("src/main/resources/DOREMUS/HT/refalign.rdf", refAlign);
        refAlign.generatePositives();
        refAlign.generateNegetives();

        findPredPairWithoutThread();
//        calInfoGainWithThread();
        calInfoGainWithoutThread();


        printToFile("./PredPair2", ppl.toString());

//        Inst inst = doc1.getInst("http://data.doremus.org/expression/63550bbe-0aab-3231-baab-9c3b8f11ca5f");
        Inst inst1 = doc1.getInst("http://data.doremus.org/expression/6abaa561-fdab-3e17-a96f-ed7a10259138");
        Inst inst2 = doc2.getInst("http://data.doremus.org/expression/9339ce35-2286-3860-8b2b-0c1aba6ea29c");

//        inst.matchInst(inst2);
        logger.info(tempAlign.toString());
    }

    public static void calAlignInfo() throws FileNotFoundException {

        pushAlins = true;
        for (CounterPart cp : refAlign.getCounterPartList()) {

            Map<String, Inst> graph1 = doc1.getGraph();
            Map<String, Inst> graph2 = doc2.getGraph();

            Inst inst1 = graph1.get(cp.getSub1());
            Inst inst2 = graph2.get(cp.getSub2());

//            inst1.matchInst(inst2);
        }
        pushAlins = false;

        printToFile("./alignsStr.txt", alignsStr);
    }
}
