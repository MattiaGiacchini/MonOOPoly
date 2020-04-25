package monoopoly.serializationfoo;

import java.io.Serializable;

public class FooContainer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6233511118823697251L;
	
	private Foo foo;
	
	public FooContainer() {
		this.foo = new Foo();
	}
	
	public void addFoo(String addedFoo) {
		this.foo.getFooList().add(addedFoo);
	}
	
	public Foo getFoo() {
		return this.foo;
	}
	
	

}
