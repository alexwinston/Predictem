package predictem.data;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries ({
	@NamedQuery(name="findAccountByEmail", query="SELECT a FROM Account a WHERE a.email = :email"),
	@NamedQuery(name="findAccountByEmailAndPassword", query="SELECT a FROM Account a WHERE a.email = :email AND a.password = :password")
})

@Entity public class Account {
	private String id;
	private String email;
	private String username;
	private String password;
	
	private Account() {}
	
	public Account(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
