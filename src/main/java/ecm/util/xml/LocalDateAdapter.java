package ecm.util.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;

/**
 * Created by dkarachurin on 25.01.2017.
 */

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) throws Exception {

        return LocalDate.parse(v.split("T")[0]);
    }

    public String marshal(LocalDate v) throws Exception {
        return v == null ? null : v.toString();
    }
}
