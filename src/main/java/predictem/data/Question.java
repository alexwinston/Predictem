package predictem.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries ({
	@NamedQuery(name="findQuestionsByGame",
			query="SELECT q FROM Question q WHERE q.gameId = :gameId ORDER BY q.creationDate DESC")
})

@Entity public class Question {
	private String id;
	private String gameId;
	private String accountId;
	private String description; 
	private List<String> choices;
	private boolean answered;
	private int answer;
	private boolean timed;
	private int timeLimit;
	private long createdDate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public String getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<String> getChoices() {
		return choices;
	}
	
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	
	public boolean isAnswered() {
		return answered;
	}
	
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	public boolean isTimed() {
		return timed;
	}
	
	public void setTimed(boolean timed) {
		this.timed = timed;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public long getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(long creationDate) {
		this.createdDate = creationDate;
	}
}
