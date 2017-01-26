package ecm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by dkarachurin on 25.01.2017.
 */
@Entity
public class Image {
    @Id
    private Integer id;
    @OneToOne
    private Person owner;

    private byte[] image;

    public Image() {
    }

    public Image(Integer id, Person owner, byte[] image) {
        this.id = id;
        this.owner = owner;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
