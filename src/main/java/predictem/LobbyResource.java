package predictem;

import javax.ws.rs.Path;

import predictem.data.EntityDatastore;

@Path("/lobby")
public class LobbyResource {
	EntityDatastore datastore;
	
	public LobbyResource() {
		this.datastore = new EntityDatastore();
	}
}
