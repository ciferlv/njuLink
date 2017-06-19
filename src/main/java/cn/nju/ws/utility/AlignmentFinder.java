package cn.nju.ws.utility;

import cn.nju.ws.threads.AlignmentFinderThread;
import cn.nju.ws.unit.Pair;
import cn.nju.ws.unit.alignSet.CounterPart;
import cn.nju.ws.unit.instSet.Inst;
import cn.nju.ws.unit.instSet.Obj;
import cn.nju.ws.unit.predPairList.PredPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.nju.ws.nlp.CalSimilarity.calObjSetSim;
import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.ThreadEndJudge.terminateThread;

/**
 * Created by ciferlv on 17-6-8.
 */
public class AlignmentFinder {

    private static Logger logger = LoggerFactory.getLogger(AlignmentFinder.class);

    public static void findResultAlignWithoutThread() {

        Map<String, Inst> graph1 = doc1.getGraph();
        Map<String, Inst> graph2 = doc2.getGraph();

        List<String> tarSubList1 = doc1.getTarSubList();
        List<String> tarSubList2 = doc2.getTarSubList();

        for (String sub1 : tarSubList1) {

            Inst inst1 = graph1.get(sub1);
            for (String sub2 : tarSubList2) {

                Inst inst2 = graph2.get(sub2);

                judgeInstsMatch(inst1, inst2);
            }
        }

//        for (String key : alignMap.getAlignMapSub1().keySet()) {
//
//            resultAlign.addCounterPart(alignMap.getAlignMapSub1().get(key));
//        }
//        alignMap.generateAlign();
        tempAlign.generateResultAlign();
    }

    public static void findResultAlignWithThread() {

        Map<String, Inst> graph1 = doc1.getGraph();
        Map<String, Inst> graph2 = doc2.getGraph();

        List<String> tarSubList1 = doc1.getTarSubList();
        List<String> tarSubList2 = doc2.getTarSubList();

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


    private static void changeCounterPartParam(CounterPart cp, boolean isDisPredPair, Set<Obj> objSet1, Set<Obj> objSet2, PredPair pp) {

        Pair matchRes;
        if (objSet1.size() < objSet2.size()) {
            matchRes = calObjSetSim(objSet1, objSet2);
        } else matchRes = calObjSetSim(objSet2, objSet1);

        if (isDisPredPair) {

            if (matchRes.getMaxSimi() > PROP_PAIR_THRESHOLD) {

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

            if (matchRes.getMaxSimi() > PROP_PAIR_THRESHOLD) {

                cp.addMatchedNumUndis(1);
                cp.addMaxSimiSumUndis(matchRes.getSimiSum());
            }
        }
    }

    public static void judgeByAllPredPair(Inst inst1, Inst inst2) {

        CounterPart cp = new CounterPart(inst1.getSub(), inst2.getSub());

        Map<String, Set<Obj>> predObj1 = inst1.getPredObj();
        Map<String, Set<Obj>> predObj2 = inst2.getPredObj();

        Iterator iter = predObj1.entrySet().iterator();

        while (iter.hasNext()) {

            Map.Entry entry = (Map.Entry) iter.next();
            String pred1 = (String) entry.getKey();
            Set<Obj> objSet1 = (Set<Obj>) entry.getValue();
            if (objSet1 != null) {

                if (ppl.containsPred1(pred1)) {

                    List<PredPair> ppResList = ppl.getPredPairByPred1(pred1);

                    for (PredPair pp : ppResList) {

                        Set<Obj> objSet2 = predObj2.get(pp.getProp2());

                        if (objSet2 != null) {

                            changeCounterPartParam(cp, true, objSet1, objSet2, pp);
                        }
                    }
                } else {

                    Set<Obj> objSet2 = predObj2.get(pred1);
                    if (objSet2 != null) {

                        changeCounterPartParam(cp, false, objSet1, objSet2, null);
                    }
                }
            }
        }
//        logger.info(cp.toString());
        if (cp.getMatchedNumDisOfSmall() > pred_pair_num_need_threshold) {


            tempAlign.addCounterPart(cp);
        }
    }

    public static void judgeByDiscriminativePredPair(Inst inst1, Inst inst2) {

        CounterPart cp = new CounterPart(inst1.getSub(), inst2.getSub());

        Map<String, Set<Obj>> predObj1 = inst1.getPredObj();
        Map<String, Set<Obj>> predObj2 = inst2.getPredObj();

        for (PredPair pp : ppl.getPredPairList()) {

            Set<Obj> objSet1 = predObj1.get(pp.getProp1());
            Set<Obj> objSet2 = predObj2.get(pp.getProp2());

            if (objSet1 != null && objSet2 != null) {

                changeCounterPartParam(cp, true, objSet1, objSet2, pp);
            }

        }

        if (cp.getMatchedNumDisOfSmall() > pred_pair_num_need_threshold) {

            tempAlign.addCounterPart(cp);
        }

    }

    public static void judgeInstsMatch(Inst inst1, Inst inst2) {

        if (!use_average_simi) {

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
