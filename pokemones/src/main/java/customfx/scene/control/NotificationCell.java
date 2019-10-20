package customfx.scene.control;
 
import com.pokemon.pokemones.core.event.NotificationEvent;

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
		
	private HBox createView(final NotificationEvent evt) {
		HBox notificationNode = new HBox();
		notificationNode.setBackground(new Background(new BackgroundFill(Color.GRAY, new CornerRadii(2), new Insets(0))));
		notificationNode.setMinHeight(50);
		
		Button closeNotification = new Button("X");
		closeNotification.setOnAction(e->getListView().getItems().remove(evt));
		closeNotification.setBackground(Background.EMPTY);
		
		Pane colorPane = new Pane();
		colorPane.setPrefWidth(15);		
		final Color color;
		switch (evt.getThreat()) {
			case ERROR:		color = Color.RED;		break;	
			case SUCCESS: 	color = Color.GREEN; 	break;
			case WARNING:	color = Color.YELLOW;	break;
			default:		color = Color.BLUE;
		}
		colorPane.setBackground(new Background(new BackgroundFill(color, new CornerRadii(2), new Insets(0))));
		
		StackPane title = new StackPane(new Text(evt.getMessage()));
		title.prefWidth(Long.MAX_VALUE);
		//title.setTextAlignment(TextAlignment.CENTER);
		HBox.setHgrow(title, Priority.ALWAYS);
		
		BorderPane notificationContentNode = new BorderPane();
		notificationContentNode.setTop(new HBox(closeNotification,title));
		notificationContentNode.setCenter(evt.getNode());
		HBox.setHgrow(notificationContentNode, Priority.ALWAYS);
		
		notificationNode.getChildren().addAll(notificationContentNode,colorPane);
		
		return notificationNode;
	}
	
	protected @Override void updateItem(NotificationEvent item, boolean empty) {	
		super.updateItem(item, empty);
		setText(null);
		if(empty) {			
			setGraphic(null);
			setBackground(Background.EMPTY);
		}else {
			setGraphic(createView(item));
		}
	}
}
