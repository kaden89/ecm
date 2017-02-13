package ecm.web.dto;

import ecm.model.Department;

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
