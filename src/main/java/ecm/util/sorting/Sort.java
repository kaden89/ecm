package ecm.util.sorting;

/**
 * Представляет собой объектное представление приходящего с клиента сортировочного JSON. Содержит поле и направление
 * @author dkarachurin
 */
public class Sort {

    private String field;
    private Direction direction;

    public Sort() {
    }

    public Sort(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
