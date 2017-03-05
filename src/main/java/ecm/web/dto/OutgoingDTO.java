package ecm.web.dto;

import ecm.model.Outgoing;

/**
 * ДТО {@link Outgoing}
 * @author dkarachurin
 */
public class OutgoingDTO extends AbstractDocumentDTO {
    private Integer recipientId;
    private String recipientName;
    private String deliveryMethod;
    private String type = "outgoing";

    public OutgoingDTO() {
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public OutgoingDTO(Outgoing outgoing) {
        super(outgoing);
        this.recipientId = outgoing.getRecipient().getId();
        this.recipientName = outgoing.getRecipient().toString();
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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
