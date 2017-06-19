package cn.nju.ws.unit.alignSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static cn.nju.ws.utility.ParamDef.resultAlign;

/**
 * Created by ciferlv on 17-6-13.
 */
public class AlignmentMap {

    private Logger logger = LoggerFactory.getLogger(AlignmentMap.class);
    private static Map<String, CounterPart> alignMapSub1 = Collections.synchronizedMap(new HashMap<String, CounterPart>());
    private static Map<String, CounterPart> alignMapSub2 = Collections.synchronizedMap(new HashMap<String, CounterPart>());

    private boolean matchJudge(int matchedCnt, int unmatchCnt,
                               double infoGainSum, double detailInfoGainSum,
                               double maxSimiSum, CounterPart cpToComp) {

        if (infoGainSum > cpToComp.getInfoGainSumDis()) {

            return true;
        } else if (infoGainSum == cpToComp.getInfoGainSumDis()) {

            if (detailInfoGainSum > cpToComp.getDetailInfoGainSumDis()) {

                return true;
            } else if (detailInfoGainSum == cpToComp.getDetailInfoGainSumDis()) {

                if (maxSimiSum > cpToComp.getMaxSimiSumDis()) {

                    return true;
                }
            }
        }
        return false;
    }

    public void generateAlign() {


        Iterator alignMapIter = alignMapSub1.entrySet().iterator();

        while (alignMapIter.hasNext()) {

            Map.Entry entry = (Map.Entry) alignMapIter.next();
            String sub1 = (String) entry.getKey();
            CounterPart cp1 = (CounterPart) entry.getValue();
            CounterPart cp2 = alignMapSub2.get(cp1.getSub2());
            if (cp2.getSub2().equals(sub1)) resultAlign.addCounterPart(cp1);
            else {
                logger.info("InstSet1:");
                logger.info("sub1:" + cp1.getSub1());
                logger.info("sub2:" + cp1.getSub2());
                logger.info("InstSet2:");
                logger.info("sub2:" + cp2.getSub1());
                logger.info("sub1:" + cp2.getSub2());
            }
        }
    }

    public void add(String sub1, String sub2,
                    int matchedCnt, int unmatchCnt,
                    double infoGainSum, double detailInfoGainSum,
                    double maxSimiSum) {

        CounterPart cp1 = alignMapSub1.get(sub1);
        CounterPart cp2 = alignMapSub2.get(sub2);

//        寻找最佳匹配项
        if (cp1 == null || matchJudge(matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum, cp1)) {

            alignMapSub1.put(sub1, new CounterPart(sub1, sub2, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum));
        }

        if (cp2 == null || matchJudge(matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum, cp2)) {

            alignMapSub2.put(sub2, new CounterPart(sub2, sub1, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum));
        }

//        方法2
//        if (cp1 == null && cp2 == null) {
//
//            addToAlignMap(sub1, sub2, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum);
//        } else if (cp1 != null && cp2 == null) {
//
//            if (matchJudge(matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum, cp1)) {
//
//                removeFromAlignMap(cp1.getSub1(), cp1.getSub2());
//                addToAlignMap(sub1, sub2, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum);
//            }
//        } else if (cp1 == null && cp2 != null) {
//
//            if (matchJudge(matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum, cp2)) {
//
//                removeFromAlignMap(cp2.getSub2(), cp2.getSub1());
//                addToAlignMap(sub1, sub2, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum);
//            }
//        } else {
//
////            if (cp1.getSub2().equals(cp2.getSub1())) {
////                logger.info("find same");
////                logger.info("sub1:" + cp1.getSub1());
////                logger.info("sub2:" + cp1.getSub2());
////                return;
////            }
//
//            if (matchJudge(matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum, cp1)) {
//
//                removeFromAlignMap(cp1.getSub1(), cp1.getSub2());
//                alignMapSub1.put(sub1, new CounterPart(sub1, sub2, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum));
//            }
//            if (matchJudge(matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum, cp2)) {
//
//                removeFromAlignMap(cp2.getSub2(), cp2.getSub1());
//                alignMapSub2.put(sub2, new CounterPart(sub2, sub1, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum));
//
//            }
//        }
    }

    private void addToAlignMap(String sub1, String sub2, int matchedCnt, int unmatchCnt, double infoGainSum, double detailInfoGainSum, double maxSimiSum) {

//        logger.info("add: ");
//        logger.info("sub1: " + sub1);
//        logger.info("sub2: " + sub2);
        alignMapSub1.put(sub1, new CounterPart(sub1, sub2, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum));
        alignMapSub2.put(sub2, new CounterPart(sub2, sub1, matchedCnt, unmatchCnt, infoGainSum, detailInfoGainSum, maxSimiSum));
    }

    private void removeFromAlignMap(String sub1, String sub2) {

//        logger.info("remove:");
        if (alignMapSub1.containsKey(sub1)) {

//            logger.info("sub1:" + sub1);
            alignMapSub1.remove(sub1);
        } else {
//            logger.info("ERROR: alignMapSub1 does't have " + sub1);
        }
        if (alignMapSub2.containsKey(sub2)) {

//            logger.info("sub2: " + sub2);
            alignMapSub2.remove(sub2);
        } else {
//            logger.info("ERROR: alignMapSub2 does't have " + sub2);
        }
    }

    public static Map<String, CounterPart> getAlignMapSub1() {
        return alignMapSub1;
    }
}
