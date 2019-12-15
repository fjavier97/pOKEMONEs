package customfx.scene.control;

import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout.Alignment;

import com.pokemon.pokemones.core.event.NotificationEvent;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;

public class NotificationArea extends BorderPane{
	
	public class NotificationInfo {
		private final NotificationArea notificationArea;
		private final NotificationEvent notification;
		
		public NotificationInfo(final NotificationEvent notification, final NotificationArea notificationArea){
			this.notification = notification;
			this.notificationArea = notificationArea;
		}
		
		public NotificationArea getNotificationArea(){
			return notificationArea;
		}
		
		public NotificationEvent getNotification(){
			return notification;
		}
	}
	
	private final long space = 13;
	
	private final VBox notificationArea;
	
	private final ScrollBar scrollBar;
	
	private final ObjectProperty<Callback<NotificationInfo,? extends Pane>> cellFactory;
	private final Map<NotificationEvent, Pane> notificationViewCache;
	
	private final ObjectProperty<ObservableList<NotificationEvent>> eventListProperty;
		
	public NotificationArea() {
		super();
		
		notificationArea = new VBox();
		scrollBar = new ScrollBar();
		scrollBar.setOrientation(Orientation.VERTICAL);
		scrollBar.setPrefWidth(30);
		scrollBar.setMin(0);
		
		notificationArea.setPrefWidth(500);
		notificationArea.setSpacing(space);
		notificationArea.setPadding(new Insets(space));
		notificationArea.setAlignment(Pos.BOTTOM_CENTER);
		notificationArea.pickOnBoundsProperty().bind(this.pickOnBoundsProperty());
		
		setCenter(notificationArea);
		setLeft(scrollBar);
		
		this.eventListProperty = new SimpleObjectProperty<ObservableList<NotificationEvent>>();
		this.notificationViewCache = new HashMap<NotificationEvent, Pane>();
		cellFactory = new SimpleObjectProperty<>(new DefaultNotificationCellFactory());
		
		final ListChangeListener<NotificationEvent> cl = c->{
			while(c.next()) {
				for(final NotificationEvent e : c.getRemoved()) {
					notificationArea.getChildren().remove(notificationViewCache.remove(e));
				}
				for(final NotificationEvent e : eventListProperty.get()) {
					if(!notificationViewCache.containsKey(e)) {
						notificationViewCache.put(e, createCell(e));
					}
				}
				notificationArea.getChildren().setAll(notificationViewCache.values());
				calculateSizes();
			}
			
		};		
		
		eventListProperty.addListener((obs, ov, nv)->{
			if(ov!=null) {
				ov.removeListener(cl);
			}
			nv.addListener(cl);
		});
		eventListProperty.set(FXCollections.observableArrayList());
		
	}
		
	private Pane createCell(NotificationEvent n){
		final NotificationInfo info = new NotificationInfo(n,this);
		return cellFactory.get().call(info);
	}
		
	public void addNotification(final NotificationEvent e) {
//		Timeline t = new Timeline(new KeyFrame(Duration.seconds(5), x -> {
//			if(this.eventListProperty.get().contains(e)){
//				this.eventListProperty.get().remove(e);
//			}
//		}));
		this.eventListProperty.get().add(e);
//		t.play();
	}
	
	public void removeNotification(final NotificationEvent e) {
		this.eventListProperty.get().remove(e);
	}


	private void calculateSizes(){
		//calcular el tamaño de las notificaciones con respecto al espacio
		final double contentSize=eventListProperty.get().size() * 70 + ((eventListProperty.get().size()+1)*space);
		final double areaSize=getHeight();
		System.out.println(getScene().getHeight());
		
		System.err.println(getHeight()+"-"+scrollBar.getHeight());
		System.err.println(contentSize+" - "+areaSize+"|"+scrollBar.getHeight());
		
		Bindings.unbindBidirectional(notificationArea.translateYProperty(),scrollBar.valueProperty());/*quitar, luego se pone*/
		
		//si es mas grande, añadir la ScrollBar
		if(contentSize>areaSize){
			
			scrollBar.setMax(1);
//			scrollBar.setValue(scrollBar.getMax());
//			scrollBar.setUnitIncrement(100.0);
			/*minimo siempre es 0*/
			
			Bindings.bindBidirectional(notificationArea.translateYProperty(),scrollBar.valueProperty());	
			scrollBar.setVisible(true);			
		}else{
			scrollBar.setVisible(false);
			notificationArea.setLayoutY(0);
			
		}			
		
	}
	
}
