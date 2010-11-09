package predictem;

import java.util.List;

import predictem.data.Guess;
import predictem.data.Question;

public class QuestionContent {
	private String id;
	private String gameId;
	private String description; 
	private List<String> choices;
	private Guess guess;
	private boolean answerable;
	private boolean answered;
	private int answer;
	private boolean timed;
	private int timeLimit;
	
	public QuestionContent(Question question) {
		this.id = question.getId();
		this.gameId = question.getGameId();
		this.description = question.getDescription();
		this.choices = question.getChoices();
		this.answered = question.isAnswered();
		this.answer = question.getAnswer();
		this.timed = question.isTimed();
		this.timeLimit = question.getTimeLimit();
	}
	
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
	
	public Guess getGuess() {
		return this.guess;
	}
	public void setGuess(Guess guess) {
		this.guess = guess;
	}
	
	public boolean isAnswerable() {
		return this.answerable;
	}
	public void setAnswerable(boolean answerable) {
		this.answerable = answerable;
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
}
