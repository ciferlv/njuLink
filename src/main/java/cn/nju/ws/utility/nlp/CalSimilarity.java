package cn.nju.ws.utility.nlp;

import cn.nju.ws.unit.others.Pair;
import cn.nju.ws.unit.instance.Obj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static cn.nju.ws.utility.nlp.EditDistance.editDistance;
import static cn.nju.ws.utility.nlp.I_SUB.I_SUBScore;
import static cn.nju.ws.utility.ParamDef.*;

/**
 * Created by ciferlv on 17-6-6.
 */
public class CalSimilarity {

    private static Logger logger = LoggerFactory.getLogger(CalSimilarity.class);

    //指示函数
    private static double indiFunc(String str1, String str2) {

        if (str1.equals("") || str2.equals("") || str1 == null || str2 == null) {

            logger.info("ERROR:  indiFunc receive the wrong parameter.");
            return 0;
        }

        if (str1.equals(str2)) {
            return 1;
        } else return 0;
    }

    public static double strFunc(String str1, String str2) {

        if (str1.equals("") || str2.equals("") || str1 == null || str2 == null) {

            logger.info("ERROR: strFunc receive the wrong parameter.");
            return 0;
        }

        if (str1.length() < 2 || str2.length() < 2) {

            return editDistance(str1, str2);
        } else {
            return I_SUBScore(str1, str2);
        }
    }


    public static int compType(int obj1Type, int obj2Type) {

        int res = -1;
        if (obj1Type == obj2Type) {

            if (obj1Type == INTEGER_TYPE_INDEX
                    || obj1Type == FLOAT_TYPE_INDEX
                    || obj1Type == DATETIME_TYPE_INDEX
                    || obj1Type == DATE_TYPE_INDEX
                    || obj1Type == BOOLEAN_TYPE_INDEX
                    || obj1Type == GYEAR_TYPE_INDEX
                    || obj1Type == GYEARMONTH_TYPE_INDEX) {
                res = 0;
            } else if (obj1Type == THING_TYPE_INDEX
                    || obj1Type == STRING_TYPE_INDEX
                    || obj1Type == LANGSTRING_TYPE_INDEX
                    || obj1Type == URI_TYPE_INDEX) {
                res = 1;
            }
        } else {
            if (obj1Type == THING_TYPE_INDEX
                    || obj2Type == THING_TYPE_INDEX
                    || (obj1Type == STRING_TYPE_INDEX && obj2Type == LANGSTRING_TYPE_INDEX)
                    || (obj2Type == STRING_TYPE_INDEX && obj1Type == LANGSTRING_TYPE_INDEX)) {
                res = 1;
            }
        }
        return res;
    }

    public static Pair calObjSetSim(Set<Obj> obj1Set, Set<Obj> obj2Set) {

        int matchedNumObj1Set = 0, unmatchedNumObj1Set = 0;
        double maxSimi = -1.0, simiSum = 0;//相似度之合

        Set<String> unMatchedInObj2Set = new HashSet<String>();

        for (Obj obj1 : obj1Set) {

            double eachObj1TempSimi = 0;
            double eachObj1MaxSimi = 0;
            for (Obj obj2 : obj2Set) {

                int obj1Type = obj1.getType();
                int obj2Type = obj2.getType();
                String value1 = new String(obj1.getValue());
                String value2 = new String(obj2.getValue());

                String lang1 = obj1.getLang();
                String lang2 = obj2.getLang();

                if (value1 == ""
                        || value1 == null
                        || value2 == ""
                        || value2 == null) {
                    logger.info("value1 or value2 is empty.");
                    continue;
                }

                if (!lang1.equals("") && !lang2.equals("") && !lang1.equals(lang2)) {

                    eachObj1TempSimi = 0;
                } else {

                    int ct = compType(obj1Type, obj2Type);

                    if (ct == 0) {
                        eachObj1TempSimi = indiFunc(obj1.getValue(), obj2.getValue());

                    } else if (ct == 1) {

                        if (obj1Type == URI_TYPE_INDEX && obj2Type == URI_TYPE_INDEX) {
                            eachObj1TempSimi = strFunc(obj1.getLocalName(), obj2.getLocalName());
                        } else if (obj1Type == URI_TYPE_INDEX && obj2Type == THING_TYPE_INDEX) {
                            eachObj1TempSimi = strFunc(obj1.getLocalName(), obj2.getValue());
                        } else if (obj1Type == THING_TYPE_INDEX && obj2Type == URI_TYPE_INDEX) {
                            eachObj1TempSimi = strFunc(obj1.getValue(), obj2.getLocalName());
                        } else {
                            eachObj1TempSimi = strFunc(obj1.getValue(), obj2.getValue());
                        }
                    }
                }

                maxSimi = Math.max(maxSimi, eachObj1TempSimi);
                eachObj1MaxSimi = Math.max(eachObj1MaxSimi, eachObj1TempSimi);

            }

            if (eachObj1MaxSimi > predPairSimiThreshold) {
                matchedNumObj1Set++;
                simiSum += eachObj1MaxSimi;
            } else unmatchedNumObj1Set++;
        }

        return new Pair(matchedNumObj1Set, unmatchedNumObj1Set, maxSimi, simiSum);
    }
}
