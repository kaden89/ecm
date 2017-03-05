package ecm.web.dto;

import ecm.model.Organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * ДТО {@link Organization}
 * @author dkarachurin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "organizations")
public class OrganizationsDTO {
    @XmlElement(name = "organization", type = Organization.class)
    private List<Organization> organizations = new ArrayList<>();

    public OrganizationsDTO() {
    }

    public OrganizationsDTO(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

}
