package ecm.web.dto;

import ecm.model.Staff;

/**
 * Created by dkarachurin on 31.01.2017.
 */
public class StaffWidgetResponse extends AbstractWidgetResponse {
    private AbstractStaffDTO entity;

    public StaffWidgetResponse() {
    }

    public StaffWidgetResponse(String template, String script, AbstractStaffDTO staff) {
        super(template, script);
        this.entity = staff;
    }

    public AbstractStaffDTO getEntity() {
        return entity;
    }

    public void setEntity(AbstractStaffDTO entity) {
        this.entity = entity;
    }
}
