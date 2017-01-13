package dao;

import model.Document;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by dkarachurin on 13.01.2017.
 */
@Stateless
public class DocumentDaoJpa implements DocumentDAO {
    @PersistenceContext(unitName="EcmPersistence")
    private EntityManager entityManager;

    public DocumentDaoJpa() {
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Document> findAll() {
        return entityManager.createQuery("SELECT d FROM Document d").getResultList();
    }

    @Override
    public Document find(int id) {
        return entityManager.find(Document.class, id);
    }

    @Override
    public void addDocument(Document document) {
        entityManager.persist(document);
    }

    @Override
    public void updateDocument(Document document) {
        entityManager.merge(document);
    }

    @Override
    public void deleteDocument(int id) {
        Document document = find(id);
        entityManager.remove(document);
    }
}
