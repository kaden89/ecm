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
public class OrganizationDaoJpa extends GenericDaoJpa<Organization> {

}
