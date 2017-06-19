package cn.nju.ws.threads;

import cn.nju.ws.unit.predPairList.PredPair;

import static cn.nju.ws.utility.InfoGainCalculator.calPredPairInfoGain;

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
