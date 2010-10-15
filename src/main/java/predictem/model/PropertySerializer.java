package predictem.model;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PropertySerializer implements JsonSerializer<Property<?>> {
	public JsonElement serialize(Property<?> src, Type typeOfSrc, JsonSerializationContext context) {
		if (src.get() instanceof Number)
			return new JsonPrimitive((Number)src.get());
		
		return new JsonPrimitive(src.toString());
	}
}
