package ecm.web.dto.converters;

import ecm.model.Incoming;
import ecm.web.dto.IncomingDTO;

import javax.inject.Singleton;

/**
 * Конвертер {@link Incoming} - {@link IncomingDTO}
 * @author dkarachurin
 */
@Singleton
public class IncomingDTOConverter extends AbstractDocumentDTOConverterImpl<Incoming, IncomingDTO> {
    @Override
    public Incoming fromDTO(IncomingDTO dto) {
        Incoming incoming = new Incoming(dto);
        incoming.setAuthor(getPersonDAO().find(dto.getAuthorId()));
        incoming.setSender(getPersonDAO().find(dto.getSenderId()));
        incoming.setRecipient(getPersonDAO().find(dto.getRecipientId()));
        return incoming;
    }

    @Override
    public IncomingDTO toDTO(Incoming document) {
        IncomingDTO incomingDTO = new IncomingDTO(document);
        return incomingDTO;
    }
}
