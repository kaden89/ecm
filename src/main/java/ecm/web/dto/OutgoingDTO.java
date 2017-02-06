package ecm.web.dto;

import ecm.model.Outgoing;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public class OutgoingDTO extends AbstractDocumentDTO {
    private Integer recipientId;
    private String deliveryMethod;
    private String restUrl = "outgoings";
    public OutgoingDTO() {
    }

    public OutgoingDTO(Integer id, String name, String text, String regNumber, LocalDate date, Integer authorId, Integer recipientId, String deliveryMethod) {
        super(id, name, text, regNumber, date, authorId);
        this.recipientId = recipientId;
        this.deliveryMethod = deliveryMethod;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public OutgoingDTO(Outgoing outgoing) {
        super(outgoing);
        this.recipientId = outgoing.getRecipient().getId();
        this.deliveryMethod = outgoing.getDeliveryMethod();
        this.setFullname(outgoing.toString());
    }
    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }
}
