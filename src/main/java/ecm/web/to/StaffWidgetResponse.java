package ecm.web.to;

import ecm.model.Staff;

/**
 * Created by dkarachurin on 31.01.2017.
 */
public class StaffWidgetResponse extends AbstractWidgetResponse {
    private Staff entity;

    public StaffWidgetResponse() {
    }

    public StaffWidgetResponse(String template, String script, Staff staff) {
        super(template, script);
        this.entity = staff;
    }

    public Staff getEntity() {
        return entity;
    }

    public void setEntity(Staff entity) {
        this.entity = entity;
    }
}
