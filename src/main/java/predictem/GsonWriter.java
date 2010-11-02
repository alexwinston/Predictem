package predictem;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.spi.resource.Singleton;

@Provider @Produces(MediaType.APPLICATION_JSON) @Singleton 
public final class GsonWriter implements MessageBodyWriter<Object> { 
    private Gson gson; 

    public GsonWriter() { 
        this.gson = new GsonBuilder().create(); 
    } 

    @Override 
    public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) { 
        return -1; 
    } 

    @Override 
    public void writeTo(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) 
            throws IOException, WebApplicationException { 
        entityStream.write(gson.toJson(t).getBytes()); 
    } 

    @Override 
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) { 
        return true; 
    } 

} 
