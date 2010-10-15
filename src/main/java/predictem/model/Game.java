package predictem.model;

public class Game {
	public final Property<String> id = new Property<String>();
	public final Property<Integer> count = new Property<Integer>();
	
	public Game(String id) {
		this.id.set(id);
		this.count.set(0);
	}
}
