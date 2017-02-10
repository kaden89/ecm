package ecm.web.dto.filtering;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dkarachurin on 10.02.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RootFilterDTO {
    private Comparison op;
    private List<FilterDTO> data;

    public RootFilterDTO() {
    }

    public RootFilterDTO(Comparison op, List<FilterDTO> data) {
        this.op = op;
        this.data = data;
    }

    public Comparison getOp() {
        return op;
    }

    public void setOp(Comparison op) {
        this.op = op;
    }

    public List<FilterDTO> getData() {
        return data;
    }

    public void setData(List<FilterDTO> data) {
        this.data = data;
    }
}
