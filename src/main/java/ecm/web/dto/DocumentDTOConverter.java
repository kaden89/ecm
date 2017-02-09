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
public class DocumentDTOConverter implements DTOConverter<Document, AbstractDocumentDTO> {
    @Inject
    private GenericDAO<Person> personDAO;

    public Document fromDTO(AbstractDocumentDTO dto) {
        if (dto instanceof IncomingDTO) {
            Incoming incoming = new Incoming((IncomingDTO) dto);
            incoming.setAuthor(personDAO.find(dto.getAuthorId()));
            incoming.setSender(personDAO.find(((IncomingDTO) dto).getSenderId()));
            incoming.setRecipient(personDAO.find(((IncomingDTO) dto).getRecipientId()));
            return incoming;
        } else if (dto instanceof OutgoingDTO) {
            Outgoing outgoing = new Outgoing((OutgoingDTO) dto);
            outgoing.setAuthor(personDAO.find(dto.getAuthorId()));
            outgoing.setRecipient(personDAO.find(((OutgoingDTO) dto).getRecipientId()));
            return outgoing;
        } else if (dto instanceof TaskDTO) {
            Task task = new Task((TaskDTO) dto);
            task.setAuthor(personDAO.find(dto.getAuthorId()));
            task.setExecutor(personDAO.find(((TaskDTO) dto).getExecutorId()));
            task.setController(personDAO.find(((TaskDTO) dto).getControllerId()));
            return task;
        }
        return null;
    }

    public AbstractDocumentDTO toDTO(Document document) {
        if (document instanceof Incoming) {
            IncomingDTO incomingDTO = new IncomingDTO((Incoming) document);
            return incomingDTO;
        } else if (document instanceof Outgoing) {
            OutgoingDTO outgoingDTO = new OutgoingDTO((Outgoing) document);
            return outgoingDTO;

        } else if (document instanceof Task) {
            TaskDTO taskDTO = new TaskDTO((Task) document);
            return taskDTO;
        }
        return null;
    }

    public Collection<AbstractDocumentDTO> toDtoCollection(Collection<Document> documents) {
        Collection<AbstractDocumentDTO> result = new ArrayList<>();
        for (Document document : documents) {
            result.add(toDTO(document));
        }
        return result;
    }

    public Collection<Document> fromDtoCollection(Collection<AbstractDocumentDTO> dtoCollection) {
        Collection<Document> result = new ArrayList<>();
        for (AbstractDocumentDTO dto : dtoCollection) {
            result.add(fromDTO(dto));
        }
        return result;
    }
}
