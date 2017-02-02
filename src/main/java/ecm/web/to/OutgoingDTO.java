package ecm.web.to;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 02.02.2017.
 */
public class OutgoingDTO extends AbstractDocumentDTO {
    private Integer recipientId;
    private String deliveryMethod;

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

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
}
