package ecm.util.sorting;

/**
 * Created by Денис on 11.02.2017.
 */
public class Sort {

    private String field;
    private String direction;

    public Sort(String sortParam) {
//        {sortField: id, direction: ASC}
        String[] sort = sortParam.replaceAll("\\{", "").replaceAll("\\}", "").split(",");
        this.field = sort[0].split(":")[1].replaceAll(" ","");
        this.direction = sort[1].split(":")[1].replaceAll(" ","");
    }

    public Sort(String field, String direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
