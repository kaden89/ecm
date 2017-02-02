package ecm.web.dto;

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
        else if (dto instanceof OutgoingDTO){
            Outgoing outgoing = new Outgoing(dto.getName(),
                    dto.getText(),
                    dto.getRegNumber(),
                    dto.getDate(),
                    personDAO.find(((OutgoingDTO) dto).getRecipientId()),
                    personDAO.find(((OutgoingDTO) dto).getRecipientId()),
                    ((OutgoingDTO) dto).getDeliveryMethod());
            outgoing.setId(dto.getId());
            return outgoing;
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

            incomingDTO.setFullname(document.toString());
            return incomingDTO;
        }
        else if (document instanceof Outgoing){
            OutgoingDTO outgoingDTO = new OutgoingDTO(document.getId(),
                    document.getName(),
                    document.getText(),
                    document.getRegNumber(),
                    document.getDate(),
                    document.getAuthor().getId(),
                    ((Outgoing) document).getRecipient().getId(),
                    ((Outgoing) document).getDeliveryMethod());
            outgoingDTO.setFullname(document.toString());
            return outgoingDTO;

        }
        return null;
    }
}
