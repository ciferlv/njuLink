//import org.apache.jena.rdf.model.Model;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import static cn.nju.ws.utility.nlp.FormatData.getStopWords;
//import static cn.nju.ws.utility.fileParser.InstFileApacheJenaParser.parseInstFileByApacheJena;
//import static cn.nju.ws.utility.ParamDef.souDoc;
//
///**
// * Created by ciferlv on 17-6-11.
// */
//public class testMatchingEntry {
//
//    private static Logger logger = LoggerFactory.getLogger(testMatchingEntry.class);
//
//    public static void init() {
//
//        getStopWords();
//    }
//
//    public static void main(String[] args) {
//
//        init();
//        logger.info("finish");
//        Set<String> tarType = new HashSet<>();
//        souDoc.setTarType(tarType);
//
//        tarType.add("http://erlangen-crm.org/efrbroo/f22_self-contained_expression");
//        Model model1 = ModelFactory.createDefaultModel();
//        parseInstFileByApacheJena("src/main/resources/DOREMUS/HT/test.ttl", souDoc, model1);
//
//        souDoc.processGraph();
//
//        logger.info(souDoc.graphToString());
//
//    }
//}
