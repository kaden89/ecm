package ecm.web.dto.converters;

import ecm.model.Outgoing;
import ecm.web.dto.OutgoingDTO;

import javax.inject.Singleton;

/**
 * Created by dkarachurin on 13.02.2017.
 */
@Singleton
public class OutgoingDTOConverter extends AbstractDocumentDTOConverterImpl<Outgoing, OutgoingDTO> {
    @Override
    public Outgoing fromDTO(OutgoingDTO dto) {
        Outgoing outgoing = new Outgoing(dto);
        outgoing.setAuthor(getPersonDAO().find(dto.getAuthorId()));
        outgoing.setRecipient(getPersonDAO().find(dto.getRecipientId()));
        return outgoing;
    }

    @Override
    public OutgoingDTO toDTO(Outgoing document) {
        OutgoingDTO outgoingDTO = new OutgoingDTO(document);
        return outgoingDTO;
    }
}
