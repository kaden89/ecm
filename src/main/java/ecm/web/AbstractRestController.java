
package ecm.web;

import com.google.gson.*;
import ecm.model.*;
import ecm.service.GenericService;
import ecm.service.ImageService;
import ecm.util.paging.Page;
import ecm.web.dto.*;
import ecm.web.dto.converters.GenericDTOConverter;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by dkarachurin on 24.01.2017.
 */
public class AbstractRestController {

    @Inject
    private transient Logger log;

    @Inject
    private Gson gson;

    @Inject
    private GenericService<Person> personService;

    @Inject
    private GenericService<Outgoing> outgoingService;

    @Inject
    private GenericService<Incoming> incomingService;

    @Inject
    private GenericService<Task> taskService;

    @Inject
    private GenericService<Post> postService;

    @Inject
    private ImageService imageService;

    @Inject
    private GenericDTOConverter<Incoming, IncomingDTO> incomingDTOConverter;

    @Inject
    private GenericDTOConverter<Outgoing, OutgoingDTO> outgoingDTOConverter;

    @Inject
    private GenericDTOConverter<Task, TaskDTO> taskDTOConverter;

    @Inject
    private GenericDTOConverter<Person, PersonDTO> personDTOConverter;

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public Object fromJson(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public Response getPageResponseFromClass(Page page, Class clazz) {
        return Response.ok(toJson(getConverterFromClass(clazz).toDtoCollection(page.getItems())))
                .header("Content-Range", "items " + page.getStartIndex() + "-" + page.getEndIndex() + "/" + page.getAllItemsCount())
                .build();
    }

    private GenericDTOConverter getConverterFromClass(Class clazz){
        GenericDTOConverter converter = null;

        if (clazz.isAssignableFrom(Incoming.class)){
            converter = getIncomingDTOConverter();
        }
        else if (clazz.isAssignableFrom(Outgoing.class)){
            converter = getOutgoingDTOConverter();
        }
        else if (clazz.isAssignableFrom(Task.class)){
            converter = getTaskDTOConverter();
        }
        else if (clazz.isAssignableFrom(Person.class)){
            converter = getPersonDTOConverter();
        }
        return converter;
    }


    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public GenericService<Person> getPersonService() {
        return personService;
    }

    public void setPersonService(GenericService<Person> personService) {
        this.personService = personService;
    }

    public GenericService<Outgoing> getOutgoingService() {
        return outgoingService;
    }

    public void setOutgoingService(GenericService<Outgoing> outgoingService) {
        this.outgoingService = outgoingService;
    }

    public GenericService<Incoming> getIncomingService() {
        return incomingService;
    }

    public void setIncomingService(GenericService<Incoming> incomingService) {
        this.incomingService = incomingService;
    }

    public GenericService<Task> getTaskService() {
        return taskService;
    }

    public void setTaskService(GenericService<Task> taskService) {
        this.taskService = taskService;
    }

    public ImageService getImageService() {
        return imageService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    public GenericDTOConverter<Incoming, IncomingDTO> getIncomingDTOConverter() {
        return incomingDTOConverter;
    }

    public void setIncomingDTOConverter(GenericDTOConverter<Incoming, IncomingDTO> incomingDTOConverter) {
        this.incomingDTOConverter = incomingDTOConverter;
    }

    public GenericDTOConverter<Outgoing, OutgoingDTO> getOutgoingDTOConverter() {
        return outgoingDTOConverter;
    }

    public void setOutgoingDTOConverter(GenericDTOConverter<Outgoing, OutgoingDTO> outgoingDTOConverter) {
        this.outgoingDTOConverter = outgoingDTOConverter;
    }

    public GenericDTOConverter<Task, TaskDTO> getTaskDTOConverter() {
        return taskDTOConverter;
    }

    public void setTaskDTOConverter(GenericDTOConverter<Task, TaskDTO> taskDTOConverter) {
        this.taskDTOConverter = taskDTOConverter;
    }

    public GenericDTOConverter<Person, PersonDTO> getPersonDTOConverter() {
        return personDTOConverter;
    }

    public void setPersonDTOConverter(GenericDTOConverter<Person, PersonDTO> personDTOConverter) {
        this.personDTOConverter = personDTOConverter;
    }

    public GenericService<Post> getPostService() {
        return postService;
    }

    public void setPostService(GenericService<Post> postService) {
        this.postService = postService;
    }
}
