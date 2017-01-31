package ecm.web.to;

import ecm.model.Document;

/**
 * Created by dkarachurin on 23.01.2017.
 */

public class DocumentWidgetResponse extends AbstractWidgetResponse{
    private Document entity;

    public DocumentWidgetResponse() {
    }

    public DocumentWidgetResponse(String template, String script, Document document) {
        super(template, script);
        this.entity = document;
    }

    public Document getEntity() {
        return entity;
    }

    public void setEntity(Document entity) {
        this.entity = entity;
    }
}
