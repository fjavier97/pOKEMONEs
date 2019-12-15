package customfx.scene.control;

import customfx.scene.control.NotificationArea.NotificationInfo;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class DefaultNotificationCellFactory implements Callback<NotificationInfo,GridPane>{
	
	public GridPane call(final NotificationInfo info){
		final GridPane cell = new GridPane();
		cell.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		cell.setMinHeight(70);
		
		final Pane colorarea = new Pane();
		colorarea.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		colorarea.setPrefWidth(35);
		GridPane.setVgrow(colorarea,Priority.ALWAYS);
		cell.add(colorarea,2,0,1,2);
		
		final Text text = new Text(info.getNotification().getMessage());
		text.setTextAlignment(TextAlignment.CENTER);
		ColumnConstraints cc = new ColumnConstraints();
		cc.setHalignment(HPos.CENTER);
		cell.getColumnConstraints().addAll(new ColumnConstraints(),cc);
		GridPane.setHgrow(text,Priority.SOMETIMES);
		GridPane.setVgrow(text,Priority.ALWAYS);
		cell.add(text,1,0,1,1);
		
		final Button btnc = new Button("X");
		btnc.setPrefSize(35,35);
		btnc.setOnAction((e) -> info.getNotificationArea().removeNotification(info.getNotification()));
		btnc.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
		GridPane.setHgrow(btnc,Priority.NEVER);
		cell.add(btnc,0,0,1,1);
		
//		if(n.getDescription!=null){
//			final TitlePane desc = new TitlePane();
//			final TextArea ta = new Textarea();
//			ta.setEditable(false);
//			if(info.getNotifiation().getDescription()instanceof String){
//				ta.setText(info.getNotifiation().getDescription());
//			}
//			else if(info.getNotifiation().getDescription()instanceof Throwable){				
//				final StringWriter w =new StringWriter();
//				((Throwable)info.getNotifiation().getDescription()).printStackTrace(new PrintWriter(w));
//				ta.setText(w.toString());
//			}
//			else{
//				ta.setText(info.getNotifiation().getDescription().toString());
//			}
//			desc.setContetn(ta);
//			desc.setCollapsible(true);
//			cell.add(desc,1,1,2,1);
//		}	
		
		return cell;
		
	}
}
