package predictem;

import javax.ws.rs.Path;

import predictem.data.ObjectDatastore;

@Path("/lobby")
public class LobbyResource {
	ObjectDatastore datastore;
	
	public LobbyResource() {
		this.datastore = new ObjectDatastore();
	}
}
