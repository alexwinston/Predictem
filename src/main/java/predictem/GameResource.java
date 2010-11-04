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
import predictem.data.Question;

import com.google.gson.GsonBuilder;

@Path("/game/{id}")
public class GameResource {
	
	private ObjectDatastore datastore;
	
	public GameResource() {
		this.datastore = new ObjectDatastore();
	}
	
	@GET @Path("/subscribe") @Suspend(listeners={GameEventListener.class}) @Produces("text/plain")
	public Broadcastable subscribe(@PathParam("id") Broadcaster category) {
		return new Broadcastable(category);
	}
	
	// TODO: Check aid cookie and implement timestamp check to limit calls
	@POST @Path("/question/create") @Consumes("application/json") @Broadcast @Produces("text/plain")
	public Broadcastable pose(@PathParam("id") Broadcaster category, Question question) {
		// Broadcast the creation of the new question
		return new Broadcastable(
				new GsonBuilder().create().toJson(this.create(question)), category);
	}
	
	@GET @Path("/questions/{begin}/{count}") @Produces("application/json")
	public List<Question> questions(@PathParam("id") String id, @PathParam("begin") int begin, @PathParam("count") int count) {
		return this.datastore.findQuestionsByGame(id, begin, count);
	}
	
	private Question create(Question question) {
		return this.datastore.createQuestion(question);
	}
}
