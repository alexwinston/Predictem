package predictem;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;

import predictem.data.Guess;
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
	@POST @Path("/question") @Consumes("application/json") @Broadcast @Produces("text/plain")
	public Broadcastable create(Question question, @PathParam("id") Broadcaster game, @CookieParam("aid") String accountId) {
		if (accountId == null)
			throw new WebApplicationException(Response.status(400).build());
		
		// Broadcast the creation of the new question
		return new Broadcastable(
				new GsonBuilder().create().toJson(this.create(question, accountId)), game);
	}

	public Question create(Question question, String accountId) {
		// Set the account that created the new question
		question.setAccountId(accountId);
		
		return this.datastore.create(question);
	}
	
	@GET @Path("/questions") @Produces("application/json")
	public List<Question> questions(@PathParam("id") String gameId) {
		return this.datastore.findQuestionsByGame(gameId);
	}
	
	@POST @Path("/guess") @Consumes("application/json")
	public Response guess(Guess guess, @CookieParam("aid") String accountId) {
		// Set the account that created the new question
		guess.setAccountId(accountId);
		
		this.datastore.createOrUpdate(guess);
		
		return Response.status(200).build();
	}
	
	@GET @Path("/guesses") @Produces("application/json")
	public List<Guess> guesses(@PathParam("id") String gameId, @CookieParam("aid") String accountId) {
		return this.datastore.findGuessesByGameAndAccount(gameId, accountId);
	}
	
	@POST @Path("/answer") @Consumes("application/json") @Broadcast @Produces("text/plain")
	public Response answer(Question question, @PathParam("id") Broadcaster game, @CookieParam("aid") String accountId) {
		// Set the account that created the new question
		question.setAccountId(accountId);
		
		/**
		this.datastore.update(question);
		
		// Broadcast the answer to the question, including statistics
		return new Broadcastable(
				new GsonBuilder().create().toJson(this.create(question, accountId)), game);
		**/
		return null;
	}
}
