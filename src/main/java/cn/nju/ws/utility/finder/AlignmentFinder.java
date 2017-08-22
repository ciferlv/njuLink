package cn.nju.ws.utility.finder;

import cn.nju.ws.unit.alignment.CounterPart;
import cn.nju.ws.unit.instance.Inst;
import cn.nju.ws.unit.instance.Value;
import cn.nju.ws.unit.others.Pair;
import cn.nju.ws.unit.predicatePair.PredPair;
import cn.nju.ws.utility.threads.AlignmentFinderThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.nlp.CalSimilarity.calValSetSim;
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

                if (!useAverageSimi) {
                    getSimiInfoOfTwoInsts(inst1, inst2);
                }
            }
        }

//        tempAlign.stableMarriage();
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


    private static void changeCounterPartParam(CounterPart cp1, CounterPart cp2, boolean isDisPredPair, Set<Value> valSet1, Set<Value> valSet2, PredPair pp) {

        Pair matchRes;
//        matchRes = calValSetSim(valSet1, valSet2);

        if (valSet1.size() < valSet2.size()) {
            matchRes = calValSetSim(valSet1, valSet2);
        } else matchRes = calValSetSim(valSet2, valSet1);

        if (isDisPredPair) {

            if (matchRes.getMaxSimi() > predPairSimiThreshold) {

                cp1.addMatchedNumDis(1);
//                cp2.addMatchedNumDis(1);

                cp1.addInfoGainSumDis(pp.getInfoGain());
//                cp2.addInfoGainSumDis(pp.getInfoGain());

                cp1.addDetailInfoGainSumDis(
                        (matchRes.getMatchedNumOfValSet1() - matchRes.getUnmatchedNumOfValSet1())
                                * pp.getInfoGain());
//                cp2.addDetailInfoGainSumDis(
//                        (matchRes.getMatchedNumOfValSet2() - matchRes.getMatchedNumOfValSet2())
//                                * pp.getInfoGain());

                cp1.addMaxSimiSumDis(matchRes.getSimiSumOfValSet1() * pp.getInfoGain());
//                cp2.addMaxSimiSumDis(matchRes.getSimiSumOfValSet2() * pp.getInfoGain());
            } else {

                cp1.addInfoGainSumDis(pp.getInfoGain() * -1);
//                cp2.addInfoGainSumDis(pp.getInfoGain() * -1);

                cp1.addDetailInfoGainSumDis(pp.getInfoGain() * -1);
//                cp2.addDetailInfoGainSumDis(pp.getInfoGain() * -1);

                cp1.addUnmatchedNumDis(1);
//                cp2.addUnmatchedNumDis(1);
            }
        } else {

            if (matchRes.getMaxSimi() > predPairSimiThreshold) {

                cp1.addMatchedNumUndis(1);
//                cp2.addMatchedNumUndis(1);

                cp1.addMaxSimiSumUndis(matchRes.getSimiSumOfValSet1());
//                cp2.addMaxSimiSumUndis(matchRes.getSimiSumOfValSet2());
            }
        }
    }

    public static void judgeByAllPredPair(CounterPart cp1, CounterPart cp2, Inst inst1, Inst inst2) {

        Map<String, Set<Value>> propVal1 = inst1.getPropVal();
        Map<String, Set<Value>> propVal2 = inst2.getPropVal();

        Iterator iter = propVal1.entrySet().iterator();

        while (iter.hasNext()) {

            Map.Entry entry = (Map.Entry) iter.next();
            String prop1 = (String) entry.getKey();
            Set<Value> valSet1 = (Set<Value>) entry.getValue();
            if (valSet1 != null) {

                if (ppl.containsProp1(prop1)) {

                    List<PredPair> ppResList = ppl.getPredPairByPred1(prop1);

                    for (PredPair pp : ppResList) {

                        Set<Value> valSet2 = propVal2.get(pp.getProp2());
                        if (valSet2 != null) {

                            changeCounterPartParam(cp1, cp2, true, valSet1, valSet2, pp);
                        }
                    }
                } else {

                    Set<Value> valSet2 = propVal2.get(prop1);
                    if (valSet2 != null) {

                        changeCounterPartParam(cp1, cp2, false, valSet1, valSet2, null);
                    }
                }
            }
        }

        if (cp1.getMatchedNumDis() >= ppl.size() * 0.618) {

            tempAlign.addCounterPart(cp1);
//            tempAlign.addCounterPart(cp2);
//            tempAlign.addCpToCpGraph(inst1.getSub(), inst2.getSub(), cp1);
//            tempAlign.addCpToCpGraph(inst2.getSub(), inst1.getSub(), cp2);
        }
    }


    public static void getSimiInfoOfTwoInsts(Inst inst1, Inst inst2) {

//        Get Structure Info
        int sameProp = 0, diffProp = 0;
        Set<String> inst1Prop = inst1.getPropVal().keySet();
        for (String prop : inst1Prop)
            if (inst2.getValSetByProp(prop) != null) sameProp++;

        diffProp += inst1.getPropVal().size() + inst2.getPropVal().size() - 2 * sameProp;

        if (inst1.hasSameAs() & inst2.hasSameAs()) sameProp++;
        else diffProp++;

        if (inst1.hasType() & inst2.hasType()) sameProp++;
        else diffProp++;

//        Get SimiInfo
        CounterPart cp1 = new CounterPart(inst1.getSub(), inst2.getSub());
        CounterPart cp2 = new CounterPart(inst2.getSub(), inst1.getSub());

        cp1.setSameProp(sameProp);
        cp2.setSameProp(sameProp);

        cp1.setDiffProp(diffProp);
        cp2.setDiffProp(diffProp);

        judgeByAllPredPair(cp1, cp2, inst1, inst2);

    }

    public static void judgeByDiscriminativePredPair(Inst inst1, Inst inst2) {

        CounterPart cp1 = new CounterPart(inst1.getSub(), inst2.getSub());
        CounterPart cp2 = new CounterPart(inst1.getSub(), inst2.getSub());

        Map<String, Set<Value>> propVal1 = inst1.getPropVal();
        Map<String, Set<Value>> propVal2 = inst2.getPropVal();

        for (PredPair pp : ppl.getPredPairList()) {

            Set<Value> valSet1 = propVal1.get(pp.getProp1());
            Set<Value> valSet2 = propVal2.get(pp.getProp2());

            if (valSet1 != null && valSet2 != null) {

                changeCounterPartParam(cp1, cp2, true, valSet1, valSet2, pp);
            }

        }

        if (cp1.getMatchedNumDis() >= ppl.size() * 0.618) {

            tempAlign.addCounterPart(cp1);
        }

    }
}
