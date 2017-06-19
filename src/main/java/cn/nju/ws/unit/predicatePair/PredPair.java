package cn.nju.ws.unit.predicatePair;

/**
 * Created by ciferlv on 17-6-8.
 */
public class PredPair implements Comparable<PredPair> {

    private String pred1, pred2, token;

    private int time;
    private double infoGain;

    public PredPair(String pred1, String pred2) {

        this.pred1 = pred1;
        this.pred2 = pred2;
        time = 1;
        token = pred1 + pred2;
        infoGain = 0;
    }

    @Override
    public int compareTo(PredPair pp) {

        int order = token.compareTo(pp.getToken());
        if (order < 0) {

            return -1;
        } else if (order > 0) {

            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object pp) {

        if (pp instanceof PredPair) {
            if (((PredPair) pp).getToken().equals(this.token)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {

        return token.hashCode();
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("pred1: " + pred1 + "\n");
        buffer.append("pred2: " + pred2 + "\n");
        buffer.append("pred1: " + pred1.split("/")[pred1.split("/").length - 1] + "\n");
        buffer.append("pred2: " + pred2.split("/")[pred2.split("/").length - 1] + "\n");
//        buffer.append("token: " + token + "\n");
        buffer.append("time: " + time + "\n");
        buffer.append("infoGain: " + infoGain + "\n");

        return String.valueOf(buffer);
    }


    public String getToken() {
        return token;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getProp1() {
        return pred1;
    }

    public String getProp2() {
        return pred2;
    }

    public void setInfoGain(double infoGain) {
        this.infoGain = infoGain;
    }

    public double getInfoGain() {
        return infoGain;
    }

}
