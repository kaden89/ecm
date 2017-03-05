package ecm.util.filtering;

import com.google.gson.annotations.SerializedName;

/**
 * Условия запроса приходящие с клиента
 * @author dkarachurin
 */

public enum Conditions {
    @SerializedName("and")
    AND("AND"),
    @SerializedName("or")
    OR("OR"),
    @SerializedName("not")
    NOT("NOT"),
    @SerializedName("equal")
    EQUAL("="),
    @SerializedName("greater")
    GREATER(">"),
    @SerializedName("less")
    LESS("<"),
    @SerializedName("greaterEqual")
    GREATER_EQUAL(">="),
    @SerializedName("lessEqual")
    LESS_EQUAL("<="),
    @SerializedName("match")
    MATCH("IN"),
    @SerializedName("contain")
    CONTAIN("LIKE"),
    @SerializedName("startWith")
    START_WITH("like CONCAT(?2, '%')"),
    @SerializedName("endWith")
    END_WITH("AND");

    private final String text;

    Conditions(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
