package ecm.util.filtering;

import java.util.List;

/**
 * Created by dkarachurin on 10.02.2017.
 */
public class Rule {
    private Conditions op;
    private List<Field> data;

    public Rule() {
    }

    public Rule(Conditions op, List<Field> data) {
        this.op = op;
        this.data = data;
    }

    public Conditions getOp() {
        return op;
    }

    public void setOp(Conditions op) {
        this.op = op;
    }

    public List<Field> getData() {
        return data;
    }

    public void setData(List<Field> data) {
        this.data = data;
    }

    public Field getLeftField(){
        Field leftField = null;
        for (Field field : data) {
            if (field.isCol()) leftField = field;
        }
        return leftField;
    }
    public Field getRightField(){
        Field rightField = null;
        for (Field field : data) {
            if (!field.isCol()) rightField = field;
        }
        return rightField;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append(" e.")
                .append(getLeftField()+" ")
                .append(op+" :")
                .append((getLeftField()+" ").replaceAll("\\.",""))
                .toString();

    }
}
