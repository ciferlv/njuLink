package cn.nju.ws.unit.alignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static cn.nju.ws.utility.ParamDef.*;

/**
 * Created by ciferlv on 17-6-8.
 */
public class AlignmentSet {

    private Logger logger = LoggerFactory.getLogger(AlignmentSet.class);

    private List<CounterPart> counterPartList = Collections.synchronizedList(new ArrayList<CounterPart>());

    public synchronized void addCounterPart(CounterPart counterPart) {

        counterPartList.add(counterPart);
    }

    public void sortAlign() {

        Collections.sort(counterPartList, new CounterPartComparator());
    }

    public int size() {

        return counterPartList.size();
    }

    public boolean contains(CounterPart cp) {

        if (counterPartList.contains(cp)) return true;
        else return false;
    }

    public CounterPart findCounterPart(int index) {

        if (index < counterPartList.size() && index >= 0) {
            return counterPartList.get(index);
        } else {

            logger.info("[AlignmentSet.counterPartList] index is not found!");
            return null;
        }

    }

    public void generatePositives() {

        if (isForFormalContest) {

            positives.setCounterPartList(counterPartList);
        } else {
            Random r = new Random();
            int positiveSize = (int) (size() * INITIAL_SAMPLE_PERSENT);

            while (positives.size() < positiveSize) {

                int index = r.nextInt(size());
                positives.addCounterPart(findCounterPart(index));
            }
        }
    }

    public void generateNegetives() {

        List<CounterPart> counterPartList = positives.getCounterPartList();
        List<String> subList = doc2.getTarSubList();

        Random r = new Random(105);

        for (CounterPart cp : counterPartList) {

            String sub1 = cp.getSub1();
            boolean flag = false;
            do {

                int index = r.nextInt(subList.size());
                String tarSub = subList.get(index);

                if (!positives.contains(new CounterPart(sub1, tarSub))) {
                    flag = true;
                    negetives.addCounterPart(new CounterPart(sub1, tarSub));
                }
            } while (!flag);
        }
    }

    public void generateResultAlign() {

        List<String> sub = new ArrayList<>();
        sortAlign();

        for (CounterPart cp : counterPartList) {

            alignsStr += cp.toString();

            if (!(sub.contains(cp.getSub1()) || sub.contains(cp.getSub2()))) {

//                    resultAlign.addCounterPart(cp);
                testAlign.addCounterPart(cp);
                sub.add(cp.getSub1());
                sub.add(cp.getSub2());

            }
        }

        List<CounterPart> testCpl = testAlign.getCounterPartList();

        for (CounterPart cp : testCpl) {

            if (refSet.contains(cp.getSub1())
                    || refSet.contains(cp.getSub2())) {

                resultAlign.addCounterPart(cp);
            }
        }
//        logger.info(resultAlign.toString());
    }

    public String toString() {

        StringBuffer bf = new StringBuffer();

        bf.append("size: " + counterPartList.size() + "\n");

        for (CounterPart cp : counterPartList) {
            bf.append(cp.toString());
            bf.append("\n");
        }
        return String.valueOf(bf);
    }

    public List<CounterPart> getCounterPartList() {
        return counterPartList;
    }

    public void setCounterPartList(List<CounterPart> counterPartList) {
        this.counterPartList = counterPartList;
    }
}
