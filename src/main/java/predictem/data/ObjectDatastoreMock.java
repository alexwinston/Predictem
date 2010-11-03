package predictem.data;

import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.NoResultException;

public class ObjectDatastoreMock {
	public static final ArrayList<Account> accounts = new ArrayList<Account>();
	public static final ArrayList<Game> games = new ArrayList<Game>();
	
	public ObjectDatastoreMock() {
	}

	public Account createAccount(Account account) {
		account.setId(UUID.randomUUID().toString());
		accounts.add(account);
		
		return account;
	}
	
	public Account findAccountByEmail(String email) {
		for (Account account : accounts) {
			if (account.getEmail().equals(email))
				return account;
		}
		
		throw new NoResultException();
	}
	
	public Account findAccountByEmailAndPassword(String email, String password) {
		for (Account account : accounts) {
			if (account.getEmail().equals(email) && account.getPassword().equals(password))
				return account;
		}
		
		throw new NoResultException();
	}
	
	public Game createGame(Game game) {
		game.setId(UUID.randomUUID().toString());
		games.add(game);
		
		return game;
	}
}
