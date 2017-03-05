package ecm.service;

import ecm.model.Incoming;

import javax.ejb.Stateless;

/**
 * Слой сервиса реализующий доступ к DAO класса {@link Incoming}
 * @author dkarachurin
 */
@Stateless
public class IncomingService extends AbstractGenericServiceImpl<Incoming> {
}
