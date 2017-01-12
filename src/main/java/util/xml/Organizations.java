package util.xml;

import model.Organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 11.01.2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "organizations")
public class Organizations {
    @XmlElement(name = "organization", type = Organization.class)
    private List<Organization> organizations = new ArrayList<>();

    public Organizations() {
    }

    public Organizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

}
