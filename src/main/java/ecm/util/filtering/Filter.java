package ecm.util.filtering;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Объектное представление JSON фильтра получаемого от Gridx.
 * Содержит список правил {@link Rule} и условия между ними {@link Conditions}
 * Формирует предикат для Criteria API для метода where()
 * @author dkarachurin
 */
public class Filter {

    private Conditions op;
    private List<Rule> data;

    public Filter() {
    }

    public Filter(Conditions op, List<Rule> data) {
        this.op = op;
        this.data = data;
    }

    public Conditions getOp() {
        return op;
    }

    public void setOp(Conditions op) {
        this.op = op;
    }

    public List<Rule> getData() {
        return data;
    }

    public void setData(List<Rule> data) {
        this.data = data;
    }

    /**
     * Формирует предикат для Criteria API
     * @param cb Criteria Builder
     * @param root Корневой путь
     * @param entityClass класс Entity
     * @return
     */
    public Predicate getFilterPredicate(CriteriaBuilder cb, Root root, Class entityClass) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate result = null;
        for (Rule rule : data) {
            predicates.add(rule.getPredicate(cb, root, entityClass));
        }
        switch (op) {
            case AND:
                result = cb.and(predicates.toArray(new Predicate[predicates.size()]));
                break;
            case OR:
                result = cb.or(predicates.toArray(new Predicate[predicates.size()]));
                break;
        }
        return result;
    }
}
