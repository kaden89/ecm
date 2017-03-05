package ecm.dao;

/**
 * Абстрактное DAO для документов, реализующее {@link DocumentGenericDAO}
 * @author dkarachurin
 */
public abstract class DocumentGenericDaoJpa<T> extends AbstractGenericDaoJpaImpl<T> implements DocumentGenericDAO<T> {
}
