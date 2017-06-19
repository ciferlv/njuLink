package cn.nju.ws.unit.predPairList;

import java.util.Comparator;

/**
 * Created by ciferlv on 17-6-16.
 */
public class PredPairComparator implements Comparator<PredPair> {

    @Override
    public int compare(PredPair o1, PredPair o2) {

        if (o1.getInfoGain() == o2.getInfoGain()) {

            if (o1.getTime() < o2.getTime()) {
                return 1;
            } else if (o1.getTime() > o2.getTime()) {
                return -1;
            } else return 0;
        } else if (o1.getInfoGain() < o2.getInfoGain()) {
            return 1;
        } else return -1;
    }
}
