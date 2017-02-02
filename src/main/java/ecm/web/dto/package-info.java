/**
 * Created by dkarachurin on 25.01.2017.
 */
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type = LocalDate.class,
                value = LocalDateAdapter.class)
})
package ecm.web.dto;

import ecm.util.xml.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;
