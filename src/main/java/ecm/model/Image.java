package ecm.model;

import javax.persistence.*;

/**
 * Created by dkarachurin on 25.01.2017.
 */
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @OneToOne
    private Person owner;
    @Lob
    private byte[] image;

    public Image() {
    }

    public Image(Person owner, byte[] image) {
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
