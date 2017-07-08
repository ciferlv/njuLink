package cn.nju.ws.utility.finder;

import cn.nju.ws.unit.instance.Value;
import cn.nju.ws.utility.threads.PredPairFinderThread;
import cn.nju.ws.unit.alignment.CounterPart;
import cn.nju.ws.unit.instance.Inst;
import cn.nju.ws.unit.predicatePair.PredPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.nju.ws.utility.nlp.CalSimilarity.calObjSetSim;
import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.threads.ThreadEndJudge.terminateThread;

/**
 * Created by ciferlv on 17-6-8.
 */
public class PredPairFinder {

    private static Logger logger = LoggerFactory.getLogger(PredPairFinder.class);

    public static void findPredPairWithThread() {

        Map<String, Inst> graph1 = souDoc.getGraph();
        Map<String, Inst> graph2 = tarDoc.getGraph();

        List<CounterPart> counterPartList = positives.getCounterPartList();
        ExecutorService cachedThreadPool = Executors.newFixedThreadPool(2);

        for (CounterPart cp : counterPartList) {

            String sub1 = cp.getSub1();
            String sub2 = cp.getSub2();

            Inst inst1 = graph1.get(sub1);
            Inst inst2 = graph2.get(sub2);

            Runnable run = new Thread(
                    new PredPairFinderThread(sub1, sub2, inst1, inst2));
            cachedThreadPool.execute(run);
        }

        terminateThread(cachedThreadPool, logger);
    }

    public static void findPredPairWithoutThread() {

        Map<String, Inst> graph1 = souDoc.getGraph();
        Map<String, Inst> graph2 = tarDoc.getGraph();

        List<CounterPart> counterPartList = positives.getCounterPartList();

        for (CounterPart cp : counterPartList) {

            String sub1 = cp.getSub1();
            String sub2 = cp.getSub2();

            Inst inst1 = graph1.get(sub1);
            Inst inst2 = graph2.get(sub2);

            compareCounterPart(sub1, sub2, inst1, inst2);
        }
    }

    public static void compareCounterPart(String sub1, String sub2, Inst inst1, Inst inst2) {

        if (inst1 == null) {

            logger.info("inst1 is null: ");
            logger.info("sub1: " + sub1);
            return;
        }

        if (inst2 == null) {
            logger.info("inst2 is null: ");
            logger.info("sub2: " + sub2);
            return;
        }

        Map<String, Set<Value>> predObj1 = inst1.getPredObj();
        Map<String, Set<Value>> predObj2 = inst2.getPredObj();

        if (predObj1 == null) {

            logger.info("predObj1 is null: ");
            logger.info("sub1: " + sub1);
            return;
        }

        if (predObj2 == null) {

            logger.info("predObj2 is null: ");
            logger.info("sub2: " + sub2);
            return;
        }

        Iterator iter1 = predObj1.entrySet().iterator();
        while (iter1.hasNext()) {
            Map.Entry entry1 = (Map.Entry) iter1.next();
            String pred1 = (String) entry1.getKey();

            Set<Value> valueSet1 = (Set<Value>) entry1.getValue();

            Iterator iter2 = predObj2.entrySet().iterator();

            while (iter2.hasNext()) {

                Map.Entry entry2 = (Map.Entry) iter2.next();
                String pred2 = (String) entry2.getKey();
                Set<Value> valueSet2 = (Set<Value>) entry2.getValue();

                double value = calObjSetSim(valueSet1, valueSet2).getMaxSimi();

                if (value > predPairSimiThreshold) {

                    ppl.add(new PredPair(pred1, pred2));
                }
            }
        }
    }

}
