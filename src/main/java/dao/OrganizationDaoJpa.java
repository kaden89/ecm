package dao;

import model.Organization;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dkarachurin on 13.01.2017.
 */
@Stateless
public class OrganizationDaoJpa implements OrganizationDAO {
    @PersistenceContext(unitName="EcmPersistence")
    private EntityManager entityManager;

    public OrganizationDaoJpa() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Organization> findAll() {
        return entityManager.createQuery("SELECT o FROM Organization o").getResultList();
    }

    @Override
    public Organization find(int id) {
        return entityManager.find(Organization.class, id);
    }

    @Override
    public void addOrganization(Organization organization) {
        entityManager.persist(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        entityManager.merge(organization);
    }

    @Override
    public void deleteOrganization(int id) {
        Organization organization = find(id);
        entityManager.remove(organization);
    }
}
