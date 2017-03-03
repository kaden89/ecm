package ecm.util.filtering;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 10.02.2017.
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
