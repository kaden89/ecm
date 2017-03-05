package ecm.model;

import ecm.web.dto.IncomingDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Модель Входящего документа
 * @author dkarachurin
 */
@Entity
public class Incoming extends Document {
    @ManyToOne
    @NotNull
    private Person sender;
    @ManyToOne
    @NotNull
    private Person recipient;
    @Size(max = 255)
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
        this(dto.getName(), dto.getText(), dto.getRegNumber(), dto.getDate(), null, null, null, dto.getReferenceNumber(), dto.getOutboundRegDate());
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
