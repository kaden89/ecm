package ecm.web.dto;

import ecm.dao.GenericDAO;
import ecm.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dkarachurin on 01.02.2017.
 */
@Singleton
public class DocumentDTOConverter implements DTOConverter<Document, AbstractDocumentDTO>{
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
        else if (dto instanceof TaskDTO){
           Task task = new Task(dto.getName(),
                   dto.getText(),
                   dto.getRegNumber(),
                   dto.getDate(),
                   personDAO.find(dto.getAuthorId()),
                   ((TaskDTO) dto).getDateOfIssue(),
                   ((TaskDTO) dto).getDeadline(),
                   personDAO.find(((TaskDTO) dto).getExecutorId()),
                   ((TaskDTO) dto).isControlled(),
                   personDAO.find(((TaskDTO) dto).getControllerId()));

            task.setId(dto.getId());
            return task;
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
        else if (document instanceof Task){
            TaskDTO taskDTO = new TaskDTO(document.getId(),
                    document.getName(),
                    document.getText(),
                    document.getRegNumber(),
                    document.getDate(),
                    document.getAuthor().getId(),
                    ((Task) document).getDateOfIssue(),
                    ((Task) document).getDeadline(),
                    ((Task) document).getExecutor().getId(),
                    ((Task) document).isControlled(),
                    ((Task) document).getController().getId());

            taskDTO.setFullname(document.toString());
            return taskDTO;

        }
        return null;
    }

    public Collection<AbstractDocumentDTO> toDtoCollection(Collection<Document> documents){
        Collection<AbstractDocumentDTO> result = new ArrayList<>();
        for (Document document : documents) {
            result.add(toDTO(document));
        }
        return result;
    }

    public Collection<Document> fromDtoCollection( Collection<AbstractDocumentDTO> dtoCollection){
        Collection<Document> result = new ArrayList<>();
        for (AbstractDocumentDTO dto : dtoCollection) {
            result.add(fromDTO(dto));
        }
        return result;
    }
}
