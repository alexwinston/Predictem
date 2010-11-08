package predictem;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;

import predictem.data.Game;
import predictem.data.ObjectDatastore;

import com.google.gson.GsonBuilder;

@Path("/games/{category}")
public class GamesResource {
	
	private ObjectDatastore datastore;
	
	public GamesResource() {
		this.datastore = new ObjectDatastore();
	}
	
	@GET @Path("/subscribe") @Suspend(listeners={GameEventListener.class}) @Produces("text/plain")
	public Broadcastable subscribe(@PathParam("category") Broadcaster category) {
		return new Broadcastable(category);
	}
	
	// TODO: Check aid cookie and implement timestamp check to limit calls
	@POST @Path("/create") @Consumes("application/json") @Broadcast @Produces("text/plain")
	public Broadcastable create(@PathParam("category") Broadcaster category, Game game) {
		// Broadcast the creation of the new game
		return new Broadcastable(
				new GsonBuilder().create().toJson(this.create(game)), category);
	}
	
	@GET @Path("/list/{begin}/{count}") @Produces("application/json")
	public List<Game> list(@PathParam("category") String category, @PathParam("begin") int begin, @PathParam("count") int count) {
		return this.datastore.findGamesByCategory(category, begin, count);
	}
	
	private Game create(Game game) {
		return this.datastore.create(game);
	}
}
