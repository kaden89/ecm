package ecm.dao;

import java.util.List;

/**
 * Created by Денис on 11.02.2017.
 */
public interface DocumentGenericDAO<T> extends GenericDAO<T> {
    List<T> findAllWithPersonId(int id);
}
