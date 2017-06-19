package cn.nju.ws.unit.instSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static cn.nju.ws.utility.ParamDef.*;

/**
 * Created by ciferlv on 17-6-5.
 */
public class Inst {

    private Logger logger = LoggerFactory.getLogger(Inst.class);

    private String sub;

    private Map<String, Set<Obj>> predObj = Collections.synchronizedMap(new HashMap<String, Set<Obj>>());

    private Set<String> typeSet = new HashSet<String>();

    //store the objects whose type are URI type
    private Map<String, Set<Obj>> predUri = Collections.synchronizedMap(new HashMap<String, Set<Obj>>());


    public Inst(String sub, String pred, String obj, String localName, int objType, String language) {

        this.sub = sub;

        addObjToPred(obj, pred, localName, objType, language);
    }


    public Set<Obj> getObjSetByPred(String pred) {

        Set<Obj> tempObjSet = predObj.get(pred);
        if (tempObjSet == null) {
            return predUri.get(pred);
        } else return tempObjSet;
    }

    public synchronized void addObjToPred(String myObj, String myPred, String localName, int objType, String language) {

        if (myObj.equals("") || myPred.equals("")) return;

        if (myPred.equals(TYPE_FULL_NAME)) {

            typeSet.add(myObj);
        } else {

            Map<String, Set<Obj>> ptr;

            if (use_reinforce) {
                if (objType == URI_TYPE_INDEX) {
                    ptr = predUri;
                } else {
                    ptr = predObj;
                }
            } else {
                ptr = predObj;
            }

            if (ptr.containsKey(myPred)) {

                Set<Obj> myValueSet = ptr.get(myPred);
                myValueSet.add(new Obj(myObj, localName, objType, language));
            } else {

                Set<Obj> mySet = new HashSet<>();
                mySet.add(new Obj(myObj, localName, objType, language));
                ptr.put(myPred, mySet);
            }
        }
    }

    @Override
    public String toString() {

        StringBuffer out = new StringBuffer("sub: ");

        out.append(sub);
        out.append("\n");

        for (String key : predObj.keySet()) {

            out.append("pred: " + key.split("/")[key.split("/").length - 1] + "\n");

            Set<Obj> myValueSet = predObj.get(key);

            for (Obj myValue : myValueSet) {

                out.append("obj: " + myValue.getValue() + "\n");

                if (myValue.getType() == URI_TYPE_INDEX) {
                    out.append("obj localname: " + myValue.getLocalName() + "\n");
                }
                out.append("obj type: " + myValue.getTypeName() + "\n");

                if (!myValue.getLang().equals("")) {
                    out.append("obj language: " + myValue.getLang() + "\n");
                }
            }
            out.append("\n");
        }

        for (String key : predUri.keySet()) {

            out.append("pred: " + key + "\n");

            Set<Obj> myValueSet = predUri.get(key);

            for (Obj myObj : myValueSet) {

                out.append("obj: " + myObj.getValue() + "\n");
                if (myObj.getType() == URI_TYPE_INDEX) {
                    out.append("obj localname: " + myObj.getLocalName() + "\n");
                }

                if (!myObj.getLang().equals("")) {
                    out.append("obj language: " + myObj.getLang() + "\n");
                }
            }
            out.append("\n");
        }

        out.append("pred: type\n");
        for (String myType : typeSet) {

            out.append("value: " + myType + "\n");
        }

        return String.valueOf(out);
    }

    public String getSub() {
        return sub;
    }

    public Map<String, Set<Obj>> getPredObj() {
        return predObj;
    }

    public Set<String> getTypeSet() {
        return typeSet;
    }

    public Map<String, Set<Obj>> getPredUri() {
        return predUri;
    }
}
