import cn.nju.ws.unit.alignment.CounterPart;
import cn.nju.ws.unit.instance.Inst;
import cn.nju.ws.unit.predicatePair.PredPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static cn.nju.ws.utility.ParamDef.*;

/**
 * Created by ciferlv on 17-6-9.
 */
public class PredPairTest {

    private static Logger logger = LoggerFactory.getLogger(PredPairTest.class);

    public static void predPairTestCal() {

        List<PredPair> predPairList = ppl.getPredPairList();

        Map<String, Inst> graph1 = doc1.getGraph();
        Map<String, Inst> graph2 = doc2.getGraph();


        for (CounterPart cp : refAlign.getCounterPartList()) {

            String sub1 = cp.getSub1();
            String sub2 = cp.getSub2();
            Inst inst1 = graph1.get(sub1);
            Inst inst2 = graph2.get(sub2);


            logger.info("sub1: " + sub1);
            logger.info("sub2: " + sub2);

        }

    }
}
