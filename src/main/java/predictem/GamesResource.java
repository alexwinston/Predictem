package predictem;

import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;

import predictem.model.Game;
import predictem.model.Property;
import predictem.model.PropertyDeserializer;
import predictem.model.PropertySerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("games/{id}")
public class GamesResource {
	private Gson gson;
	private static HashMap<String, Game> GAMES = new HashMap<String, Game>();
	@PathParam("id") Broadcaster games;
	
	static {
		GAMES.put("1", new Game("1", "Game 1"));
		GAMES.put("2", new Game("2", "Game 2"));
	}
	
	public GamesResource() {
		GsonBuilder gson = new GsonBuilder();
		gson.registerTypeAdapter(Property.class, new PropertySerializer());
		gson.registerTypeAdapter(Property.class, new PropertyDeserializer());
		
		this.gson = gson.create();
	}
	
	@GET @Suspend(listeners={GameEventListener.class})
	public Broadcastable subscribe() {
		return new Broadcastable(this.games);
	}
	
	@POST @Broadcast
	public Broadcastable publish(String json) {
		// Create a UUID for the new game
		System.out.println(json);
		Game game = this.gson.fromJson(json, Game.class);
		game.id.set(UUID.randomUUID().toString());
		
		// Persist the new game
		GAMES.put(game.id.toString(), game);
		
		// Broadcast the creation of the new game
		return new Broadcastable(this.gson.toJson(game), this.games);
	}

	@GET @Path("recent") @Produces(MediaType.APPLICATION_JSON)
	public String recent() {
		return this.gson.toJson(GAMES.values());
	}
}
