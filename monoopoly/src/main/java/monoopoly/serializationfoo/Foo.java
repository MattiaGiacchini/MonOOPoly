package monoopoly.serializationfoo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Foo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 191098879996192112L;
	private List<String> fooList;
	
	public Foo() {
		this.fooList = new ArrayList<String>();
	}
	
	public List<String> getFooList(){
		return this.fooList;
	}
}
