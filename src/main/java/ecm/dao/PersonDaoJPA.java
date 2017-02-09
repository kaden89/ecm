package ecm.dao;

import ecm.model.*;
import ecm.util.exceptions.HasLinksException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
@Singleton
@Transactional
public class PersonDaoJPA extends GenericDaoJpa<Person>{

}
