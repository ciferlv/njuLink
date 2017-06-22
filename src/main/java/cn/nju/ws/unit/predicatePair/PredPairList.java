package cn.nju.ws.unit.predicatePair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static cn.nju.ws.utility.ParamDef.infoGainThreshold;

/**
 * Created by ciferlv on 17-6-8.
 */
public class PredPairList {

    private Logger logger = LoggerFactory.getLogger(PredPairList.class);
    private List<PredPair> predPairList = Collections.synchronizedList(new ArrayList<PredPair>());


    public synchronized PredPair contains(PredPair pp) {

        Iterator<PredPair> iter = predPairList.iterator();

        while (iter.hasNext()) {

            PredPair tempPP = iter.next();
            if (tempPP.equals(pp)) {
                return tempPP;
            }
        }
        return null;
    }

    public synchronized void add(PredPair pp) {

        if (pp == null) return;
        PredPair tempPP = contains(pp);
        if (tempPP == null) {
            predPairList.add(pp);
        } else tempPP.setTime(tempPP.getTime() + 1);
    }

    public void resize(int size) {

        for (int i = predPairList.size() - 1; i >= size; i--) {

            predPairList.remove(i);
        }

    }

    public int size() {

        return predPairList.size();
    }

    public boolean containsPred1(String pred1) {

        for (PredPair pp : predPairList) {

            if (pp.getProp1().equals(pred1)) return true;
        }

        return false;
    }

    public List<PredPair> getPredPairByPred1(String pred1) {

        List<PredPair> counterList = new ArrayList<>();
        for (PredPair pp : predPairList) {

            if (pp.getProp1().equals(pred1)) {

                counterList.add(pp);
            }
        }

        return counterList;
    }

    public boolean containsPred2(String pred2) {

        for (PredPair pp : predPairList) {

            if (pp.getProp2().equals(pred2)) return true;
        }

        return false;
    }

    public boolean containsPred(String pred) {

        for (PredPair pp : predPairList) {

            if (pp.getProp1().equals(pred) || pp.getProp2().equals(pred)) return true;
        }
        return false;
    }

    @Override
    public synchronized String toString() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("size: " + size() + "\n");

        for (PredPair pp : predPairList) {

            buffer.append(pp.toString() + "\n");
        }
        return String.valueOf(buffer);
    }

    public void filterPropPairByInfoGain() {

        List<PredPair> myPropPairList = Collections.synchronizedList(new ArrayList<PredPair>());

        for (PredPair pp : predPairList) {

            if (pp.getInfoGain() > infoGainThreshold) {

                myPropPairList.add(pp);
            }
        }
        predPairList.clear();
        for (PredPair pp : myPropPairList) {

            predPairList.add(pp);
        }
    }

    public void sort() {
        Collections.sort(predPairList, new PredPairComparator());
    }

    public List<PredPair> getPredPairList() {
        return predPairList;
    }
}
