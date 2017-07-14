package cn.nju.ws.unit.others;

/**
 * Created by ciferlv on 17-6-13.
 */
public class Pair {

    int matchedNumOfValSet1, unmatchedNumOfValSet1, matchedNumOfValSet2, unmatchedNumOfValSet2;
    double maxSimi, simiSumOfValSet1, simiSumOfValSet2;

    public Pair(int matchedNumOfValSet1, int unmatchedNumOfValSet1,
                int matchedNumOfValSet2, int unmatchedNumOfValSet2,
                double maxSimi, double simiSumOfValSet1, double simiSumOfValSet2) {

        this.matchedNumOfValSet1 = matchedNumOfValSet1;
        this.unmatchedNumOfValSet1 = unmatchedNumOfValSet1;
        this.matchedNumOfValSet2 = matchedNumOfValSet2;
        this.unmatchedNumOfValSet2 = unmatchedNumOfValSet2;
        this.maxSimi = maxSimi;
        this.simiSumOfValSet1 = simiSumOfValSet1;
        this.simiSumOfValSet2 = simiSumOfValSet2;
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("matchedNumOfValSet1: " + matchedNumOfValSet1 + "\n");
        buffer.append("unmatchedNumOfValSet1: " + unmatchedNumOfValSet1 + "\n");
        buffer.append("matchedNumOfValSet2: " + matchedNumOfValSet2 + "\n");
        buffer.append("unmatchedNumOfValSet2: " + unmatchedNumOfValSet2 + "\n");
        buffer.append("maxSimi: " + maxSimi + "\n");
        buffer.append("simiSumOfValSet1: " + simiSumOfValSet1 + "\n");


        return String.valueOf(buffer);
    }

    public int getMatchedNumOfValSet1() {
        return matchedNumOfValSet1;
    }

    public int getUnmatchedNumOfValSet1() {
        return unmatchedNumOfValSet1;
    }

    public double getMaxSimi() {
        return maxSimi;
    }

    public double getSimiSumOfValSet1() {
        return simiSumOfValSet1;
    }

    public int getMatchedNumOfValSet2() {
        return matchedNumOfValSet2;
    }

    public int getUnmatchedNumOfValSet2() {
        return unmatchedNumOfValSet2;
    }

    public double getSimiSumOfValSet2() {
        return simiSumOfValSet2;
    }
}
