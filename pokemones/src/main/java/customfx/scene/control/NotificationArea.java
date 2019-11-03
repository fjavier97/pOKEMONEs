package customfx.scene.control;

import java.io.IOException;

import com.pokemon.pokemones.core.event.NotificationEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;

public class NotificationArea extends ListView<NotificationEvent> implements NotificationRenderer{
	
	private final FXMLLoader loader;
	
	public NotificationArea() {
		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/templates/fx/notification/Notification.fxml"));
		setPickOnBounds(false);
		setCellFactory(list-> {
			NotificationCell cell = new NotificationCell();
			cell.setNotificationRenderer(this);
			return cell;
		});
	}

	public NotificationCellGraphic renderNotification(final NotificationEvent item){
		NotificationCellGraphic view = new NotificationCellGraphic();
		try {
			loader.setController(view);
			loader.setRoot(view);
			loader.load();	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		view.getCloseButton().setOnAction(e->getItems().remove(item));
		return view;
	}
	
}
