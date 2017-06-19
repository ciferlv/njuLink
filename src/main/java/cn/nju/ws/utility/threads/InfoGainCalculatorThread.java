package cn.nju.ws.utility.threads;

import cn.nju.ws.unit.predicatePair.PredPair;

import static cn.nju.ws.utility.finder.InfoGainCalculator.calPredPairInfoGain;

/**
 * Created by ciferlv on 17-6-8.
 */
public class InfoGainCalculatorThread implements Runnable {

    private double initialEntropy;
    private PredPair pp;

    public InfoGainCalculatorThread(PredPair pp, double initialEntropy) {

        this.pp = pp;
        this.initialEntropy = initialEntropy;
    }

    public void run() {

        calPredPairInfoGain(pp, initialEntropy);
    }
}
