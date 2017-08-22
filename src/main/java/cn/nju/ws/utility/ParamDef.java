package cn.nju.ws.utility;

import cn.nju.ws.unit.alignment.AlignmentList;
import cn.nju.ws.unit.instance.Doc;
import cn.nju.ws.unit.predicatePair.PredPairList;
import org.semanticweb.owlapi.model.OWLDatatype;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ciferlv on 17-6-5.
 */
public class ParamDef {

    public static final boolean isForFormalContest = true;

    //    The name of predicate
    public static final String TYPE_FULL_NAME = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    public static final String CLASS_TYPE = "http://www.w3.org/2002/07/owl#class";
    public static final String OBJECT_TYPE_PROPERTY = "http://www.w3.org/2002/07/owl#objectproperty";
    public static final String DATA_TYPE_PROPERTY = "http://www.w3.org/2002/07/owl#datatypeproperty";
    public static final String SUBCLASSOF_FULL_NAME = "http://www.w3.org/2000/01/rdf-schema#subclassof";


    public static final String BOOLEAN_TYPE = "http://www.w3.org/2001/XMLSchema#boolean";
    public static final String DATETIME_TYPE = "http://www.w3.org/2001/XMLSchema#dateTime";
    public static final String DATE_TYPE = "http://www.w3.org/2001/XMLSchema#date";
    public static final String INT_TYPE = "http://www.w3.org/2001/XMLSchema#int";
    public static final String FLOAT_TYPE = "http://www.w3.org/2001/XMLSchema#float";
    public static final String GYEAR_TYPE = "http://www.w3.org/2001/XMLSchema#gYear";
    public static final String GYEARMONTH_TYPE = "http://www.w3.org/2001/XMLSchema#gYearMonth";
    public static final String PLAINLITERAL_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral";

//    public static final String STRING_TYPE = "http://www.w3.org/2001/XMLSchema#string";
//    public static final String LANGSTRING_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#langString";

    //    use reinforce or not
    public static boolean useReinforce;
    public static boolean useAverageSimi;

    //    The size of align sample
    public static final double INITIAL_SAMPLE_PERSENT = 1;

    public static double infoGainThreshold = 0.2;

    public static double predPairSimiThreshold = 0.65;

//    public static int predPairNumNeededThreshold = 4;

    public static String souPath;
    public static String tarPath;
    public static String refPath;
    public static String stopwords_file_path = "./stopwords_more.txt";

    public static Set<String> souClassFilterSet = new HashSet<String>();
    public static Set<String> tarClassFilterSet = new HashSet<String>();

    public static Set<String> stopWordSet = new HashSet<String>();

    public static AlignmentList refAlign = new AlignmentList();
    public static AlignmentList positives = new AlignmentList();
    public static AlignmentList negetives = new AlignmentList();
    public static AlignmentList tempAlign = new AlignmentList();
    public static AlignmentList resultAlign = new AlignmentList();
    public static AlignmentList testAlign = new AlignmentList();

    public static Set<String> refSet = new HashSet<>();

    public static Doc souDoc = new Doc();
    public static Doc tarDoc = new Doc();
    public static PredPairList ppl = new PredPairList();

    public static String alignsStr = "";

    public static String alignHead, alignTail;
    public static StringBuffer alignBuffer = new StringBuffer();

    public static Set<OWLDatatype> recordDataType = new HashSet<>();
    public static Set<String> recordAxiomType = new HashSet<>();

}
