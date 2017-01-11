package model;

import java.time.LocalDate;

/**
 * Created by dkarachurin on 09.01.2017.
 */
public class Outgoing extends Document {
    private Person recipient;
    private DeliveryMethods deliveryMethod;

    public Outgoing() {
    }

    public Outgoing(int id, String name, String text, String regNumber, LocalDate date, Person author, Person recipient, DeliveryMethods deliveryMethod) {
        super(id, name, text, regNumber, date, author);
        this.recipient = recipient;
        this.deliveryMethod = deliveryMethod;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person recipient) {
        this.recipient = recipient;
    }

    public DeliveryMethods getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethods deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    @Override
    public String toString() {
        return "Outgoing №"+super.toString();
    }
}
