package predictem;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import predictem.data.Account;
import predictem.data.ObjectDatastore;
import predictem.data.ObjectDatastoreMock;

@Path("/account")
public class AccountResource {
	ObjectDatastore datastore;
	
	public AccountResource() {
		this.datastore = new ObjectDatastore();
	}
	
	@PUT @Path("/login") @Consumes(MediaType.APPLICATION_JSON)
	public Response login(Login login) {
		try {
			// Check to see if the email already exists
			Account account = this.datastore.findAccountByEmailAndPassword(
					login.getEmail(), login.getPassword());
			
			// Create a new cookie with the account uuid for future requests
			return Response.status(200).cookie(new NewCookie("aid", account.getId())).build();
		} catch (NoResultException e) {
			// The email exists so return a 400 (Bad Request)
			return Response.status(400).build();
		}
	}
	
	@PUT @Path("/register") @Consumes(MediaType.APPLICATION_JSON)
	public Response register(Account account) {
		try {
			// Check to see if the email already exists
			this.datastore.findAccountByEmail(account.getEmail());
		} catch (NoResultException e) {
			// The email doesn't exist so create the new account
			this.datastore.createAccount(account);
			
			// Create a new cookie with the account uuid for future requests
			return Response.status(200).cookie(new NewCookie("aid", account.getId())).build();
		}
		
		// The email exists so return a 400 (Bad Request)
		return Response.status(400).build();
	}
}
