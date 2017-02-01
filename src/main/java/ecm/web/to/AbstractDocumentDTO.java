package ecm.web.to;

import ecm.util.xml.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 01.02.2017.
 */
@XmlSeeAlso({IncomingDTO.class})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public abstract class AbstractDocumentDTO {
    private Integer id;
    private String name;
    private String text;
    private String regNumber;
    private LocalDate date;
    private Integer authorId;

    public AbstractDocumentDTO() {
    }

    public AbstractDocumentDTO(Integer id, String name, String text, String regNumber, LocalDate date, Integer authorId) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.regNumber = regNumber;
        this.date = date;
        this.authorId = authorId;
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

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }
}