package predictem.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class ObjectDatastore {
	private static EntityManagerFactory emf =
		Persistence.createEntityManagerFactory("predictemPersistence");
	
	// Predict'em JPA EntityManager
	private EntityManager em;
	
	public ObjectDatastore() {
		this.em = emf.createEntityManager();
	}

	public Account createAccount(Account account) {
		// Begin the transaction
		this.em.getTransaction().begin();
		// Generate a uuid to identify the account and set the creation date
		account.setId(UUID.randomUUID().toString());
		account.setCreationDate(new Date().getTime());
		// Persist the account and commit the transaction
		this.em.persist(account);
		this.em.getTransaction().commit();
		
		return account;
	}
	
	public Account findAccountByEmail(String email) {
		TypedQuery<Account> queryAccountByEmail = this.em.createNamedQuery("findAccountByEmail", Account.class);
		queryAccountByEmail.setParameter("email", email);
		
		return queryAccountByEmail.getSingleResult();
	}
	
	public Account findAccountByEmailAndPassword(String email, String password) {
		TypedQuery<Account> queryAccountByEmailAndPassword =
			this.em.createNamedQuery("findAccountByEmailAndPassword", Account.class);
		queryAccountByEmailAndPassword.setParameter("email", email);
		queryAccountByEmailAndPassword.setParameter("password", password);
		
		return queryAccountByEmailAndPassword.getSingleResult();
	}
	
	public Game createGame(Game game) {
		// Begin the transaction
		this.em.getTransaction().begin();
		// Generate a uuid to identify the account and set the creation date
		game.setId(UUID.randomUUID().toString());
		game.setCreationDate(new Date().getTime());
		// Persist the account and commit the transaction
		this.em.persist(game);
		this.em.getTransaction().commit();
		
		return game;
	}
	
	public List<Game> findGamesByCategory(String category) {
		return this.findGamesByCategory(category, 0, Integer.MAX_VALUE);
	}
	
	public List<Game> findGamesByCategory(String category, int begin, int count) {
		TypedQuery<Game> queryGamesByCategory =
			this.em.createNamedQuery("findGamesByCategory", Game.class);
		queryGamesByCategory.setParameter("category", category);
		queryGamesByCategory.setFirstResult(begin);
		if (count != Integer.MAX_VALUE)
			queryGamesByCategory.setMaxResults(count);
		
		return queryGamesByCategory.getResultList();
	}

	public Question createQuestion(Question question) {
		// Begin the transaction
		this.em.getTransaction().begin();
		// Generate a uuid to identify the account and set the creation date
		question.setId(UUID.randomUUID().toString());
		question.setCreationDate(new Date().getTime());
		// Persist the account and commit the transaction
		this.em.persist(question);
		this.em.getTransaction().commit();
		
		return question;
	}
	
	public List<Question> findQuestionsByGame(String gameId) {
		return this.findQuestionsByGame(gameId, 0, Integer.MAX_VALUE);
	}
	
	public List<Question> findQuestionsByGame(String gameId, int begin, int count) {
		TypedQuery<Question> queryQuestionsByGame =
			this.em.createNamedQuery("findQuestionsByGame", Question.class);
		queryQuestionsByGame.setParameter("gameId", gameId);
		queryQuestionsByGame.setFirstResult(begin);
		if (count != Integer.MAX_VALUE)
			queryQuestionsByGame.setMaxResults(count);
		
		return queryQuestionsByGame.getResultList();
	}
}
