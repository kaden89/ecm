
package ecm.web;

import com.google.gson.*;
import ecm.dao.ImageDaoJPA;
import ecm.model.*;
import ecm.service.GenericService;
import ecm.service.ImageService;
import ecm.util.paging.Page;
import ecm.util.xml.ByteArrayAdapter;
import ecm.util.xml.GsonExclusionStrategy;
import ecm.util.xml.GsonUtil;
import ecm.web.dto.*;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by dkarachurin on 24.01.2017.
 */
public class AbstractRestController {

    //TODO impl logger
    @Inject
    private GsonUtil gsonUtil;

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
    private DTOConverter<Document, AbstractDocumentDTO> documentDTOConverter;

    @Inject
    private DTOConverter<Staff, AbstractStaffDTO> staffDTOConverter;


    public String toJson(Object obj) {
        return gsonUtil.getGson().toJson(obj);
    }

    public Object fromJson(String json, Class clazz){
        return getGsonUtil().getGson().fromJson(json, clazz);
    }

    public Response getStaffPageResponse(Page page){
        return Response.ok(toJson(getStaffDTOConverter()
                .toDtoCollection(new ArrayList<>(page.getItems()))))
                .header("Content-Range", "items "+page.getStartIndex()+"-" + page.getEndIndex() + "/" + page.getAllItemsCount())
                .build();
    }
    public Response getDocPageResponse(Page page){
        return Response.ok(toJson(getDocumentDTOConverter()
                .toDtoCollection(new ArrayList<>(page.getItems()))))
                .header("Content-Range", "items "+page.getStartIndex()+"-" + page.getEndIndex() + "/" + page.getAllItemsCount())
                .build();
    }

    public GsonUtil getGsonUtil() {
        return gsonUtil;
    }

    public void setGsonUtil(GsonUtil gsonUtil) {
        this.gsonUtil = gsonUtil;
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

    public DTOConverter<Document, AbstractDocumentDTO> getDocumentDTOConverter() {
        return documentDTOConverter;
    }

    public void setDocumentDTOConverter(DTOConverter<Document, AbstractDocumentDTO> documentDTOConverter) {
        this.documentDTOConverter = documentDTOConverter;
    }

    public DTOConverter<Staff, AbstractStaffDTO> getStaffDTOConverter() {
        return staffDTOConverter;
    }

    public void setStaffDTOConverter(DTOConverter<Staff, AbstractStaffDTO> staffDTOConverter) {
        this.staffDTOConverter = staffDTOConverter;
    }

    public GenericService<Post> getPostService() {
        return postService;
    }

    public void setPostService(GenericService<Post> postService) {
        this.postService = postService;
    }
}
