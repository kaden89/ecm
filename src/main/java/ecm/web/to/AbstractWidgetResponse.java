package ecm.web.to;

/**
 * Created by dkarachurin on 31.01.2017.
 */
public class AbstractWidgetResponse {
    private String template;
    private String script;

    public AbstractWidgetResponse() {
    }

    public AbstractWidgetResponse(String template, String script) {
        this.template = template;
        this.script = script;
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

    public void setScript(String script) {
        this.script = script;
    }
}
