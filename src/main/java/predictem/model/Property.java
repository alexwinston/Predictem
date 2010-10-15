package predictem.model;

public class Property<T> {
	private T t;
	
	private Property() {}
	
	public Property(T t) {
		if (t == null)
			throw new IllegalArgumentException("Property cannot be initialized with null");
		this.t = t;
	}
	
	public T get() {
		return this.t;
	}
	
	public void set(T t) {
		this.t = t;
	}
	
	public String toString() {
		return this.t.toString();
	}
}
