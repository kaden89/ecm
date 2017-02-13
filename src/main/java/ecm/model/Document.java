package ecm.model;

import ecm.dao.GenericDAO;
import ecm.dao.Storable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.inject.Inject;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.time.LocalDate;


/**
 * Created by dkarachurin on 09.01.2017.
 */
@MappedSuperclass
public abstract class Document implements Comparable<Document>, Storable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    private String text;
    @NotNull
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
        } else return this.getDate().compareTo(document.getDate());
    }

    @Override
    public String toString() {
        return getRegNumber() + " from " + getDate() + ". " + getName();
    }
}
