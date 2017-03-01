package ecm.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by dkarachurin on 11.01.2017.
 */
@MappedSuperclass
@Access(value = AccessType.FIELD)
public abstract class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;

    public Staff() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
