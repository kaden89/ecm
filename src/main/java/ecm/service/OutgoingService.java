package ecm.service;

import ecm.model.Outgoing;

import javax.ejb.Stateless;

/**
 * Слой сервиса реализующий доступ к DAO класса {@link Outgoing}
 * @author dkarachurin
 */
@Stateless
public class OutgoingService extends AbstractGenericServiceImpl<Outgoing> {
}
