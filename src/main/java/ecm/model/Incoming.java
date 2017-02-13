package ecm.model;

import ecm.dao.GenericDAO;
import ecm.web.dto.IncomingDTO;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.inject.Inject;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
@Entity
public class Incoming extends Document {
    @ManyToOne
    @NotNull
    private Person sender;
    @ManyToOne
    @NotNull
    private Person recipient;
    @NotNull
    private String referenceNumber;
    @NotNull
    private LocalDate outboundRegDate;

    public Incoming() {
    }

    public Incoming(String name, String text, String regNumber, LocalDate date, Person author, Person sender, Person recipient, String referenceNumber, LocalDate outboundRegDate) {
        super(name, text, regNumber, date, author);
        this.sender = sender;
        this.recipient = recipient;
        this.referenceNumber = referenceNumber;
        this.outboundRegDate = outboundRegDate;
    }

    public Incoming(IncomingDTO dto) {
        super(dto.getName(), dto.getText(), dto.getRegNumber(), dto.getDate(), null);
        this.referenceNumber = dto.getReferenceNumber();
        this.outboundRegDate = dto.getOutboundRegDate();
        this.setId(dto.getId());
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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
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
