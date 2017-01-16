package ecm.model;

import ecm.dao.Storable;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.time.LocalDate;


/**
 * Created by dkarachurin on 09.01.2017.
 */
@MappedSuperclass
@XmlSeeAlso({Incoming.class, Outgoing.class, Task.class})
@XmlRootElement
public abstract class Document implements Comparable<Document>, Storable {
    @Id
    private int id;
    private String name;
    private String text;
    private String regNumber;
    private LocalDate date;
    @ManyToOne
    private Person author;

    public Document() {
    }

    public Document(int id, String name, String text, String regNumber, LocalDate date, Person author) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.regNumber = regNumber;
        this.date = date;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    @Override
    public String getStorageName() {
        return "Documents";
    }

    @Override
    public int compareTo(Document document) {
        if (this.getDate().equals(document.getDate())) {
            return this.getRegNumber().compareTo(document.getRegNumber());
        }
        else return this.getDate().compareTo(document.getDate());
    }

    @Override
    public String toString() {
        return getRegNumber()+" from " + getDate() + ". " + getName();
    }
}
