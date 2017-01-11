package model;

/**
 * Created by dkarachurin on 11.01.2017.
 */
public abstract class Staff {
    private int id;

    public Staff(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
