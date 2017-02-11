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

    public Field getLeftField() {
        Field leftField = null;
        for (Field field : data) {
            if (field.isCol()) leftField = field;
        }
        return leftField;
    }

    public Field getRightField() {
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
                .append(getLeftField() + " ")
                .append(op + " :")
                .append((getLeftField() + " ").replaceAll("\\.", ""))
                .toString();

    }

    public String getCaseInsensitiveString(Class clazz) {
        StringBuilder builder = new StringBuilder();

        String paramName = getLeftField().getData();

        if (isFieldAString(paramName, clazz) || isFieldAString(paramName, clazz.getSuperclass())) {
            return builder.append(" LOWER(e.")
                    .append(getLeftField() + ") ")
                    .append(op==Conditions.CONTAIN ? op + " CONCAT('%', :": op + " :")
                    .append((getLeftField() + " ").replaceAll("\\.", ""))
                    .append(op==Conditions.CONTAIN ? ", '%')": "")
                    .toString();
        } else return toString();
    }

    private boolean isFieldAString(String paramName, Class clazz) {
        if (paramName.contains(".")){
            try {
                 Class childClass = clazz.getDeclaredField(paramName.split("\\.")[0]).getType();
                 return isFieldAString(paramName.split("\\.")[1], childClass);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(paramName)) {
                Class paramClass = field.getType();
                return paramClass == String.class;
            }

        }
        return false;
    }
}
