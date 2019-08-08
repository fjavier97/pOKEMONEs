package customfx.scene.control;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.Initializable;

public abstract class AbstractController implements Initializable{
	
	public AbstractController() {
		LOG = LoggerFactory.getLogger(AbstractController.class);
	}

	protected final Logger LOG;
	
	public @Override abstract void initialize(final URL location, final ResourceBundle resources);
	
	public abstract void injectArguments(final Map<String, Object> args);

}
