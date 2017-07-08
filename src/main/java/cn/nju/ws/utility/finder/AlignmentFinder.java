package cn.nju.ws.utility.finder;

import cn.nju.ws.unit.instance.Value;
import cn.nju.ws.utility.threads.AlignmentFinderThread;
import cn.nju.ws.unit.others.Pair;
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
public class AlignmentFinder {

    private static Logger logger = LoggerFactory.getLogger(AlignmentFinder.class);

    public static void findResultAlignWithoutThread() {

        Map<String, Inst> graph1 = souDoc.getGraph();
        Map<String, Inst> graph2 = tarDoc.getGraph();

        List<String> tarSubList1 = souDoc.getTarSubList();
        List<String> tarSubList2 = tarDoc.getTarSubList();

        for (String sub1 : tarSubList1) {

            Inst inst1 = graph1.get(sub1);
            for (String sub2 : tarSubList2) {

                Inst inst2 = graph2.get(sub2);

                judgeInstsMatch(inst1, inst2);
            }
        }

        tempAlign.generateResultAlign();
    }

    public static void findResultAlignWithThread() {

        Map<String, Inst> graph1 = souDoc.getGraph();
        Map<String, Inst> graph2 = tarDoc.getGraph();

        List<String> tarSubList1 = souDoc.getTarSubList();
        List<String> tarSubList2 = tarDoc.getTarSubList();

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (String sub1 : tarSubList1) {

            Inst inst1 = graph1.get(sub1);
            for (String sub2 : tarSubList2) {

                Inst inst2 = graph2.get(sub2);
                Runnable run = new Thread(
                        new AlignmentFinderThread(inst1, inst2));
                cachedThreadPool.execute(run);
            }
        }
        terminateThread(cachedThreadPool, logger);
    }


    private static void changeCounterPartParam(CounterPart cp, boolean isDisPredPair, Set<Value> valueSet1, Set<Value> valueSet2, PredPair pp) {

        Pair matchRes;
        if (valueSet1.size() < valueSet2.size()) {
            matchRes = calObjSetSim(valueSet1, valueSet2);
        } else matchRes = calObjSetSim(valueSet2, valueSet1);

        if (isDisPredPair) {

            if (matchRes.getMaxSimi() > predPairSimiThreshold) {

                cp.addMatchedNumDis(1);
                cp.addInfoGainSumDis(pp.getInfoGain());
                cp.addDetailInfoGainSumDis(
                        (matchRes.getMatchedNumOfSmall() - matchRes.getUnmatchedNumSmall())
                                * pp.getInfoGain());
                cp.addMaxSimiSumDis(matchRes.getSimiSum() * pp.getInfoGain());
            } else {

                cp.addInfoGainSumDis(pp.getInfoGain() * -1);
                cp.addDetailInfoGainSumDis(pp.getInfoGain() * -1);
                cp.addUnmatchedNumDis(1);
            }
        } else {

            if (matchRes.getMaxSimi() > predPairSimiThreshold) {

                cp.addMatchedNumUndis(1);
                cp.addMaxSimiSumUndis(matchRes.getSimiSum());
            }
        }
    }

    public static void judgeByAllPredPair(Inst inst1, Inst inst2) {

        CounterPart cp = new CounterPart(inst1.getSub(), inst2.getSub());

        Map<String, Set<Value>> predObj1 = inst1.getPredObj();
        Map<String, Set<Value>> predObj2 = inst2.getPredObj();

        Iterator iter = predObj1.entrySet().iterator();

        while (iter.hasNext()) {

            Map.Entry entry = (Map.Entry) iter.next();
            String pred1 = (String) entry.getKey();
            Set<Value> valueSet1 = (Set<Value>) entry.getValue();
            if (valueSet1 != null) {

                if (ppl.containsPred1(pred1)) {

                    List<PredPair> ppResList = ppl.getPredPairByPred1(pred1);

                    for (PredPair pp : ppResList) {

                        Set<Value> valueSet2 = predObj2.get(pp.getProp2());

                        if (valueSet2 != null) {

                            changeCounterPartParam(cp, true, valueSet1, valueSet2, pp);
                        }
                    }
                } else {

                    Set<Value> valueSet2 = predObj2.get(pred1);
                    if (valueSet2 != null) {

                        changeCounterPartParam(cp, false, valueSet1, valueSet2, null);
                    }
                }
            }
        }
//        logger.info(cp.toString());
        if (cp.getMatchedNumDisOfSmall() >= predPairNumNeededThreshold) {


            tempAlign.addCounterPart(cp);
        }
    }

    public static void judgeByDiscriminativePredPair(Inst inst1, Inst inst2) {

        CounterPart cp = new CounterPart(inst1.getSub(), inst2.getSub());

        Map<String, Set<Value>> predObj1 = inst1.getPredObj();
        Map<String, Set<Value>> predObj2 = inst2.getPredObj();

        for (PredPair pp : ppl.getPredPairList()) {

            Set<Value> valueSet1 = predObj1.get(pp.getProp1());
            Set<Value> valueSet2 = predObj2.get(pp.getProp2());

            if (valueSet1 != null && valueSet2 != null) {

                changeCounterPartParam(cp, true, valueSet1, valueSet2, pp);
            }

        }

        if (cp.getMatchedNumDisOfSmall() >= predPairNumNeededThreshold) {

            tempAlign.addCounterPart(cp);
        }

    }

    public static void judgeInstsMatch(Inst inst1, Inst inst2) {

        if (!useAverageSimi) {

//            judgeByDiscriminativePredPair(inst1, inst2);
            judgeByAllPredPair(inst1, inst2);

        } else {
//            if (result > ALIGN_THRESHOLD) {
//
//                resultAlign.addCounterPart(new CounterPart(inst1.getSub(), inst2.getSub()));
//            }
        }
    }

}
