package predictem.data;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries ({
	@NamedQuery(name="findGuessByQuestion",
			query="SELECT g FROM Guess g WHERE g.questionId = :questionId"),
	@NamedQuery(name="findGuessesByGameAndAccount",
			query="SELECT g FROM Guess g WHERE g.gameId = :gameId AND g.accountId = :accountId"),
})

@Entity public class Guess {
	private String id;
	private String gameId;
	private String questionId;
	private String accountId;
	private int choice;
	private boolean correct;
	
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
	
	public String getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public int getChoice() {
		return choice;
	}
	
	public void setChoice(int choice) {
		this.choice = choice;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
