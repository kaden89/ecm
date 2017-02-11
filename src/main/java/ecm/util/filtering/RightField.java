package ecm.util.filtering;

/**
 * Created by Денис on 11.02.2017.
 */
public class RightField extends Field {
    private boolean isCol;

    public RightField(String op, String data, boolean isCol) {
        super(op, data);
        this.isCol = isCol;
    }

    public boolean isCol() {
        return isCol;
    }

    public void setCol(boolean col) {
        isCol = col;
    }
}
