package predictem.model;

import java.util.UUID;

public class Game {
	public final Property<String> id = new Property<String>("");
	public final Property<String> description = new Property<String>("");
	
	private Game() {}
	
	public Game(String description) {
		this(UUID.randomUUID().toString(), description);
	}
	
	public Game(String id, String description) {
		this.id.set(id);
		this.description.set(description);
	}
}
