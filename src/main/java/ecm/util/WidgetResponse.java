package ecm.util;

import ecm.dao.Storable;
import ecm.model.Person;

import javax.xml.bind.annotation.*;

/**
 * Created by dkarachurin on 23.01.2017.
 */

public class WidgetResponse<T> {

    private T model;
    private String template;
    private String script;

    public WidgetResponse() {
    }

    public WidgetResponse(T model, String template, String script) {
        this.model = model;
        this.template = template;
        this.script = script;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String scripts) {
        this.script = scripts;
    }
}
