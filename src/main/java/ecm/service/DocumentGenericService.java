package ecm.service;

import java.util.List;

/**
 * Created by Денис on 11.02.2017.
 */
public interface DocumentGenericService<T> extends GenericService<T> {
    List<T> findAllWithAuthorId(int id);
}
