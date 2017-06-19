package cn.nju.ws.unit.instSet;

import static cn.nju.ws.utility.ParamDef.*;

/**
 * Created by ciferlv on 17-6-5.
 */
public class Obj {

    private String value;

    private String localName;

    private String lang;

    private int type = -1;

    public Obj(String value, String localName, int type, String language) {

        this.value = value;
        this.type = type;
        this.localName = localName;
        this.lang = language;
    }

    public String getTypeName() {

        String mT = "NULL";
        if (type == URI_TYPE_INDEX) {
            mT = "URI";
        } else if (type == THING_TYPE_INDEX) {
            mT = "THING";
        } else if (type == INTEGER_TYPE_INDEX) {
            mT = "INTEGER";
        } else if (type == FLOAT_TYPE_INDEX) {
            mT = "FLOAT";
        } else if (type == STRING_TYPE_INDEX) {
            mT = "STRING";
        } else if (type == BOOLEAN_TYPE_INDEX) {
            mT = "BOOLEAN";
        } else if (type == DATE_TYPE_INDEX) {
            mT = "DATE";
        } else if (type == DATETIME_TYPE_INDEX) {
            mT = "DATETIME";
        } else if (type == GYEAR_TYPE_INDEX) {
            mT = "GYEAR";
        } else if (type == LANGSTRING_TYPE_INDEX) {
            mT = "LANGSTRING";
        } else if (type == GYEARMONTH_TYPE_INDEX) {
            mT = "GYEARMONTH";
        }
        return mT;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocalName() {
        return localName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
