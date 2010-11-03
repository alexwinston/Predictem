package predictem.data;

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
		// Generate a uuid to identify the account
		account.setId(UUID.randomUUID().toString());
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
		// Generate a uuid to identify the account
		game.setId(UUID.randomUUID().toString());
		// Persist the account and commit the transaction
		this.em.persist(game);
		this.em.getTransaction().commit();
		
		return game;
	}
}
