package cn.nju.ws.utility;

import cn.nju.ws.unit.alignment.AlignmentSet;
import cn.nju.ws.unit.instance.Doc;
import cn.nju.ws.unit.predicatePair.PredPairList;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ciferlv on 17-6-5.
 */
public class ParamDef {

    public static final boolean isForFormalContest = false;
    //    The type of Obj
    public static final int URI_TYPE_INDEX = 0;
    public static final int STRING_TYPE_INDEX = 1;
    public static final int INTEGER_TYPE_INDEX = 2;
    public static final int FLOAT_TYPE_INDEX = 3;
    public static final int DATETIME_TYPE_INDEX = 4;
    public static final int THING_TYPE_INDEX = 5;
    public static final int BOOLEAN_TYPE_INDEX = 6;
    public static final int DATE_TYPE_INDEX = 7;
    public static final int GYEAR_TYPE_INDEX = 8;
    public static final int LANGSTRING_TYPE_INDEX = 9;
    public static final int GYEARMONTH_TYPE_INDEX = 10;

    //    The name of predicate
    public static final String TYPE_FULL_NAME = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String CLASS_TYPE = "http://www.w3.org/2002/07/owl#class";
    public static final String OBJECT_TYPE_PROPERTY = "http://www.w3.org/2002/07/owl#objectproperty";
    public static final String DATA_TYPE_PROPERTY = "http://www.w3.org/2002/07/owl#datatypeproperty";
    public static final String SUBCLASSOF_FULL_NAME = "http://www.w3.org/2000/01/rdf-schema#subclassof";


    public static final String STRING_TYPE = "http://www.w3.org/2001/XMLSchema#string";
    public static final String BOOLEAN_TYPE = "http://www.w3.org/2001/XMLSchema#boolean";
    public static final String DATETIME_TYPE = "http://www.w3.org/2001/XMLSchema#dateTime";
    public static final String DATE_TYPE = "http://www.w3.org/2001/XMLSchema#date";
    public static final String INTEGER_TYPE = "http://www.w3.org/2001/XMLSchema#int";
    public static final String FLOAT_TYPE = "http://www.w3.org/2001/XMLSchema#float";
    public static final String GYEAR_TYPE = "http://www.w3.org/2001/XMLSchema#gYear";
    public static final String LANGSTRING_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#langString";
    public static final String GYEARMONTH_TYPE = "http://www.w3.org/2001/XMLSchema#gYearMonth";

    //    use reinforce or not
    public static boolean useReinforce;
    public static boolean useAverageSimi;

    //    The size of align sample
    public static final double INITIAL_SAMPLE_PERSENT = 1;

    public static double infoGainThreshold = 0.2;

    public static double predPairSimiThreshold = 0.65;

    public static int predPairNumNeededThreshold = 3;

    public static String souPath;
    public static String tarPath;
    public static String refPath;
    public static String supp1_path;
    public static String supp2_path;
    public static String result_file_path;
    public static String metrics_file_path;
    public static String correct_result_file_path;
    public static String wrong_result_file_path;
    public static String unfound_result_file_path;
    public static String instance_set1_file_path;
    public static String instance_set2_file_path;
    public static String prop_pair_list_file_path;
    public static String stopwords_file_path = "./stopwords_more.txt";

    public static Set<String> souClassFilterSet = new HashSet<String>();
    public static Set<String> tarClassFilterSet = new HashSet<String>();

    public static Set<String> stopWordSet = new HashSet<String>();

    public static final int OBJECT_PROPERTY_INDEX = 8;
    public static final int DATA_PROPERTY_INDEX = 9;


    public static AlignmentSet refAlign = new AlignmentSet();
    public static AlignmentSet positives = new AlignmentSet();
    public static AlignmentSet negetives = new AlignmentSet();
    public static AlignmentSet tempAlign = new AlignmentSet();
    public static AlignmentSet resultAlign = new AlignmentSet();
    public static AlignmentSet testAlign = new AlignmentSet();

    public static Set<String> refSet = new HashSet<>();

    public static Doc souDoc = new Doc();
    public static Doc tarDoc = new Doc();
    public static PredPairList ppl = new PredPairList();

    public static String alignsStr = "";

    public static String alignHead, alignTail;
    public static StringBuffer alignBuffer = new StringBuffer();

}
