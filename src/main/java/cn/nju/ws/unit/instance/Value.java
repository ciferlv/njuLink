package cn.nju.ws.unit.instance;

import org.semanticweb.owlapi.model.OWLDatatype;

import static cn.nju.ws.utility.ParamDef.*;
import static cn.nju.ws.utility.nlp.FormatData.formatWords;

/**
 * Created by ciferlv on 17-6-5.
 */
public class Value {

    private String literal;

    private String localName;

    private String lang = "";

    private OWLDatatype dataType;

    private boolean isURI, isAnonyId;

    public Value() {
    }

    public void setLiteral(String literal) {

        if (isAnonyId || isURI) this.literal = literal;
        else this.literal = formatWords(literal);
    }

    public void setLocalName(String localName) {

        this.localName = formatWords(localName);
    }

    @Override
    public String toString() {

        StringBuffer buffer = new StringBuffer();

        if (isURI) {

            buffer.append("type: URI\n");
            buffer.append("literal: " + literal + "\n");
            buffer.append("local name: " + localName + "\n");
        } else if (isAnonyId) {

            buffer.append("type: AnonyId\n");
            buffer.append("literal: " + literal + "\n");
        } else {

            buffer.append("type: " + dataType.getIRI().getFragment() + "\n");
            buffer.append("literal: " + literal + "\n");
            buffer.append("lang: " + lang + "\n");
        }
        return String.valueOf(buffer);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public OWLDatatype getDataType() {
        return dataType;
    }

    public void setDataType(OWLDatatype dataType) {
        this.dataType = dataType;
    }

    public boolean isURI() {
        return isURI;
    }

    public void setIsURI(boolean URI) {
        isURI = URI;
    }

    public boolean isAnonyId() {
        return isAnonyId;
    }

    public void setIsAnonID(boolean anonID) {
        isAnonyId = anonID;
    }

    public String getLocalName() {
        return localName;
    }

    public String getLiteral() {
        return literal;
    }
}
