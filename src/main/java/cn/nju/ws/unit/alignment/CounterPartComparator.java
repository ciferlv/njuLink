package cn.nju.ws.unit.alignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Created by ciferlv on 17-6-15.
 */
public class CounterPartComparator implements Comparator<CounterPart> {

    private Logger logger = LoggerFactory.getLogger(CounterPartComparator.class);

    @Override
    public int compare(CounterPart o1, CounterPart o2) {

        if (o1.getMatchedNumDisOfSmall() > o2.getMatchedNumDisOfSmall()) {

            return -1;
        } else if (o1.getMatchedNumDisOfSmall() == o2.getMatchedNumDisOfSmall()) {

            if (o1.getInfoGainSumDis() > o2.getInfoGainSumDis()) {

                return -1;
            } else if (o1.getInfoGainSumDis() == o2.getInfoGainSumDis()) {

                if (o1.getDetailInfoGainSumDis() > o2.getDetailInfoGainSumDis()) {

                    return -1;
                } else if (o1.getDetailInfoGainSumDis() == o2.getDetailInfoGainSumDis()) {

                    if (o1.getMaxSimiSumDis() > o2.getMaxSimiSumDis()) {

                        return -1;
                    } else if (o1.getMaxSimiSumDis() == o2.getMaxSimiSumDis()) {

                        if (o1.getMatchedNumUndisOfSmall() > o2.getMatchedNumUndisOfSmall()) {

                            return -1;
                        } else if (o1.getMatchedNumUndisOfSmall() == o2.getMatchedNumUndisOfSmall()) {

                            if (o1.getMaxSimiSumUndis() > o2.getMaxSimiSumUndis()) {

                                return -1;
                            } else if (o1.getMaxSimiSumUndis() == o2.getMaxSimiSumUndis()) {

                                if (o1.getMaxSimiSumUndis() > o2.getMaxSimiSumUndis()) {

                                    return -1;
                                } else if (o1.getMaxSimiSumUndis() == o2.getMaxSimiSumUndis()) {

                                    return 0;
                                }
                            }
                        }
                    }
                }
            }
        }
        return 1;
    }
}
