package cn.nju.ws.utility.nlp;

import cn.nju.ws.unit.instance.Value;
import cn.nju.ws.unit.others.Pair;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static int getTheWayToCompByDataType(Value val1, Value val2) {

        int res = -1;

        if (val1.isURI() && val2.isURI()) {

            res = 0;
        } else if (!val1.isURI() && !val2.isURI() && !val1.isAnonyId() && !val2.isAnonyId()) {

            OWLDatatype datatype1 = val1.getDataType();
            OWLDatatype datatype2 = val2.getDataType();

            String type1IRIStr = datatype1.getIRI().toString();
            String type2IRIStr = datatype2.getIRI().toString();

            if (type1IRIStr.equals(type2IRIStr)) {

                if (type1IRIStr.equals(PLAINLITERAL_TYPE)) res = 2;
                else res = 1;
            }
        }
        return res;
    }

    public static Pair calObjSetSim(Set<Value> value1Set, Set<Value> value2Set) {

        int matchedNumObj1Set = 0, unmatchedNumObj1Set = 0;
        double maxSimi = -1.0, simiSum = 0;//相似度之合

        for (Value val1 : value1Set) {

            double eachVal1TempSimi = 0, eachVal1MaxSimi = 0;
            for (Value val2 : value2Set) {

                String lang1 = val1.getLang();
                String lang2 = val2.getLang();

                if (lang1 != null && lang2 != null && !lang1.equals(lang2)) {

                    eachVal1TempSimi = 0;
                } else {

                    int comWay = getTheWayToCompByDataType(val1, val2);

                    if (comWay == 0) {

                        eachVal1TempSimi = strFunc(val1.getLocalName(), val2.getLocalName());
                    } else if (comWay == 1) {

                        eachVal1TempSimi = indiFunc(val1.getLiteral(), val2.getLiteral());
                    } else if (comWay == 2) {
                        eachVal1TempSimi = strFunc(val1.getLiteral(), val2.getLiteral());
                    }
                }

                maxSimi = Math.max(maxSimi, eachVal1TempSimi);
                eachVal1MaxSimi = Math.max(eachVal1MaxSimi, eachVal1TempSimi);

            }

            if (eachVal1MaxSimi > predPairSimiThreshold) {
                matchedNumObj1Set++;
                simiSum += eachVal1MaxSimi;
            } else unmatchedNumObj1Set++;
        }

        return new Pair(matchedNumObj1Set, unmatchedNumObj1Set, maxSimi, simiSum);
    }
}
