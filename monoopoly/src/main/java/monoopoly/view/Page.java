package monoopoly.view;

import java.io.IOException;

import javafx.scene.layout.Pane;

/**
 * This interface is used to manage each JavaFx page
 */
public interface Page {

	/**
	 * This method is used to get the {@link Page} identifier
	 * 
	 * @return the page ID
	 */
	public String getID();

	/**
	 * This method is used to get the {@link Page} title
	 * 
	 * @return the page title
	 */
	public String getTitle();

	/**
	 * This method return the JavaFx {@link Page} content
	 * 
	 * @return JavaFx Pane with the {@link Page} content
	 * @throws IOException
	 */
	public Pane getContent() throws IOException;

	/**
	 * This method is used to set the {@link ViewController} for this {@link Page}
	 * 
	 * @param view controller
	 */
	public void setController(ViewController controller);

	/**
	 * This method is used to get the {@link ViewController} of this {@link Page}
	 * 
	 * @return the {@link ViewController}
	 */
	public ViewController getController();

}
