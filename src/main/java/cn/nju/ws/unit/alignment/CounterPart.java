package cn.nju.ws.unit.alignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ciferlv on 17-6-8.
 */
public class CounterPart implements Comparable<CounterPart> {

    private static Logger logger = LoggerFactory.getLogger(CounterPart.class);
    private String sub1, sub2, token;

    private int matchedNumDis, unmatchedNumDis, matchedNumUndis, sameProp, diffProp;

    private double infoGainSumDis, detailInfoGainSumDis, maxSimiSumDis, maxSimiSumUndis;

    public CounterPart() {

    }

    public CounterPart(String sub1, String sub2) {

        this.sub1 = sub1;
        this.sub2 = sub2;
        matchedNumUndis = 0;
        infoGainSumDis = detailInfoGainSumDis = maxSimiSumDis = maxSimiSumUndis = 0;
        matchedNumDis = unmatchedNumDis = 0;
        token = sub1 + sub2;
//        if (sub1.compareTo(sub2) < 0) token = sub1 + sub2;
//        else token = sub2 + sub1;
    }


    public CounterPart(String sub1, String sub2,
                       int matchedNumDis, int unmatchedNumDis,
                       double infoGainSumDis, double detailInfoGainSumDis,
                       double maxSimiSumDis) {

        this.sub1 = sub1;
        this.sub2 = sub2;
        this.matchedNumDis = matchedNumDis;
        this.unmatchedNumDis = unmatchedNumDis;
        this.infoGainSumDis = infoGainSumDis;
        this.detailInfoGainSumDis = detailInfoGainSumDis;
        this.maxSimiSumDis = maxSimiSumDis;
        matchedNumUndis = 0;
        maxSimiSumUndis = 0;
        token = sub1 + sub2;
//        if (sub1.compareTo(sub2) < 0) token = sub1 + sub2;
//        else token = sub2 + sub1;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj instanceof CounterPart) {
            CounterPart cp = (CounterPart) obj;
            if (cp.getToken().equals(token)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {

        return token.hashCode();
    }

    @Override
    public int compareTo(CounterPart o) {

        int order = token.compareTo(o.getToken());
        if (order < 0) return -1;
        else if (order > 0) return 1;
        return 0;
    }

    public int compareWithAnotherCounterPart(CounterPart o2) {

        if (!sub1.equals(o2.getSub1())) {

            logger.info("Two CounterPart has different sub1!!!");
            return -10;
        }

        CounterPartComparator cpc = new CounterPartComparator();
        int res = cpc.compare(this, o2);

        return res;
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("sub1: " + sub1 + "\n");
        buffer.append("sub2: " + sub2 + "\n");

        if (matchedNumDis != -1) buffer.append("matchedNumDis: " + matchedNumDis + "\n");

        if (unmatchedNumDis != -1) buffer.append("unmatchedNumDis: " + unmatchedNumDis + "\n");

        buffer.append("infoGainSumDis: " + infoGainSumDis + "\n");
        buffer.append("detailInfoGainSumDis: " + detailInfoGainSumDis + "\n");
        buffer.append("maxSimiSumDis: " + maxSimiSumDis + "\n");
        buffer.append("matchedNumUndis: " + matchedNumUndis + "\n");
        buffer.append("maxSimiSumUndis: " + maxSimiSumUndis + "\n");
        return String.valueOf(buffer);
    }

    public synchronized String getSub1() {
        return sub1;
    }

    public synchronized String getSub2() {
        return sub2;
    }

    public double getInfoGainSumDis() {
        return infoGainSumDis;
    }

    public double getDetailInfoGainSumDis() {
        return detailInfoGainSumDis;
    }

    public double getMaxSimiSumDis() {
        return maxSimiSumDis;
    }

    public String getToken() {
        return token;
    }

    public int getMatchedNumDis() {
        return matchedNumDis;
    }

    public int getUnmatchedNumDis() {
        return unmatchedNumDis;
    }


    public int getMatchedNumUndis() {
        return matchedNumUndis;
    }

    public void setMatchedNumUndis(int matchedNumUndis) {
        this.matchedNumUndis = matchedNumUndis;
    }

    public double getMaxSimiSumUndis() {
        return maxSimiSumUndis;
    }

    public void setMaxSimiSumUndis(double maxSimiSumUndis) {
        this.maxSimiSumUndis = maxSimiSumUndis;
    }

    public void setMatchedNumDis(int matchedNumDis) {
        this.matchedNumDis = matchedNumDis;
    }

    public void setUnmatchedNumDis(int unmatchedNumDis) {
        this.unmatchedNumDis = unmatchedNumDis;
    }

    public void setInfoGainSumDis(double infoGainSumDis) {
        this.infoGainSumDis = infoGainSumDis;
    }

    public void setDetailInfoGainSumDis(double detailInfoGainSumDis) {
        this.detailInfoGainSumDis = detailInfoGainSumDis;
    }

    public void setMaxSimiSumDis(double maxSimiSumDis) {
        this.maxSimiSumDis = maxSimiSumDis;
    }

    public void setSameProp(int sameProp) {
        this.sameProp = sameProp;
    }

    public void setDiffProp(int diffProp) {
        this.diffProp = diffProp;
    }

    public int getSameProp() {
        return sameProp;
    }

    public int getDiffProp() {
        return diffProp;
    }

    public void addMatchedNumDis(int add) {

        matchedNumDis += add;
    }

    public void addUnmatchedNumDis(int add) {

        unmatchedNumDis += add;
    }

    public void addMatchedNumUndis(int add) {

        matchedNumUndis += add;
    }

    public void addInfoGainSumDis(double add) {

        infoGainSumDis += add;
    }

    public void addDetailInfoGainSumDis(double add) {

        detailInfoGainSumDis += add;
    }

    public void addMaxSimiSumDis(double add) {

        maxSimiSumDis += add;
    }

    public void addMaxSimiSumUndis(double add) {

        maxSimiSumUndis += add;
    }
}
