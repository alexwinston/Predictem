package predictem.model;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class PropertyDeserializer implements JsonDeserializer<Property<?>> {
	public Property<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonPrimitive()) {
			if (json.getAsJsonPrimitive().isString())
				return new Property<String>(json.getAsJsonPrimitive().getAsString());
			
			return new Property<Number>(json.getAsJsonPrimitive().getAsNumber());
		}
		
		// TODO Determine what a JsonObject actually is
		return new Property(json.getAsJsonObject().getAsString());
	}
}
