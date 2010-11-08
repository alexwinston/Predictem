package predictem;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import predictem.data.Account;
import predictem.data.ObjectDatastore;

@Path("/account")
public class AccountResource {
	ObjectDatastore datastore;
	
	public AccountResource() {
		this.datastore = new ObjectDatastore();
	}
	
	@POST @Path("/login") @Consumes(MediaType.APPLICATION_JSON)
	public Response login(Login login) {
		try {
			// Check to see if the email already exists
			Account account = this.datastore.findAccountByEmailAndPassword(
					login.getEmail(), login.getPassword());
			
			// Create a new cookie with the account uuid for future requests
			return Response.status(200).cookie(
					new NewCookie(
							new Cookie("aid", account.getId(), "/rs", "localhost"))).build();
			
			// TODO Encapsulate NoResultException to eliminate JPA persistence dependency
		} catch (NoResultException e) {
			// The email exists so return a 400 (Bad Request)
			return Response.status(400).build();
		}
	}
	
	@POST @Path("/register") @Consumes(MediaType.APPLICATION_JSON)
	public Response register(Account account) {
		try {
			// Check to see if the email already exists
			this.datastore.findAccountByEmail(account.getEmail());
		} catch (NoResultException e) {
			// The email doesn't exist so create the new account
			this.datastore.create(account);
			
			// Create a new cookie with the account uuid for future requests
			return Response.status(200).cookie(
					new NewCookie(
							new Cookie("aid", account.getId(), "/rs", "localhost"))).build();
		}
		
		// The email exists so return a 400 (Bad Request)
		return Response.status(400).build();
	}
}
