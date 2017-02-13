package ecm.model;

import ecm.web.dto.OutgoingDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@Entity
public class Outgoing extends Document {
    @ManyToOne
    @NotNull
    private Person recipient;
    private String deliveryMethod;

    public Outgoing() {
    }

    public Outgoing(String name, String text, String regNumber, LocalDate date, Person author, Person recipient, String deliveryMethod) {
        super(name, text, regNumber, date, author);
        this.recipient = recipient;
        this.deliveryMethod = deliveryMethod;
    }

    public Outgoing(OutgoingDTO dto) {
        super(dto.getName(), dto.getText(), dto.getRegNumber(), dto.getDate(), null);
        this.deliveryMethod = dto.getDeliveryMethod();
        this.setId(dto.getId());
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
        return "Outgoing №" + super.toString();
    }
}
