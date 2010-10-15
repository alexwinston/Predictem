package predictem.model;

public class Property<T> {
	private T t;
	
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
