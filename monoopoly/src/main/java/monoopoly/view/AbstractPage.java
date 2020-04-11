package monoopoly.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public abstract class AbstractPage implements Page {

	private FXMLLoader loader;
	private Optional<Pane> root = Optional.empty();

	/**
	 * This constructor set the FXML file to get for the current view
	 * 
	 * @param fxmlFile URL
	 */
	public AbstractPage(URL fxmlFile) {
		this.loader = new FXMLLoader(fxmlFile);
	}

	@Override
	abstract public String getID();

	@Override
	abstract public String getTitle();

	@Override
	public Pane getContent() throws IOException {
		if (this.root.isEmpty()) {
			this.root = Optional.of(this.loader.load());
		}
		return this.root.get();
	}

	@Override
	public void setController(ViewController controller) {
		this.loader.setController(controller);
	}

	@Override
	public ViewController getController() {
		return this.loader.getController();
	}

}
