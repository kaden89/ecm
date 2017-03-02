package ecm.util.filtering;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
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

    public Predicate getPredicate(CriteriaBuilder cb, Root root, Class entityClass){
        Predicate predicate = null;
        switch (op){
            case EQUAL:
                predicate = cb.equal(
                        getCriteriaPath(root, getLeftField().toString()),
                        getClassCastedParam(getLeftField().toString(), getRightField().getData(), entityClass));
                break;
            case CONTAIN:
                predicate = cb.like(
                        cb.lower(getCriteriaPath(root, getLeftField().toString())),
                        getRightField().getData().toLowerCase());
                break;
        }
        return predicate;
    }

    private Object getClassCastedParam(String paramName, Object param, Class entityClass) {

        Object result = null;
        try {
            //Check superclass fields
            result = castParamToFieldClassType(Class.forName(entityClass.getCanonicalName()).getSuperclass(), paramName, param);
            if (result == null) {
                //Check our class fields
                result = castParamToFieldClassType(Class.forName(entityClass.getCanonicalName()), paramName, param);
            }

        } catch (ClassNotFoundException e) {
//            log.warning(e.getMessage());
        }
        return result;
    }

    private Object castParamToFieldClassType(Class clazz, String paramName, Object param) {
        if (paramName.contains(".")) {
            try {
                Class childClass = clazz.getDeclaredField(paramName.split("\\.")[0]).getType();
                return castParamToFieldClassType(childClass, paramName.split("\\.")[1], param);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(paramName)) {
                Class paramClass = field.getType();
                if (paramClass == String.class) {
                    return String.valueOf(param);
                } else if (paramClass == Integer.class) {
                    return Integer.parseInt(String.valueOf(param));
                } else if (paramClass == LocalDate.class) {
                    return LocalDate.parse(String.valueOf(param));
                } else if (paramClass == boolean.class) {
                    return Boolean.valueOf(String.valueOf(param));
                }
            }
        }
        return null;
    }
    /**
     * Evaluates given string path (splitting by dot) and returns the desired path
     *
     * @param root       Root to start with
     * @param pathString Result path
     * @return Path to desired property
     */
    private Path getCriteriaPath(Root root, String pathString) {
        String[] fields = pathString.split("\\.");
        Path path = root.get(fields[0]);

        for (int i = 1; i < fields.length; i++) {
            path = path.get(fields[i]);
        }

        return path;
    }
}
