package cn.nju.ws.threads;

import cn.nju.ws.unit.instSet.Inst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.nju.ws.utility.AlignmentFinder.judgeInstsMatch;

/**
 * Created by ciferlv on 17-6-8.
 */
public class AlignmentFinderThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(AlignmentFinderThread.class);

    private Inst inst1 = null;
    private Inst inst2 = null;

    public AlignmentFinderThread(Inst inst1, Inst inst2) {

        this.inst1 = inst1;
        this.inst2 = inst2;
    }

    public void run() {

        judgeInstsMatch(inst1, inst2);
    }
}
