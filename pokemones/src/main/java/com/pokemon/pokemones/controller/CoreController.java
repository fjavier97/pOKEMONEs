package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.AbstractController;
import com.pokemon.pokemones.ComponenteChangeCommitEvent;
import com.pokemon.pokemones.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.Navigation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import customfx.scene.control.ConfigurableMenuBar;
import customfx.scene.control.MenuCretionException;
import customfx.scene.control.MenuDefinition;
import customfx.scene.control.TreeMenu;

@Component("Core")
@Scope("ComponentScope")
public class CoreController extends AbstractController {
	
	/* log */
	private final Logger LOG;
	
	/* publicador de eventos */
	private final ApplicationEventPublisher publisher;
		
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
	
	public @Autowired CoreController(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
		LOG = LoggerFactory.getLogger(CoreController.class);
	}
	
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
	
	public void setContentComponent(final ComponenteChangeCommitEvent evt) {
						
		if(this.content.getChildren().size()==0) {
			this.content.getChildren().add(evt.getComponente().getContent());
			return;
		}
	
	
		final BorderPane anterior = (BorderPane)this.content.getChildren().get(0);
		
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
				
				keyvalue = new KeyValue(anterior.translateXProperty(),-content.getWidth());

				break;
				
			default:
					
				nuevo.setTranslateY(-content.getHeight());
				
				content.getChildren().add(1, nuevo);
				
				keyvalue = new KeyValue(nuevo.translateYProperty(),0, Interpolator.EASE_IN);

		}
		
		final KeyFrame keyFrame = new KeyFrame(Duration.millis(350), keyvalue);
		
		final Timeline animation = new Timeline(keyFrame);
		
		animation.setOnFinished(e->{
			content.getChildren().remove(anterior);
			Platform.runLater(()->isplaying=false); 
		});
		
		isplaying=true;
		animation.play();	
		
	}
	
	private void setMenus(final ComponenteChangeCommitEvent evt) {
		this.menus.clean();
		if(evt.getComponente().hasMenu()) {	
			for(MenuDefinition m : evt.getComponente().getMenus()) {
				try {
					this.menus.addMenu(m);
				}catch(MenuCretionException mce) {
					LOG.error("no se pudo registrar el menu ["+m.getText()+"] :"+m.getPath());
				}
			}			
		}
	}
	
	public @EventListener void onComponentChangeCommitEvent(final ComponenteChangeCommitEvent evt) {
		setContentComponent(evt);
		setMenus(evt);
	}
	
	/****
	 * metodos llamados durante el ciclo de vida del controlador
	 * **/
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
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
		
	}

	@Override
	public void injectArguments(Map<String, Object> args) {
		// TODO Auto-generated method stub
		
	}

}
