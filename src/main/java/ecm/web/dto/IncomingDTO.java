package ecm.web.dto;

import ecm.model.Incoming;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 01.02.2017.
 */
public class IncomingDTO extends AbstractDocumentDTO{
    private Integer senderId;
    private Integer recipientId;
    private Integer referenceNumber;
    private LocalDate outboundRegDate;
    private String restUrl = "incomings";
    public IncomingDTO() {
    }

    public IncomingDTO(Integer id, String name, String text, String regNumber, LocalDate date, Integer authorId, Integer senderId, Integer recipientId, Integer referenceNumber, LocalDate outboundRegDate) {
        super(id, name, text, regNumber, date, authorId);
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.referenceNumber = referenceNumber;
        this.outboundRegDate = outboundRegDate;
    }

    public IncomingDTO(Incoming incoming) {
        super(incoming.getId(), incoming.getName(), incoming.getText(), incoming.getRegNumber(), incoming.getDate(), incoming.getAuthor().getId());
        this.senderId = incoming.getSender().getId();
        this.recipientId = incoming.getRecipient().getId();
        this.referenceNumber = incoming.getReferenceNumber();
        this.outboundRegDate = incoming.getOutboundRegDate();
        this.setFullname(incoming.toString());
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public Integer getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(Integer referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public LocalDate getOutboundRegDate() {
        return outboundRegDate;
    }

    public void setOutboundRegDate(LocalDate outboundRegDate) {
        this.outboundRegDate = outboundRegDate;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }
}
