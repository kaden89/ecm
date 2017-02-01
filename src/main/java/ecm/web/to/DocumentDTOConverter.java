package ecm.web.to;

import ecm.dao.GenericDAO;
import ecm.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dkarachurin on 01.02.2017.
 */
@Singleton
public class DocumentDTOConverter {
    @Inject
    GenericDAO<Person> personDAO;

    public Document fromDTO(AbstractDocumentDTO dto){
        if (dto instanceof IncomingDTO){
            Incoming incoming = new Incoming(dto.getName(),
                    dto.getText(),
                    dto.getRegNumber(),
                    dto.getDate(),
                    personDAO.find(dto.getAuthorId()),
                    personDAO.find(((IncomingDTO) dto).getSenderId()),
                    personDAO.find(((IncomingDTO) dto).getRecipientId()),
                    ((IncomingDTO) dto).getReferenceNumber(),
                    ((IncomingDTO) dto).getOutboundRegDate());
            incoming.setId(dto.getId());
            return incoming;
        }
        return null;
    }

    public AbstractDocumentDTO toDTO(Document document){
        if (document instanceof Incoming){
            IncomingDTO incomingDTO = new IncomingDTO(document.getId(),
                    document.getName(),
                    document.getText(),
                    document.getRegNumber(),
                    document.getDate(),
                    document.getAuthor().getId(),
                    ((Incoming) document).getSender().getId(),
                    ((Incoming) document).getRecipient().getId(),
                    ((Incoming) document).getReferenceNumber(),
                    ((Incoming) document).getOutboundRegDate());

            return incomingDTO;
        }
        return null;
    }
}
