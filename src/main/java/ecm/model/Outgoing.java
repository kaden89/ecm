package ecm.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@XmlRootElement(name = "Outgoing")
@Entity
public class Outgoing extends Document {
    @ManyToOne
    private Person recipient;
    private String deliveryMethod;

    public Outgoing() {
    }

    public Outgoing(String name, String text, String regNumber, LocalDate date, Person author, Person recipient, String deliveryMethod) {
        super(name, text, regNumber, date, author);
        this.recipient = recipient;
        this.deliveryMethod = deliveryMethod;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return "Outgoing №"+super.toString();
    }
}
