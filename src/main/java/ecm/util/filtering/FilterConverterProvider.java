package ecm.util.filtering;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by dkarachurin on 13.02.2017.
 */
//Not working. Don't know why. Have to create Filter via GSON from string in controllers
@Provider
public class FilterConverterProvider implements ParamConverterProvider {
    @Inject
    private Gson gson;

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if (aClass.getName().equals(Filter.class.getName())) {

            return new ParamConverter<T>() {

                @SuppressWarnings("unchecked")
                @Override
                public T fromString(String value) {
                    Filter filter = gson.fromJson(value, Filter.class);
                    return (T) filter;
                }

                @Override
                public String toString(T bean) {
                    return bean.toString();
                }

            };
        }
        return null;
    }
}
