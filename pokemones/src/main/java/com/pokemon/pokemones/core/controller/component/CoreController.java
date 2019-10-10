package com.pokemon.pokemones.core.controller.component;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.event.ComponenteChangeCommitEvent;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import customfx.scene.control.ConfigurableMenuBar;
import customfx.scene.control.TreeMenu;

@Component("Core")
@Scope("ComponentScope")
public class CoreController extends AbstractController {
		
	private ChangeListener<? super Toggle> languajeChangeListener = (o,a,n)->{
				if(n!=null) {
					super.localization_service.changeLanguaje(((RadioMenuItem)n).getId());
				}};
	
	private @FXML ListView<Node> notificationarea;
			
	/* referencias a la vista */
	private @FXML ConfigurableMenuBar menus;	
	
	private @FXML HBox navigation_menu;
	private @FXML Button show_navigation_menu;
	
	private @FXML TreeMenu tree;
	
	private @FXML StackPane content;
	
	private @FXML StackPane menu_container;
	
	private @FXML SplitPane sp;
	
	private boolean autocolapsed;
	
	public ConfigurableMenuBar getMenus() {
		return menus;
	}
	
	public @Autowired CoreController() {
		super();
	}
	
	private @FXML ToggleGroup toggle_idioma;
	
	/****
	 * metodos para ocultar el menu
	 * **/
	
	private boolean navigation_menu_hidden=false;
	private @FXML BorderPane root;
	
	public void toggleHide() {
		menu_container.getChildren().clear();
		if(navigation_menu_hidden) {
			menu_container.getChildren().add(navigation_menu);
			menu_container.setMaxWidth(-1);
			Platform.runLater(()->sp.setDividerPositions(navigation_menu.getWidth() / sp.getScene().getWidth()));
		}else {
			menu_container.getChildren().add(show_navigation_menu);
			menu_container.setMaxWidth(show_navigation_menu.getWidth());
			Platform.runLater(()->sp.setDividerPositions(0));
		}
		navigation_menu_hidden = !navigation_menu_hidden;
		Platform.runLater(()->sp.setDividerPositions(0));		
		
		
	}
		
	/****
	 * metodos para cambiar el componente
	 * **/
	
	private boolean isplaying;
	
	/**
	 * metodo que sirve para que el manager pregunte si la animacion esta activa
	 * @return
	 */
	public boolean isAnimationRunning() {
		return isplaying;
	}
	
	public @EventListener void onNotificationEvent(final NotificationEvent evt) {
		BorderPane not = new BorderPane();
		HBox hbox = new HBox();
		
		Label label = new Label(evt.getMessage());
		Button btn = new Button("X");
		btn.setOnAction(e->notificationarea.getItems().remove(not));
		hbox.getChildren().addAll(label,btn);
		hbox.setMaxWidth(Double.MAX_VALUE);
		label.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(label, Priority.ALWAYS);

		not.setTop(hbox);
		label.setAlignment(Pos.CENTER);

		if(evt.getNode()!=null) {
			evt.getNode().setMaxWidth(Double.MAX_VALUE);
			not.setCenter(evt.getNode());
		}
		
		Color color;
		switch (evt.getThreat()) {
			case ERROR:
				color = Color.RED;
				break;	
			case SUCCESS:
				color=Color.GREEN;
				break;
			case WARNING:
				color= Color.YELLOW;
				break;
			default:
				color= Color.BLUE;
				break;
		}
		not.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, new Insets(0))));
		
		notificationarea.getItems().add(not);
	}
	
 	public void onComponentChangeCommitEvent(final ComponenteChangeCommitEvent evt, final boolean animate) {
 		/* añado las nuevas opciones a la barra de menus */
 		this.menus.setMenus(evt.getComponente().getMenus());
 		
 		/* si no hay animacion */
 		/* TODO si no habia nada, simplemente no hacer sus animaciones */
 		if(!animate /*|| this.content.getChildren().size()==0*/) {
 			this.content.getChildren().clear();
			this.content.getChildren().add(evt.getComponente().getContent());
			return;
		}
		else {		
			final BorderPane anterior = this.content.getChildren().isEmpty()?null:(BorderPane)this.content.getChildren().get(0);
			
			final BorderPane nuevo = (BorderPane)evt.getComponente().getContent();
			
			KeyValue keyvalue = null;
			
			switch(evt.getNavigation()) {
				case FORWARD:
	
					nuevo.setTranslateX(-content.getWidth());
					
					content.getChildren().add(1, nuevo);
					
					keyvalue = new KeyValue(nuevo.translateXProperty(),0);
	
					break;
					
				case BACKWARD:
					
					content.getChildren().add(0, nuevo);
					
					if(anterior!=null) 
						keyvalue = new KeyValue(anterior.translateXProperty(),-content.getWidth());
	
					break;
					
				default:
						
					nuevo.setTranslateY(-content.getHeight());
					
					content.getChildren().add(anterior==null?0:1, nuevo);
					
					keyvalue = new KeyValue(nuevo.translateYProperty(),0, Interpolator.EASE_IN);
	
			}
			
			final KeyFrame keyFrame = new KeyFrame(Duration.millis(100), keyvalue);
			
			final Timeline animation = new Timeline(keyFrame);
			
			animation.setOnFinished(e->{
				if(anterior!=null)
					content.getChildren().remove(anterior);
				System.out.println("fin animacion"+Thread.currentThread().getId());
				Platform.runLater(()->isplaying=false); 
			});
			
			isplaying=true;
			animation.play();
			System.out.println("inicio animacion"+Thread.currentThread().getId());
			
		}
	}
	
	/****
	 * metodos llamados durante el ciclo de vida del controlador
	 * **/
	

	@Override
	public void handleParams(Map<String, Object> args) {
		
		/* no se puede comprobar si ya esta, asi que por si acaso lo quito antes y lo vuelvo a registrar */
		toggle_idioma.selectedToggleProperty().removeListener(this.languajeChangeListener);
		toggle_idioma.selectedToggleProperty().addListener(this.languajeChangeListener);
		
		sp.widthProperty().addListener((ob, o, n) -> {
			if (o.doubleValue() > n.doubleValue())// si pasa a estar pequeña
			{
				if ((menu_container.getWidth() + 200) > n.doubleValue())
				{
					menu_container.getChildren().clear();
					menu_container.getChildren().add(show_navigation_menu);
					menu_container.setMaxWidth(show_navigation_menu.getWidth());					
					navigation_menu_hidden = true;
					Platform.runLater(()->sp.setDividerPositions(0));	
					
					autocolapsed = true;				
				}
			}
			else
			{
				if ((menu_container.getWidth() + 200) < n.doubleValue())
				{
					if (autocolapsed)
					{
						menu_container.getChildren().clear();
						menu_container.getChildren().add(navigation_menu);
						menu_container.setMaxWidth(-1);
						navigation_menu_hidden = false;
						Platform.runLater(()->sp.setDividerPositions(0));	
						autocolapsed = false;
					}
				}
			}
		});
		
		menu_container.getChildren().add(navigation_menu);
		
		tree.addEntry("/pruebas", "prueba1", e->publisher.publishEvent(new ComponenteChangeRequestEvent("Prueba1",Navigation.FORWARD)));
		tree.addEntry("/pruebas", "prueba2", e->publisher.publishEvent(new ComponenteChangeRequestEvent("Prueba2",Navigation.BACKWARD)));
		tree.addEntry("/pruebas", "pokemon", e->publisher.publishEvent(new ComponenteChangeRequestEvent("PokemonList",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "running jobs", e->publisher.publishEvent(new ComponenteChangeRequestEvent("JobList",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "job definitions", e->publisher.publishEvent(new ComponenteChangeRequestEvent("JobClassList",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "job history", e->publisher.publishEvent(new ComponenteChangeRequestEvent("JobHistory",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "scheduled jobs", e->{});
		publisher.publishEvent(new NotificationEvent("aplicacion iniciada",Threat.INFO));
		
		
	}

	public @Override void refreshData() {	
	}

}
