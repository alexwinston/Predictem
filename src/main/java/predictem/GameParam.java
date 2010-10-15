package predictem;

import javax.ws.rs.WebApplicationException;

import predictem.model.Game;
import predictem.model.Property;
import predictem.model.PropertyDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameParam extends AbstractParam<Game> {
	private Gson gson;

	public GameParam(String json) throws WebApplicationException {
		super(json);
		
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(Property.class, new PropertyDeserializer());
		
		this.gson = gson.create();
	}

	@Override
	protected Game parse(String json) throws Throwable {
		return this.gson.fromJson(json, Game.class);
	}
}
