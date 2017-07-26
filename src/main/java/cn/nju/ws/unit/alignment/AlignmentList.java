package cn.nju.ws.unit.alignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static cn.nju.ws.utility.ParamDef.*;

/**
 * Created by ciferlv on 17-6-8.
 */
public class AlignmentList {

    private Logger logger = LoggerFactory.getLogger(AlignmentList.class);

    private List<CounterPart> counterPartList = Collections.synchronizedList(new ArrayList<CounterPart>());

    private Map<String, Map<String, CounterPart>> cpGraph = new HashMap<>();

//    public void addCpToCpGraph(String sub1, String sub2, CounterPart cp) {
//
//        if (cpGraph.containsKey(sub1)) {
//
//            Map<String, CounterPart> tmpCp = cpGraph.get(sub1);
//            tmpCp.put(sub2, cp);
//        } else {
//
//            Map<String, CounterPart> tmpCp = new HashMap<>();
//            tmpCp.put(sub2, cp);
//            cpGraph.put(sub1, tmpCp);
//        }
//    }
//
//    public void stableMarriage() {
//
//        Map<String, String> tarConnToSou = new HashMap<>();
//        Set<String> hasTried = new HashSet<>();
//
//        Queue<String> souSubQueue = new LinkedList<>();
//
//        for (String sub : souDoc.getTarSubList()) souSubQueue.add(sub);
//        for (String sub : tarDoc.getTarSubList()) tarConnToSou.put(sub, null);
//
//        while (!souSubQueue.isEmpty()) {
//
//            String souSub = souSubQueue.poll();
//            Map<String, CounterPart> tmpMap = cpGraph.get(souSub);
//
//            if (tmpMap == null) {
//                continue;
//            }
//
//            CounterPart tmpCp = null;
//            String aim = null;
//            for (String tarSub : tarDoc.getTarSubList()) {
//
//                if (tmpMap.containsKey(tarSub) && !hasTried.contains(souSub + tarSub)) {
//
//                    if (aim == null || tmpCp.compareTo(tmpMap.get(tarSub)) > 0) {
//
//                        if (tarConnToSou.get(tarSub) == null) {
//
//                            aim = tarSub;
//                            tmpCp = tmpMap.get(tarSub);
//                        } else {
//
//                            String presentSouSub = tarConnToSou.get(tarSub);
//                            CounterPart presentInst = cpGraph.get(tarSub).get(presentSouSub);
//                            CounterPart compInst = cpGraph.get(tarSub).get(souSub);
//
//                            if (presentInst.compareWithAnotherCounterPart(compInst) > 0) {
//
//                                aim = tarSub;
//                                tmpCp = tmpMap.get(tarSub);
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (aim == null) continue;
//
//            if (tarConnToSou.get(aim) == null) {
//
//                tarConnToSou.put(aim, souSub);
//                hasTried.add(souSub+aim);
//            } else {
//
//                souSubQueue.add(tarConnToSou.get(aim));
//                hasTried.add(souSub+aim);
//
//                tarConnToSou.put(aim,souSub);
//            }
//        }
//
//        Iterator iter = tarConnToSou.entrySet().iterator();
//        while (iter.hasNext()) {
//
//            Map.Entry entry = (Map.Entry) iter.next();
//            String tarSub = (String) entry.getKey();
//            String souSub = (String) entry.getValue();
//
//            if (souSub != null) {
//
//                test2Align.addCounterPart(new CounterPart(souSub, tarSub));
//            }
//        }
//        test2Align.generateResultAlign();
//    }

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

            logger.info("[AlignmentList.counterPartList] index is not found!");
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

        Set<String> positiveSub1Set = new HashSet<>();

        for (CounterPart cp : counterPartList) {

            positiveSub1Set.add(cp.getSub1());
        }

        List<String> subList = tarDoc.getTarSubList();

        if (subList.size() == 0) {

            if (!isForFormalContest) {

                logger.info("SubList in TarDoc is empty!");
            }
            return;
        }

        Random r = new Random(105);

        for (String sub1 : positiveSub1Set) {

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

            if (!isForFormalContest) {
                alignsStr += cp.toString();
            }

            if (!(sub.contains(cp.getSub1()) || sub.contains(cp.getSub2()))) {

                resultAlign.addCounterPart(cp);
//                testAlign.addCounterPart(cp);
                sub.add(cp.getSub1());
                sub.add(cp.getSub2());

            }
        }

//        List<CounterPart> testCpl = testAlign.getCounterPartList();
//
//        for (CounterPart cp : testCpl) {
//
//            if (refSet.contains(cp.getSub1())
//                    || refSet.contains(cp.getSub2())) {
//
//                resultAlign.addCounterPart(cp);
//            }
//        }

        for (CounterPart cp : resultAlign.getCounterPartList()) {

            alignBuffer.append("\n\t<map>");
            alignBuffer.append("\n\t\t<Cell>");
            alignBuffer.append("\n\t\t\t<entity1 rdf:resource=\"" + cp.getSub1() + "\"/>");
            alignBuffer.append("\n\t\t\t<entity2 rdf:resource=\"" + cp.getSub2() + "\"/>");
            alignBuffer.append("\n\t\t\t<measure rdf:datatype=\"xsd:float\">1.0</measure>");
            alignBuffer.append("\n\t\t\t<relation>=</relation>");
            alignBuffer.append("\n\t\t</Cell>");
            alignBuffer.append("\n\t</map>");
        }
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
