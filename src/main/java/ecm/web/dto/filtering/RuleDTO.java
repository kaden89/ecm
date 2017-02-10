package ecm.web.dto.filtering;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by dkarachurin on 10.02.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RuleDTO {
    private String op;
    private String data;
    private boolean isCol;

    public RuleDTO() {
    }

    public RuleDTO(String op, String data, boolean isCol) {
        this.op = op;
        this.data = data;
        this.isCol = isCol;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isCol() {
        return isCol;
    }

    public void setCol(boolean col) {
        isCol = col;
    }
}
