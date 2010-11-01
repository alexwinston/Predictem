package predictem;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import predictem.model.Player;

import com.sun.jersey.api.view.Viewable;

@Path("/lobby")
public class LobbyResource {
	EntityManager em;
	
	public LobbyResource() {
		this.em = Persistence.createEntityManagerFactory("predictemPersistence").createEntityManager();
	}
	
	@GET @Produces(MediaType.TEXT_HTML)
	public Viewable index() {
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("test", "test");
		
		this.em.getTransaction().begin();
		this.em.persist(new Player("email", "username", "password"));
		this.em.getTransaction().commit();
		
		for (Player p : this.em.createQuery("SELECT p FROM Player p", Player.class).getResultList())
			System.out.println(p.getEmail());
		
		return new Viewable("/lobby.jsp", model);
	}
}
