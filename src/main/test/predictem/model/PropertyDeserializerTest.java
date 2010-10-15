package predictem.model;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PropertyDeserializerTest extends TestCase {
	public void testDeserialize() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Property.class, new PropertySerializer());
		gsonBuilder.registerTypeAdapter(Property.class, new PropertyDeserializer());
		
		Gson gson = gsonBuilder.create();
		
		String json1 = "{ \"description\":\"Game without id\" }";
		Game g1 = gson.fromJson(json1, Game.class);
		Assert.assertEquals("", g1.id.toString());
		Assert.assertEquals("Game without id", g1.description.toString());
		
		Game g2 = new Game("1", "Description 1");
		Game g3 = gson.fromJson(gson.toJson(g2), Game.class);
		Assert.assertEquals(g2.id.toString(), g3.id.toString());
		Assert.assertEquals(g2.description.toString(), g3.description.toString());
	}
}
