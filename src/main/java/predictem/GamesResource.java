package predictem;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import predictem.model.Game;
import predictem.model.Property;
import predictem.model.PropertySerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("games")
public class GamesResource {
	private Gson gson;
	private static HashMap<String, Game> GAMES = new HashMap<String, Game>();
	
	static {
		GAMES.put("1", new Game("1"));
		GAMES.put("2", new Game("2"));
	}
	
	public GamesResource() {
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(Property.class, new PropertySerializer());
		
		this.gson = gson.create();
	}

	@GET @Produces(MediaType.APPLICATION_JSON)
	public String games() {
		return this.gson.toJson(GAMES.values());
	}
}
