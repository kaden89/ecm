package ecm.model;

import ecm.dao.Storable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


/**
 * Абстрактная модель документа
 * @author dkarachurin
 */
@MappedSuperclass
public abstract class Document implements Comparable<Document>, Storable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    @Size(max = 255)
    private String name;
    private String text;
    @Size(max = 255)
    private String regNumber;
    @NotNull
    private LocalDate date;
    @ManyToOne
    @NotNull
    private Person author;

    public Document() {
    }


    public Document(String name, String text, String regNumber, LocalDate date, Person author) {
        this.name = name;
        this.text = text;
        this.regNumber = regNumber;
        this.date = date;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        } else {
            return this.getDate().compareTo(document.getDate());
        }
    }

    @Override
    public String toString() {
        return getRegNumber() + " from " + getDate() + ". " + getName();
    }
}
