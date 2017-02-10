package ecm.web.dto.filtering;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dkarachurin on 10.02.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class FilterDTO {
    @XmlElement(name = "op", required = true)
    private Comparison op;
    private List<RuleDTO> data;

    public FilterDTO() {
    }

    public FilterDTO(Comparison op, List<RuleDTO> data) {
        this.op = op;
        this.data = data;
    }

    public Comparison getOp() {
        return op;
    }

    public void setOp(Comparison op) {
        this.op = op;
    }

    public List<RuleDTO> getData() {
        return data;
    }

    public void setData(List<RuleDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (RuleDTO rule : data) {
            if (rule.isCol());
        }
        return "FilterDTO{" +
                "op='" + op + '\'' +
                ", data=" + data +
                '}';
    }
}
