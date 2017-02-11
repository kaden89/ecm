package ecm.util.filtering;

/**
 * Created by dkarachurin on 10.02.2017.
 */
public class Field {
    private String op;
    private String data;

    public Field() {
    }

    public Field(String op, String data) {
        this.op = op;
        this.data = data;

    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {

            return data;

    }
}
