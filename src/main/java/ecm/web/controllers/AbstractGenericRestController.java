
package ecm.web.controllers;

import com.google.gson.Gson;
import ecm.model.Incoming;
import ecm.model.Outgoing;
import ecm.model.Person;
import ecm.model.Task;
import ecm.service.*;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;
import ecm.web.dto.*;
import ecm.web.dto.converters.GenericDTOConverter;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dkarachurin on 24.01.2017.
 */
public abstract class AbstractGenericRestController<T, D> {

    private Class<T> typeOfT;

    @Inject
    private transient Logger log;
    @Inject
    private Gson gson;

    /**
     * Инжектятся все сервисы для универсальности, т.к. EJB и CDI путаются в дженерик интерфейсах
     * GenericService<T> и  GenericDTOConverter<T, D> - так не получается инжектить
     * доступ только через getService() и getDtoConverter() для определения класса
     */
    @EJB
    private PersonService personService;
    @EJB
    private IncomingService incomingService;
    @EJB
    private OutgoingService outgoingService;
    @EJB
    private TaskService taskService;

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

    @SuppressWarnings("unchecked")
    public AbstractGenericRestController() {
        this.typeOfT = (Class<T>)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @GET
    @Path("/tree/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTree() {
        List<D> dtos = (List<D>) getDtoConverter().toDtoCollection(getService().findAll());
        TreeNode<D> root = new TreeNode<>(typeOfT.getSimpleName(), "", dtos);
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntityList(@HeaderParam("Range") RangeHeader range, @QueryParam("sort") Sort sort, @QueryParam("filter") String filterString) {
        if (filterString != null && sort != null && range != null) {
            Filter filter = (Filter) fromJson(filterString, Filter.class);
            Page<T> page = getService().findAllSortedFilteredAndPageable(sort, filter, range);
            return getPageResponse(page);
        } else if (sort != null && range != null) {
            Page<T> page = getService().findAllSortedAndPageable(sort, range);
            return getPageResponse(page);
        } else {
            Collection<D> tasks = getDtoConverter().toDtoCollection(getService().findAll());
            return Response.ok(toJson(tasks)).header("Content-Range", "items 0-" + tasks.size() + "/" + tasks.size()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntity(@PathParam("id") int employeeId) {
        D dto = (D) getDtoConverter().toDTO(getService().find(employeeId));
        return Response.ok(dto).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEntity(D dto) {
//        dto.setId(null);
        T document = (T) getService().save(getDtoConverter().fromDTO(dto));
        return Response.ok(getDtoConverter().toDTO(document)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEntity(@PathParam("id") int id, D dto) {
//        dto.setId(id);
        T updated = (T) getService().update(getDtoConverter().fromDTO(dto));
        return Response.ok(getDtoConverter().toDTO(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEntity(@PathParam("id") int taskId) {
        getService().delete(taskId);
        return Response.ok().build();
    }
    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public Object fromJson(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public Response getPageResponse(Page page) {
        return Response.ok(toJson(getDtoConverter().toDtoCollection(page.getItems())))
                .header("Content-Range", "items " + page.getStartIndex() + "-" + page.getEndIndex() + "/" + page.getAllItemsCount())
                .build();
    }


    private GenericService getService(){
        GenericService service = null;

        if (typeOfT.isAssignableFrom(Incoming.class)){
            service = incomingService;
        }
        else if (typeOfT.isAssignableFrom(Outgoing.class)){
            service = outgoingService;
        }
        else if (typeOfT.isAssignableFrom(Task.class)){
            service = taskService;
        }
        else if (typeOfT.isAssignableFrom(Person.class)){
            service = personService;
        }
        return service;
    }

    private GenericDTOConverter getDtoConverter(){
        GenericDTOConverter converter = null;

        if (typeOfT.isAssignableFrom(Incoming.class)){
            converter = incomingDTOConverter;
        }
        else if (typeOfT.isAssignableFrom(Outgoing.class)){
            converter = outgoingDTOConverter;
        }
        else if (typeOfT.isAssignableFrom(Task.class)){
            converter = taskDTOConverter;
        }
        else if (typeOfT.isAssignableFrom(Person.class)){
            converter = personDTOConverter;
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


    public ImageService getImageService() {
        return imageService;
    }

    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

}
