package ecm.dao;

/**
 * Абстрактное DAO для документов, реализующее {@link DocumentGenericDAO}
 * @see IncomingDaoJPA
 * @see OutgoingDaoJPA
 * @see TaskDaoJPA
 * @author dkarachurin
 */
public abstract class DocumentGenericDaoJpa<T> extends AbstractGenericDaoJpaImpl<T> implements DocumentGenericDAO<T> {
}
