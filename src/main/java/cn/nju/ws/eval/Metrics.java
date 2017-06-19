package cn.nju.ws.eval;

import cn.nju.ws.unit.alignSet.AlignmentSet;
import cn.nju.ws.unit.alignSet.CounterPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;

import static cn.nju.ws.utility.FileWriter.printToFile;
import static cn.nju.ws.utility.ParamDef.refAlign;
import static cn.nju.ws.utility.ParamDef.resultAlign;

/**
 * Created by ciferlv on 17-6-8.
 */
public class Metrics {

    private static Logger logger = LoggerFactory.getLogger(Metrics.class);

    public static void calMetrics() {

        double precision, recall, f1_score;

        AlignmentSet correctAlign = new AlignmentSet();
        AlignmentSet wrongAlign = new AlignmentSet();
        AlignmentSet unfoundAlign = new AlignmentSet();

        int correctNum = 0, wrongNum = 0;
        int refSize, resultSize;
        List<CounterPart> refCPL = refAlign.getCounterPartList();
        List<CounterPart> resultCPL = resultAlign.getCounterPartList();

        refSize = refAlign.size();
        resultSize = resultAlign.size();

        for (CounterPart cp : resultCPL) {
            if (refCPL.contains(cp)) {

                correctNum++;
                correctAlign.addCounterPart(cp);
            } else {
                wrongNum++;
                wrongAlign.addCounterPart(cp);
            }
        }

        for (CounterPart cp : refCPL) {

            if (!resultCPL.contains(cp)) {

                unfoundAlign.addCounterPart(cp);
            }
        }

        logger.info("CorrectNum: "+correctNum);
        logger.info("refSize: "+refSize);
        logger.info("resultSize: "+resultSize);
        precision = 1.0 * correctNum / resultSize;
        recall = 1.0 * correctNum / refSize;
        f1_score = (2.0 * precision * recall) / (precision + recall);

        String metrics = "precision: " + precision + "\nrecall: " + recall + "\nf1-score: " + f1_score;
        logger.info(metrics);

        try {

            printToFile("./correct.txt", correctAlign.toString());
            printToFile("./wrong.txt", wrongAlign.toString());
            printToFile("./unfound.txt", unfoundAlign.toString());
            printToFile("./metrics.txt", metrics);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
