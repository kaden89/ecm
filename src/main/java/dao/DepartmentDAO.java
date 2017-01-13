package dao;

import model.Department;

import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface DepartmentDAO {
    List<Department> findAll();

    Department find(int id);

    void addDepartment(Department person);

    void updateDepartment(Department person);

    void deleteDepartment(int id);
}
