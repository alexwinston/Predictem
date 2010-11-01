package predictem.model;

import javax.persistence.Entity;

@Entity public class Player {
	private String email;
	private String username;
	private String password;
	
	public Player(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
