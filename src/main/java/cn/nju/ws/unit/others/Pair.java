package cn.nju.ws.unit.others;

/**
 * Created by ciferlv on 17-6-13.
 */
public class Pair {

    int matchedNumOfSmall, unmatchedNumSmall;
    double maxSimi, simiSum;


    public Pair(int matchedNumOfSmall, int unmatchedNumSmall,
                double maxSimi, double simiSum) {

        this.matchedNumOfSmall = matchedNumOfSmall;
        this.unmatchedNumSmall = unmatchedNumSmall;
        this.maxSimi = maxSimi;
        this.simiSum = simiSum;

    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("matchedNumOfSmall: " + matchedNumOfSmall + "\n");
        buffer.append("unmatchedNumSmall: " + unmatchedNumSmall + "\n");
        buffer.append("maxSimi: " + maxSimi + "\n");
        buffer.append("simiSum: " + simiSum + "\n");


        return String.valueOf(buffer);
    }

    public int getMatchedNumOfSmall() {
        return matchedNumOfSmall;
    }

    public int getUnmatchedNumSmall() {
        return unmatchedNumSmall;
    }

    public double getMaxSimi() {
        return maxSimi;
    }

    public double getSimiSum() {
        return simiSum;
    }
}
