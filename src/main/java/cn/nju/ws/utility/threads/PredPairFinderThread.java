package cn.nju.ws.utility.threads;

import cn.nju.ws.unit.instance.Inst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.nju.ws.utility.finder.PredPairFinder.compareCounterPart;

/**
 * Created by ciferlv on 17-6-8.
 */
public class PredPairFinderThread implements Runnable {

    Logger logger = LoggerFactory.getLogger(PredPairFinderThread.class);

    Inst inst1, inst2;
    String sub1, sub2;

    public PredPairFinderThread(String sub1, String sub2, Inst inst1, Inst inst2) {

        this.inst1 = inst1;
        this.inst2 = inst2;
        this.sub1 = sub1;
        this.sub2 = sub2;
    }

    public void run() {

        compareCounterPart(sub1, sub2, inst1, inst2);
    }
}
