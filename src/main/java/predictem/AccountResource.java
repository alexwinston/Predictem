package predictem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import predictem.model.Player;

@Path("/account")
public class AccountResource {
	EntityManager em;
	
	public AccountResource() {
		EntityManagerFactory emf =
			Persistence.createEntityManagerFactory("predictemPersistence");
		this.em = emf.createEntityManager();
	}
	
	@POST
	public void create(Player account) {
	}
	
	@GET @Path("exists/{email}") @Produces(MediaType.APPLICATION_JSON)
	public String exists(@PathParam("email") String email) {
		return "{ \"exists\": true }";
	}
	
	@GET @Path("{id}") @Produces(MediaType.APPLICATION_JSON)
	public Player get(@PathParam("id") String id) {
		return new Player("email", "username", "password");
		//this.em.createQuery("SELECT p FROM Player p WHERE p.id = :value", Player.class).getSingleResult()id;
	}
}
