package ecm.util;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dkarachurin on 23.01.2017.
 */
@XmlRootElement
public class WidgetResponse {
    private Object model;
    private String template;
    private String[] scripts;

    public WidgetResponse() {
    }

    public WidgetResponse(Object model, String template, String[] scripts) {
        this.model = model;
        this.template = template;
        this.scripts = scripts;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
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
