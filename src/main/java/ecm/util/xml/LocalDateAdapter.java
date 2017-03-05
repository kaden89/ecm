package ecm.util.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * Адаптер для даты пришедшей с клиента
 * @author dkarachurin
 */

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    public LocalDate unmarshal(String v) throws Exception {

        return LocalDate.parse(v.split("T")[0]);
    }

    public String marshal(LocalDate v) throws Exception {
        return v == null ? null : v.toString();
    }
}
