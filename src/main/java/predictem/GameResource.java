package predictem;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;


@Path("/game")
public class GameResource {
	private @PathParam("id") Broadcaster game;
	
	@GET @Path("{id}") @Suspend(listeners={GameEventListener.class})
	public Broadcastable subscribe() {
		return new Broadcastable(game);
	}
	
	@POST @Path("{id}") @Broadcast
	public Broadcastable publish(@FormParam("message") String message) {
		return new Broadcastable(message + "\n", game);
    }
}
