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
    private String[] scripts;

    public WidgetResponse() {
    }

    public WidgetResponse(T model, String template, String[] scripts) {
        this.model = model;
        this.template = template;
        this.scripts = scripts;
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

    public String[] getScripts() {
        return scripts;
    }

    public void setScripts(String[] scripts) {
        this.scripts = scripts;
    }
}
