package predictem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;

import predictem.data.Guess;
import predictem.data.ObjectDatastore;
import predictem.data.Question;

import com.google.gson.GsonBuilder;

@Path("/game/{gid}")
public class GameResource {
	
	private ObjectDatastore datastore;
	
	public GameResource() {
		this.datastore = new ObjectDatastore();
	}
	
	@GET @Path("/subscribe") @Suspend(listeners={GameEventListener.class}) @Produces("text/plain")
	public Broadcastable subscribe(@PathParam("gid") Broadcaster category) {
		return new Broadcastable(category);
	}
	
	// TODO: Check aid cookie and implement timestamp check to limit calls
	@POST @Path("/question") @Consumes("application/json") @Broadcast @Produces("text/plain")
	public Broadcastable create(Question question, @PathParam("gid") Broadcaster game) {
		// TODO Check that the accountId is not null and the account can create questions for this game
		
		// Broadcast the creation of the new question
		return new Broadcastable(
				new GsonBuilder().create().toJson(this.datastore.create(question)), game);
	}
	
	// TODO Exclude accountId when Questions are serialized into json
	@GET @Path("/questions/{aid}") @Produces("application/json")
	public List<QuestionContent> questions(@PathParam("gid") String gameId, @PathParam("aid") String accountId) {
		List<QuestionContent> questions = new ArrayList<QuestionContent>();
		
		for (Question q : this.datastore.findQuestionsByGame(gameId)) {
			QuestionContent qc = new QuestionContent(q);
			qc.setAnswerable(q.getAccountId().equals(accountId));
			
			try {
				qc.setGuess(this.datastore.findGuessByQuestionAndAccount(q.getId(), accountId));
			} catch (NoResultException e) { }
			
			questions.add(qc);
		}
		
		return questions;
	}
	
	@POST @Path("/guess") @Consumes("application/json")
	public Response guess(Guess guess) {
		if (guess.getAccountId() == null)
			return Response.status(400).build();
			
		this.datastore.createOrUpdate(guess);
		
		return Response.status(200).build();
	}
	
	@POST @Path("/answer") @Consumes("application/json") @Broadcast @Produces("text/plain")
	public Broadcastable answer(AnswerContent answer, @PathParam("gid") Broadcaster game) {
		Question question = this.datastore.findQuestionById(answer.getQuestionId());
		question.setAnswered(true);
		question.setAnswer(answer.getAnswer());
		
		// TODO Update statistics
		
		this.datastore.update(question);
		
		// Broadcast the answer to the question, including statistics
		return new Broadcastable(
				new GsonBuilder().create().toJson(new QuestionContent(question)), game);
	}
}
