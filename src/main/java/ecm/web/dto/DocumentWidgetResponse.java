package ecm.web.dto;

/**
 * Created by dkarachurin on 23.01.2017.
 */

public class DocumentWidgetResponse extends AbstractWidgetResponse {
    private AbstractDocumentDTO entity;

    public DocumentWidgetResponse() {
    }

    public DocumentWidgetResponse(String template, String script, AbstractDocumentDTO document) {
        super(template, script);
        this.entity = document;
    }

    public AbstractDocumentDTO getEntity() {
        return entity;
    }

    public void setEntity(AbstractDocumentDTO entity) {
        this.entity = entity;
    }
}
