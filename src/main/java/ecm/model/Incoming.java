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
@XmlRootElement(name = "Incoming")
@Entity
public class Incoming extends Document {
    @ManyToOne
    private Person sender;
    @ManyToOne
    private Person recipient;
    private int referenceNumber;
    private LocalDate outboundRegDate;

    public Incoming() {
    }

    public Incoming(int id, String name, String text, String regNumber, LocalDate date, Person author, Person sender, Person recipient, int referenceNumber, LocalDate outboundRegDate) {
        super(id, name, text, regNumber, date, author);
        this.sender = sender;
        this.recipient = recipient;
        this.referenceNumber = referenceNumber;
        this.outboundRegDate = outboundRegDate;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public LocalDate getOutboundRegDate() {
        return outboundRegDate;
    }

    public void setOutboundRegDate(LocalDate outboundRegDate) {
        this.outboundRegDate = outboundRegDate;
    }

    @Override
    public String toString() {
        return "Incoming №" + super.toString();
    }
}
