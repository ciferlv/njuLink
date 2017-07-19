package cn.nju.ws.unit.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by ciferlv on 17-6-5.
 */
public class Doc {

    private Logger logger = LoggerFactory.getLogger(Doc.class);

    private Map<String, Inst> graph = Collections.synchronizedMap(new HashMap<String, Inst>());

    private List<String> tarSubList = Collections.synchronizedList(new ArrayList<String>());

    private Set<String> tarType;

    public Doc() {
    }

    public void processGraph() {

        filterTarSub();
//        reinforceGraph();
    }

    public void filterTarSub() {

        Iterator iter1 = graph.entrySet().iterator();
        while (iter1.hasNext()) {

            Map.Entry entry1 = (Map.Entry) iter1.next();

            String sub = (String) entry1.getKey();
            Inst inst = (Inst) entry1.getValue();

            Set<String> typeSet = inst.getTypeSet();

            for (String type : typeSet) {
                if (tarType.contains(type)) {
                    tarSubList.add(sub);
                    break;
                }
            }
        }
    }

    private void reinforceSub(Inst inst, String tarPred, String tarObj) {

        Inst tarInst = graph.get(tarObj);

        if (tarInst == null) return;

        Map<String, Set<Value>> propUri = tarInst.getPropUri();

        Iterator iterPropUri = propUri.entrySet().iterator();

        while (iterPropUri.hasNext()) {

            Map.Entry entry = (Map.Entry) iterPropUri.next();

            String prop = (String) entry.getKey();
            Set<Value> valueSet = (Set<Value>) entry.getValue();

            for (Value value : valueSet) {
                reinforceSub(tarInst, prop, value.getLiteral());
            }
        }

        propUri.clear();

        if (inst == null) return;

        Map<String, Set<Value>> propValue = tarInst.getPropVal();
        Iterator iter = propValue.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry entry = (Map.Entry) iter.next();
            String pred = (String) entry.getKey();
            Set<Value> valueSet = (Set<Value>) entry.getValue();

            for (Value value : valueSet) {

                String myPred = tarPred + '@' + pred;
//                inst.addObjToPred(value.getLiteral(), myPred, value.getLocalName(), value.getType(), value.getLang());
            }
        }
    }

    private void reinforceGraph() {

        for (String sub : tarSubList) {

            reinforceSub(null, "", sub);
        }
    }

//    public void addStmtToGraph(Resource sub, Property prop, RDFNode val) {
//
//        String subStr = sub.toString().toLowerCase();
//        String propStr = prop.toString().toLowerCase();
//
//        String valStr = "";
//        String valLocalName = "";
//        int typeIndex = THING_TYPE_INDEX;
//        String language = "";
//
//        if (val == null) {
//
//            logger.info("val is null");
//            logger.info("sub:" + subStr);
//            logger.info("propStr:" + propStr);
//            return;
//        }
//
//        if (val.isResource()) {
//
//            if (val.asResource().getURI() == null) {
//
////                valStr = valLocalName = val.toString();
////                if (valStr == "") {
////
////                    logger.info("valURI is null");
////                    logger.info("sub:" + subStr);
////                    logger.info("propStr:" + propStr);
////                    logger.info("valStr:" + valStr);
////                    return;
////                }
//
//                return;
//            } else {
//
//                valStr = val.asResource().getURI().toLowerCase();
////                valLocalName = val.asResource().getLocalName();
//
//                int i = valStr.length() - 1;
//
//                while (i >= 0) {
//
//                    char currentChar = valStr.charAt(i);
//                    if (currentChar == '/' || currentChar == '#') {
//
//                        if (valLocalName.equals("")) continue;
//                        else break;
//                    } else {
//
//                        valLocalName += currentChar;
//                    }
//                    i--;
//                }
//                valLocalName = new StringBuffer(valLocalName).reverse().toString();
//            }
////            if(valLocalName.equals("sy")) logger.info("*************************");
////            if (valLocalName.equals("") || valLocalName == null) {
////
////                String[] tempSplit = valStr.split("/");
////                valLocalName = tempSplit[tempSplit.length - 1];
////            }
//            valLocalName = formatWords(valLocalName);
//            typeIndex = URI_TYPE_INDEX;
//
//        } else if (val.isLiteral()) {
//
//            valStr = formatWords(val.asLiteral().getLexicalForm());
//
//            if (valStr.equals("")) {
//                return;
//            }
//
//            try {
//
//                String rtype = val.asLiteral().getDatatypeURI();
//
//                typeIndex = findTypeIndex(rtype);
//
//                language = val.asLiteral().getLanguage();
//            } catch (Exception e) {
//
//            }
//
//        }
//
//        if (typeIndex == THING_TYPE_INDEX) {
//
//            logger.info("sub:" + subStr);
//            logger.info("propStr:" + propStr);
//            logger.info(val.asLiteral().getDatatypeURI());
//            logger.info("********************NO TYPE****************");
//        }
//
//        if (graph.containsKey(subStr)) {
//
//            Inst myInst = graph.get(subStr);
//            myInst.addObjToPred(valStr, propStr, valLocalName, typeIndex, language);
//        } else {
//
//            graph.put(subStr, new Inst(subStr, propStr, valStr, valLocalName, typeIndex, language));
//        }
//
//    }

    public void addSubPropValToGraph(String sub, String prop, Value value) {

        String literal = value.getLiteral();
        if (literal == null || literal.equals("")) return;

        if (graph.containsKey(sub)) {

            Inst myInst = graph.get(sub);
            myInst.addPropValToInst(prop, value);
        } else {

            graph.put(sub, new Inst(sub, prop, value));
        }
    }

    public void addTypeToInst(String sub, String type) {

        if (graph.containsKey(sub)) {

            Inst myInst = graph.get(sub);

            myInst.addTypeToInst(type.toLowerCase());

        } else {

            graph.put(sub, new Inst(sub, type.toLowerCase()));
        }
    }

    public void addSameAsToInst(String sub1, String sub2) {

        if (graph.containsKey(sub1)) {

            Inst myInst = graph.get(sub1);
            myInst.addSameAsToInst(sub2);
        } else {

            Inst myInst = new Inst(sub1);
            myInst.addSameAsToInst(sub2);
            graph.put(sub1, myInst);
        }
    }

    public Map<String, Inst> getGraph() {
        return graph;
    }

    public String graphToString() {

        StringBuffer buffer = new StringBuffer();

        buffer.append("Instance number: " + graph.size() + "\n");
        buffer.append("tarSubList number: " + tarSubList.size() + "\n\n");

        Iterator graphIter = graph.entrySet().iterator();

        while (graphIter.hasNext()) {

            Map.Entry entry = (Map.Entry) graphIter.next();

            Inst inst = (Inst) entry.getValue();

            buffer.append(inst.toString() + "\n");
        }

        return String.valueOf(buffer);
    }

    public String tarInstToString() {

        StringBuffer buffer = new StringBuffer();

        for (String sub : tarSubList) {

            buffer.append("sub: " + sub + "\n");

            Inst inst = graph.get(sub);
            buffer.append(inst.toString() + "\n");
        }

        return String.valueOf(buffer);
    }

    public String tarSubToString() {

        StringBuffer buffer = new StringBuffer();

        for (String sub : tarSubList) {

            buffer.append("sub: " + sub + "\n");
            buffer.append("type:\n");

            for (String type : graph.get(sub).getTypeSet()) {

                buffer.append(type + "\n");
            }
            buffer.append("\n");
        }
        return String.valueOf(buffer);
    }

    public List<String> getTarSubList() {
        return tarSubList;
    }

    public Set<String> getTarType() {
        return tarType;
    }

    public void setTarType(Set<String> tarType) {
        this.tarType = tarType;
    }

    public Inst getInst(String key) {

        return graph.get(key);
    }
}
