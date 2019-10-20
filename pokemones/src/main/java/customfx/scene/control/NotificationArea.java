package customfx.scene.control;

import com.pokemon.pokemones.core.event.NotificationEvent;

import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class NotificationArea extends ListView<NotificationEvent>{
	
	public NotificationArea() {
		setPickOnBounds(false);
		setCellFactory(list-> new NotificationCell());
	}

}
