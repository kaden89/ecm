package ecm.web.dto.converters;

import ecm.dao.GenericDAO;
import ecm.model.*;
import ecm.web.dto.IncomingDTO;
import ecm.web.dto.OutgoingDTO;
import ecm.web.dto.TaskDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dkarachurin on 01.02.2017.
 */

public abstract class AbstractDocumentDTOConverterImpl<E, D> implements GenericDTOConverter<E, D> {
    @Inject
    private GenericDAO<Person> personDAO;

    public E fromDTO(D dto) {
        if (dto instanceof IncomingDTO) {
            Incoming incoming = new Incoming((IncomingDTO) dto);
            incoming.setAuthor(personDAO.find(((IncomingDTO) dto).getAuthorId()));
            incoming.setSender(personDAO.find(((IncomingDTO) dto).getSenderId()));
            incoming.setRecipient(personDAO.find(((IncomingDTO) dto).getRecipientId()));
            return (E) incoming;
        } else if (dto instanceof OutgoingDTO) {
            Outgoing outgoing = new Outgoing((OutgoingDTO) dto);
            outgoing.setAuthor(personDAO.find(((OutgoingDTO) dto).getAuthorId()));
            outgoing.setRecipient(personDAO.find(((OutgoingDTO) dto).getRecipientId()));
            return (E) outgoing;
        } else if (dto instanceof TaskDTO) {
            Task task = new Task((TaskDTO) dto);
            task.setAuthor(personDAO.find(((TaskDTO) dto).getAuthorId()));
            task.setExecutor(personDAO.find(((TaskDTO) dto).getExecutorId()));
            task.setController(personDAO.find(((TaskDTO) dto).getControllerId()));
            return (E) task;
        }
        return null;
    }

    public D toDTO(E document) {
        if (document instanceof Incoming) {
            IncomingDTO incomingDTO = new IncomingDTO((Incoming) document);
            return (D) incomingDTO;
        } else if (document instanceof Outgoing) {
            OutgoingDTO outgoingDTO = new OutgoingDTO((Outgoing) document);
            return (D) outgoingDTO;

        } else if (document instanceof Task) {
            TaskDTO taskDTO = new TaskDTO((Task) document);
            return (D) taskDTO;
        }
        return null;
    }

    public Collection<D> toDtoCollection(Collection<E> documents) {
        Collection<D> result = new ArrayList<>();
        for (E document : documents) {
            result.add(toDTO(document));
        }
        return result;
    }

    public Collection<E> fromDtoCollection(Collection<D> dtoCollection) {
        Collection<E> result = new ArrayList<>();
        for (D dto : dtoCollection) {
            result.add(fromDTO(dto));
        }
        return result;
    }
}
