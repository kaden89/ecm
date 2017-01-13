package dao;

import model.Department;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dkarachurin on 13.01.2017.
 */
@Stateless
public class DepartmentDaoJpa implements DepartmentDAO {
    @PersistenceContext(unitName="EcmPersistence")
    private EntityManager entityManager;

    public DepartmentDaoJpa() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Department> findAll() {
        return entityManager.createQuery("SELECT d FROM Department d").getResultList();
    }

    @Override
    public Department find(int id) {
        return entityManager.find(Department.class, id);
    }

    @Override
    public void addDepartment(Department department) {
        entityManager.persist(department);
    }

    @Override
    public void updateDepartment(Department department) {
        entityManager.merge(department);
    }

    @Override
    public void deleteDepartment(int id) {
        Department department = find(id);
        entityManager.remove(department);
    }
}
