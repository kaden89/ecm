
package ecm.web.controllers;

import com.google.gson.Gson;
import ecm.service.GenericService;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;
import ecm.web.dto.TreeNode;
import ecm.web.dto.converters.GenericDTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;


/**
 * Created by dkarachurin on 24.01.2017.
 */
public abstract class AbstractGenericRestController<T, D> {

    private Class<T> typeOfT;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private Gson gson;

    @Inject
    private GenericService<T> service;

    @Inject
    private GenericDTOConverter<T, D> converter;

    @SuppressWarnings("unchecked")
    public AbstractGenericRestController() {
        this.typeOfT = (Class<T>)
                ((ParameterizedType) getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

    @GET
    @Path("/tree/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTree() {
        List<D> dtos = (List<D>) converter.toDtoCollection(service.findAll());
        TreeNode<D> root = new TreeNode<>(typeOfT.getSimpleName(), "", dtos);
        String jsonInString = toJson(root);
        return Response.ok(jsonInString).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntityList(@HeaderParam("Range") RangeHeader range, @QueryParam("sort") String sortString, @QueryParam("filter") String filterString) {
        if (filterString != null && sortString != null && range != null) {
            Filter filter = (Filter) fromJson(filterString, Filter.class);
            Sort sort = (Sort) fromJson(sortString, Sort.class);
            Page<T> page = service.findAllSortedFilteredAndPageable(sort, filter, range);
            return getPageResponse(page);
        } else if (sortString != null && range != null) {
            Sort sort = (Sort) fromJson(sortString, Sort.class);
            Page<T> page = service.findAllSortedAndPageable(sort, range);
            return getPageResponse(page);
        } else {
            Collection<D> tasks = converter.toDtoCollection(service.findAll());
            return Response.ok(toJson(tasks)).header("Content-Range", "items 0-" + tasks.size() + "/" + tasks.size()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEntity(@PathParam("id") int employeeId) {
        D dto = converter.toDTO(service.find(employeeId));
        return Response.ok(dto).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEntity(D dto) {
        T document = service.save(converter.fromDTO(dto));
        return Response.ok(converter.toDTO(document)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEntity(@PathParam("id") int id, D dto) {
        T updated = service.update(converter.fromDTO(dto));
        return Response.ok(converter.toDTO(updated)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEntity(@PathParam("id") int taskId) {
        service.delete(taskId);
        return Response.ok().build();
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public Object fromJson(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public Response getPageResponse(Page page) {
        return Response.ok(toJson(converter.toDtoCollection(page.getItems())))
                .header("Content-Range", "items " + page.getStartIndex() + "-" + page.getEndIndex() + "/" + page.getAllItemsCount())
                .build();
    }

    public Logger getLog() {
        return log;
    }

    public Gson getGson() {
        return gson;
    }

    public Class<T> getTypeOfT() {
        return typeOfT;
    }

    public GenericService<T> getService() {
        return service;
    }

    public GenericDTOConverter<T, D> getConverter() {
        return converter;
    }
}
