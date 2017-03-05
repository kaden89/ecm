package ecm.web.dto;

import ecm.model.Department;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * ДТО {@link Department}
 * @author dkarachurin
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "departments")
public class DepartmentsDTO {
    @XmlElement(name = "department", type = Department.class)
    private List<Department> departments = new ArrayList<>();

    public DepartmentsDTO() {
    }

    public DepartmentsDTO(List<Department> departments) {
        this.departments = departments;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
