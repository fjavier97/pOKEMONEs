package customfx.scene.control;
 
import com.pokemon.pokemones.core.event.NotificationEvent;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NotificationCell extends ListCell<NotificationEvent>{
			
	private NotificationRenderer notificationRenderer;
	
	public NotificationRenderer getNotificationRenderer() {
		return notificationRenderer;
	}

	public void setNotificationRenderer(NotificationRenderer notificationRenderer) {
		this.notificationRenderer = notificationRenderer;
	}
		
	protected @Override void updateItem(final NotificationEvent item, final boolean isEmpty){
		super.updateItem(item, isEmpty);		
		if(isEmpty){
			setText(null);
			setGraphic(null);
			setBackground(Background.EMPTY);
		}else{
			if(getNotificationRenderer() != null){
				NotificationCellGraphic view = getNotificationRenderer().renderNotification(item);
				view.setText(item.getMessage());
				view.setColor(NotificationUtils.getColorFromThreat(item.getThreat()));	
				
				setText(null);				
				setGraphic(view);
			}else{
				setText(item.getMessage());
			}
		}
	}
}
