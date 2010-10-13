package predictem;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.view.Viewable;

@Path("/lobby")
public class LobbyResource {
	@GET @Produces(MediaType.TEXT_HTML)
	public Viewable index() {
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("test", "test");
		
		return new Viewable("/lobby.jsp", model);
	}
}
