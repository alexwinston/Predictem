package predictem;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.spi.resource.Singleton;

@Provider @Produces(MediaType.APPLICATION_JSON) @Singleton 
public final class GsonProvider implements MessageBodyWriter<Object>, MessageBodyReader<Object> { 
    private Gson gson; 

    public GsonProvider() { 
        this.gson = new GsonBuilder().create(); 
    }
    
    @Override 
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) { 
        return true; 
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
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
		return this.gson.fromJson(new InputStreamReader(entityStream), genericType);
	} 

} 
