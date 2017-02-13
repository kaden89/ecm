package ecm.util.filtering;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Map<String, Object> getQueryParams(){
        Map<String, Object> params = new HashMap<>();
        for (Rule rule : getData()) {
            params.put(rule.getLeftField().getData(), rule.getRightField().getData());
        }
        return params;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.size(); i++) {
            builder.append(data.get(i).toString());

            if (data.size()>1 && i!=data.size()-1){
                builder.append(op+" ");
            }
        }
        return builder.toString();
    }

    public String getCaseInsensitiveQueryString(Class clazz){
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.size(); i++) {
            builder.append(data.get(i).getCaseInsensitiveString(clazz));

            if (data.size()>1 && i!=data.size()-1){
                builder.append(op+" ");
            }
        }
        return builder.toString();
    }
}
