package monoopoly;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import monoopoly.serializationfoo.FooContainer;

public class TestSerializationFoo {

	private FooContainer container;
	private static final String SEP = File.separator;
	private static final String STR = System.getProperty("user.home") + SEP + "serialization"
			+ SEP + "file.bin";
	
	
	@Test
	public void testInput() throws Exception{
		String path = this.generateFilePath();
		System.out.println(path);
		try {
			File file = new File(path);
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}		
		//ostream -> bstream -> file
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
		final String foo1 = "This";
		final String foo2 = "Is";
		final String foo3 = "Some";
		final String foo4 = "Foo";
		this.container = new FooContainer();
		container.addFoo(foo1);
		container.addFoo(foo2);
		container.addFoo(foo3);
		container.addFoo(foo4);
		out.writeObject(this.container);
		out.close();
	}
	
	@Test
	public void testRecovery() throws Exception{
		String path = this.generateFilePath();
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
		final FooContainer container = (FooContainer) in.readObject();
		final String foo1 = "This";
		final String foo2 = "Is";
		final String foo3 = "Some";
		final String foo4 = "Foo";
		List<String> fooList = new ArrayList<String>();
		fooList.add(foo1);
		fooList.add(foo2);
		fooList.add(foo3);
		fooList.add(foo4);
		assertTrue(container.getFoo().getFooList().equals(fooList));
		
	}
	
	private String generateFilePath() {
		return "bin/file.bin";
	}
}
