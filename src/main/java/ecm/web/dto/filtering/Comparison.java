package ecm.web.dto.filtering;

import com.google.gson.annotations.SerializedName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by dkarachurin on 10.02.2017.
 */
//and, or, not, equal, greater, less, greaterEqual, lessEqual, match, contain, startWith, endWith
@XmlType(name="Comparison")
@XmlEnum(String.class)
public enum Comparison{
    @XmlEnumValue("and")
    @SerializedName("and")
    AND("AND"),
    @XmlEnumValue("or")
    @SerializedName("or")
    OR("OR"),
    @XmlEnumValue("not")
    @SerializedName("not")
    NOT("NOT"),
    @XmlEnumValue("equal")
    @SerializedName("equal")
    EQUAL("="),
    @SerializedName("greater")
    @XmlEnumValue("greater")
    GREATER(">"),
    @SerializedName("less")
    @XmlEnumValue("less")
    LESS("<"),
    @SerializedName("greaterEqual")
    @XmlEnumValue("greaterEqual")
    GREATER_EQUAL(">="),
    @SerializedName("lessEqual")
    @XmlEnumValue("lessEqual")
    LESS_EQUAL("<="),
    @SerializedName("match")
    @XmlEnumValue("match")
    MATCH("LIKE"),
    @SerializedName("contain")
    @XmlEnumValue("contain")
    CONTAIN("IN"),
    @SerializedName("startWith")
    @XmlEnumValue("startWith")
    START_WITH("like CONCAT(?2, '%')"),
    @SerializedName("endWith")
    @XmlEnumValue("endWith")
    END_WITH("AND");

    private final String text;

    Comparison(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
