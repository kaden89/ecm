package ecm.web.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by dkarachurin on 02.02.2017.
 */
@XmlSeeAlso({PersonDTO.class})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AbstractStaffDTO {
    private Integer id;

    public AbstractStaffDTO() {
    }

    public AbstractStaffDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
