package cn.nju.ws.unit.instance;

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

    private Map<String, Set<Value>> propVal = Collections.synchronizedMap(new HashMap<String, Set<Value>>());

    private Set<String> typeSet = new HashSet<>();

    private Set<String> sameAsSet = new HashSet<>();

    //store the objects whose type are URI type
    private Map<String, Set<Value>> propUri = Collections.synchronizedMap(new HashMap<String, Set<Value>>());

    public Inst(String sub) {

        this.sub = sub;
    }

    public Inst(String sub, String prop, Value value) {

        this.sub = sub;

        addPropValToInst(prop, value);
    }

    public Inst(String sub, String type) {

        this.sub = sub;
        addTypeToInst(type);
    }

    public Set<Value> getValSetByProp(String pred) {

        Set<Value> tempValueSet = propVal.get(pred);
        if (tempValueSet == null) {
            return propUri.get(pred);
        } else return tempValueSet;
    }

    public void addPropValToInst(String prop, Value value) {

        Map<String, Set<Value>> ptr;

        if (useReinforce) {
            if (value.isURI()) ptr = propUri;
            else ptr = propVal;
        } else ptr = propVal;

        if (ptr.containsKey(prop)) {

            Set<Value> myValueSet = ptr.get(prop);
            myValueSet.add(value);
        } else {

            Set<Value> myValueSet = new HashSet<>();
            myValueSet.add((value));
            ptr.put(prop, myValueSet);
        }

    }

    public void addTypeToInst(String type) {

        typeSet.add(type);
    }

    public void addSameAsToInst(String sameAsIndividual) {

        sameAsSet.add(sameAsIndividual);
    }

    @Override
    public String toString() {

        StringBuffer out = new StringBuffer("sub: ");

        out.append(sub);
        out.append("\n");

        for (String key : propVal.keySet()) {

            out.append("pred: " + key.split("/")[key.split("/").length - 1] + "\n");

            Set<Value> myValueSet = propVal.get(key);

            for (Value myValue : myValueSet) {

                out.append(myValue.toString());
            }
            out.append("\n");
        }

        for (String key : propUri.keySet()) {

            out.append("pred: " + key + "\n");

            Set<Value> myValueSet = propUri.get(key);

            for (Value myValue : myValueSet) {

                out.append(myValue.toString());
            }
            out.append("\n");
        }

        if (sameAsSet.size() > 0) out.append("sameAs:\n");
        for (String sameAsIndividual : sameAsSet) {

            out.append(sameAsIndividual + "\n");
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

    public Map<String, Set<Value>> getPropVal() {
        return propVal;
    }

    public Set<String> getTypeSet() {
        return typeSet;
    }

    public Map<String, Set<Value>> getPropUri() {
        return propUri;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean hasSameAs(){

        if(sameAsSet.size() > 0) return true;
        else return false;
    }

    public boolean hasType(){

        if(typeSet.size()> 0) return true;
        else return false;
    }
}
