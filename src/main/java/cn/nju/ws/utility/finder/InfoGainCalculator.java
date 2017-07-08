package cn.nju.ws.utility.finder;

import cn.nju.ws.utility.threads.InfoGainCalculatorThread;
import cn.nju.ws.unit.alignment.CounterPart;
import cn.nju.ws.unit.instance.Inst;
import cn.nju.ws.unit.instance.Value;
import cn.nju.ws.unit.predicatePair.PredPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class InfoGainCalculator {

    private static Logger logger = LoggerFactory.getLogger(InfoGainCalculator.class);

    public static double calEntropy(double posSize, double negSize) {

        if (posSize == 0 && negSize == 0) return 0;
        double pos = posSize * 1.0 / (posSize + negSize);
        double neg = negSize * 1.0 / (posSize + negSize);


        if (neg == 1 || pos == 1) {
            return 0;
        }

        double res = -1 * pos * Math.log(pos) / Math.log(2.0) - neg * Math.log(neg) / Math.log(2.0);

        return res;
    }

    public static void calInfoGainWithThread() {

        int posSize = positives.size();
        int negSize = negetives.size();

        double initialEntropy = calEntropy(posSize, negSize);

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (PredPair pp : ppl.getPredPairList()) {

            Runnable run = new Thread(
                    new InfoGainCalculatorThread(pp, initialEntropy));
            cachedThreadPool.execute(run);
        }

        terminateThread(cachedThreadPool, logger);

        ppl.filterPropPairByInfoGain();
        ppl.sort();
    }

    public static void calInfoGainWithoutThread() {

        int posSize = positives.size();
        int negSize = negetives.size();

        double initialEntropy = calEntropy(posSize, negSize);

        for (PredPair pp : ppl.getPredPairList()) {

            calPredPairInfoGain(pp, initialEntropy);
        }

        ppl.filterPropPairByInfoGain();
        ppl.sort();
    }

    public static void calPredPairInfoGain(PredPair pp, double initialEntropy) {

        int truePos = 0, falsePos = 0, trueNeg = 0, falseNeg = 0;

        int posSize = positives.size();
        int negSize = negetives.size();

        String pred1 = pp.getProp1();
        String pred2 = pp.getProp2();

        Map<String, Inst> graph1 = souDoc.getGraph();
        Map<String, Inst> graph2 = tarDoc.getGraph();

        for (CounterPart cp : positives.getCounterPartList()) {

            String sub1 = cp.getSub1();
            String sub2 = cp.getSub2();

            Inst inst1 = graph1.get(sub1);
            Inst inst2 = graph2.get(sub2);

            Set<Value> valueSet1 = inst1.getPredObj().get(pred1);
            Set<Value> valueSet2 = inst2.getPredObj().get(pred2);

            if (valueSet1 == null || valueSet2 == null) {

                falsePos++;
                continue;
            } else {

                double value = calObjSetSim(valueSet1, valueSet2).getMaxSimi();
                if (value > predPairSimiThreshold) {
                    truePos++;
                } else falsePos++;
            }
        }

        for (CounterPart cp : negetives.getCounterPartList()) {

            String sub1 = cp.getSub1();
            String sub2 = cp.getSub2();

            Inst inst1 = graph1.get(sub1);
            Inst inst2 = graph2.get(sub2);

            Set<Value> valueSet1 = inst1.getPredObj().get(pred1);
            Set<Value> valueSet2 = inst2.getPredObj().get(pred2);

            if (valueSet1 == null || valueSet2 == null) {
                trueNeg++;
            } else {

                double value = calObjSetSim(valueSet1, valueSet2).getMaxSimi();
                if (value > predPairSimiThreshold) {
                    falseNeg++;
                } else trueNeg++;
            }
        }

        double posEntropy = calEntropy(truePos, falseNeg);
        double negEntropy = calEntropy(falsePos, trueNeg);

        double stateEntropy = posEntropy * (truePos + falseNeg) / (posSize + negSize)
                + negEntropy * (falsePos + trueNeg) / (posSize + negSize);
        pp.setInfoGain(initialEntropy - stateEntropy);
    }

}
