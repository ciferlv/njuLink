package cn.nju.ws.unit.alignment;

/**
 * Created by ciferlv on 17-6-8.
 */
public class CounterPart implements Comparable<CounterPart> {

    private String sub1, sub2, token;

    private int matchedNumDisOfSmall, unmatchedNumDisOfSmall, matchedNumUndisOfSmall;

    private int matchedNumDisOfBig, unmatchedNumDisOfBig, matchedNumUndisOfBig;

    private double infoGainSumDis, detailInfoGainSumDis, maxSimiSumDis, maxSimiSumUndis;

    public CounterPart() {

    }

    public CounterPart(String sub1, String sub2) {

        this.sub1 = sub1;
        this.sub2 = sub2;
        matchedNumUndisOfSmall = 0;
        infoGainSumDis = detailInfoGainSumDis = maxSimiSumDis = maxSimiSumUndis = 0;
        matchedNumDisOfSmall = unmatchedNumDisOfSmall = 0;
        if (sub1.compareTo(sub2) < 0) token = sub1 + sub2;
        else token = sub2 + sub1;
    }


    public CounterPart(String sub1, String sub2,
                       int matchedNumDisOfSmall, int unmatchedNumDisOfSmall,
                       double infoGainSumDis, double detailInfoGainSumDis,
                       double maxSimiSumDis) {

        this.sub1 = sub1;
        this.sub2 = sub2;
        this.matchedNumDisOfSmall = matchedNumDisOfSmall;
        this.unmatchedNumDisOfSmall = unmatchedNumDisOfSmall;
        this.infoGainSumDis = infoGainSumDis;
        this.detailInfoGainSumDis = detailInfoGainSumDis;
        this.maxSimiSumDis = maxSimiSumDis;
        matchedNumUndisOfSmall = 0;
        maxSimiSumUndis = 0;
        if (sub1.compareTo(sub2) < 0) token = sub1 + sub2;
        else token = sub2 + sub1;
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

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("sub1: " + sub1 + "\n");
        buffer.append("sub2: " + sub2 + "\n");

        if (matchedNumDisOfSmall != -1) buffer.append("matchedNumDisOfSmall: " + matchedNumDisOfSmall + "\n");

        if (unmatchedNumDisOfSmall != -1) buffer.append("unmatchedNumDisOfSmall: " + unmatchedNumDisOfSmall + "\n");

        buffer.append("infoGainSumDis: " + infoGainSumDis + "\n");
        buffer.append("detailInfoGainSumDis: " + detailInfoGainSumDis + "\n");
        buffer.append("maxSimiSumDis: " + maxSimiSumDis + "\n");
        buffer.append("matchedNumUndisOfSmall: " + matchedNumUndisOfSmall + "\n");
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

    public int getMatchedNumDisOfSmall() {
        return matchedNumDisOfSmall;
    }

    public int getUnmatchedNumDisOfSmall() {
        return unmatchedNumDisOfSmall;
    }


    public int getMatchedNumUndisOfSmall() {
        return matchedNumUndisOfSmall;
    }

    public void setMatchedNumUndisOfSmall(int matchedNumUndisOfSmall) {
        this.matchedNumUndisOfSmall = matchedNumUndisOfSmall;
    }

    public double getMaxSimiSumUndis() {
        return maxSimiSumUndis;
    }

    public void setMaxSimiSumUndis(double maxSimiSumUndis) {
        this.maxSimiSumUndis = maxSimiSumUndis;
    }

    public void setMatchedNumDisOfSmall(int matchedNumDisOfSmall) {
        this.matchedNumDisOfSmall = matchedNumDisOfSmall;
    }

    public void setUnmatchedNumDisOfSmall(int unmatchedNumDisOfSmall) {
        this.unmatchedNumDisOfSmall = unmatchedNumDisOfSmall;
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

    public void addMatchedNumDis(int add) {

        matchedNumDisOfSmall += add;
    }

    public void addUnmatchedNumDis(int add) {

        unmatchedNumDisOfSmall += add;
    }

    public void addMatchedNumUndis(int add) {

        matchedNumUndisOfSmall += add;
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
