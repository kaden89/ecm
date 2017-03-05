package ecm.model;

import ecm.web.dto.OutgoingDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Модель Исходящего документа
 * @author dkarachurin
 */
@Entity
public class Outgoing extends Document {
    @ManyToOne
    @NotNull
    private Person recipient;
    @Size(max = 255)
    private String deliveryMethod;

    public Outgoing() {
    }

    public Outgoing(String name, String text, String regNumber, LocalDate date, Person author, Person recipient, String deliveryMethod) {
        super(name, text, regNumber, date, author);
        this.recipient = recipient;
        this.deliveryMethod = deliveryMethod;
    }

    public Outgoing(OutgoingDTO dto) {
        this(dto.getName(), dto.getText(), dto.getRegNumber(), dto.getDate(), null, null, dto.getDeliveryMethod());
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
