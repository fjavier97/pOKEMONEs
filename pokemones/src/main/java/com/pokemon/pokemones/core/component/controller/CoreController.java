package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.View;
import com.pokemon.pokemones.core.event.ComponenteChangeCommitEvent;
import com.pokemon.pokemones.core.event.ComponentChangeRequestEvent;
import com.pokemon.pokemones.core.event.LoginNotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.services.AspectPropertyService;
import com.pokemon.pokemones.core.services.LoginService;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import customfx.scene.control.ConfigurableMenuBar;
import customfx.scene.control.NotificationArea;
import customfx.scene.control.TreeMenu;

@Component("Core")
//@Scope("ComponentScope")
public class CoreController extends AbstractController{
		
	private Object lock;
	
	private ChangeListener<? super Toggle> languajeChangeListener = (o,a,n)->{
				if(n!=null) {
					super.localizationService.changeLanguaje(((RadioMenuItem)n).getId());
				}};
	
	private @FXML NotificationArea notificationarea;
			
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
	
	private @FXML GridPane login;	
	private @FXML BorderPane ui;
	private @FXML HBox notificaciones;
	
	private final LoginService loginService;
	
	private AspectPropertyService aspectPropertyService;
	
	public @Autowired CoreController(final LoginService loginService) {
		super();
		this.loginService = loginService;
		LOG.info("CORE CONTROLLER INITIALIZED");
	}
	
	public @Autowired void setAspectPropertyService(final AspectPropertyService aspectPropertyService){
		this.aspectPropertyService = aspectPropertyService;
	}
	
	private @FXML ToggleGroup toggle_idioma;
	
	/****
	 * metodos para ocultar el menu
	 * **/
	
	private boolean navigation_menu_hidden=false;
	private @FXML StackPane root;
	
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
	
	public synchronized boolean requestComponentChangeTransaction(final Object o){
		/* comprobar si se puede */
		if(isAnimationRunning()){
			return false;
		}
		
		/* get lock if available */
		if(lock==null){
			lock = o;
			return true;
		}else{
			return false;
		}
	}
		
	public synchronized void freeComponentChangeTransaction(final Object o){
		/* release lock if given object had it */
		if(lock==o) lock = null;
	}
	
	public synchronized void changeContentComponent(View view, Navigation n, Object o) throws Exception{
		if(lock != o){// compruebo que tengo el lock antes de hacer nada
			throw new Exception("Object needs to have lock in order to perform change");
		}
		
		final boolean animate = aspectPropertyService.getAnimationEnabled();
		
		/* añado las nuevas opciones a la barra de menus */
 		this.menus.setMenus(view.getMenus());
 		
 		/* si no hay animacion */
 		/* TODO si no habia nada, simplemente no hacer sus animaciones */
 		if(!animate /*|| this.content.getChildren().size()==0*/) {
 			this.content.getChildren().clear();
			this.content.getChildren().add(view.getContent());
			freeComponentChangeTransaction(o);//libero el lock 
			return;
		}
		else {		
			final Pane anterior = this.content.getChildren().isEmpty()?null:(Pane)this.content.getChildren().get(0);
			
			final Pane nuevo = view.getContent();
			
			KeyValue keyvalue = null;
			
			switch(n) {
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
			
			final KeyFrame keyFrame = new KeyFrame(Duration.millis(aspectPropertyService.getAnimationLenght()), keyvalue);
			
			final Timeline animation = new Timeline(keyFrame);
			
			animation.setOnFinished(e->{
				if(anterior!=null)
					content.getChildren().remove(anterior);
				System.out.println("fin animacion"+Thread.currentThread().getId());
				Platform.runLater(()->isplaying=false); 
				freeComponentChangeTransaction(o);//libero el lock 
			});
			
			isplaying=true;
			animation.play();
			System.out.println("inicio animacion"+Thread.currentThread().getId());
			
		}
		
		
	}
	
	public void publishNotification(final NotificationEvent evt) {
		notificationarea.addNotification(evt);		
	}

	/****
	 * metodos llamados durante el ciclo de vida del controlador
	 * **/
 	
 	private @FXML Label loggedUser;

 	public void setLoginText(final String usr) {
 		loggedUser.setText(usr); 
 		System.out.println("user="+usr);
 	}
 	
 	private @FXML void requestLogin() {
 		loginService.logout();
 	}
 	
	@Override
	public void handleParams(final Map<String, Object> args) {
		
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
		
		tree.addEntry("/pruebas", "prueba1", e->publisher.publishEvent(new ComponentChangeRequestEvent("Prueba1",Navigation.FORWARD)));
		tree.addEntry("/pruebas", "prueba2", e->publisher.publishEvent(new ComponentChangeRequestEvent("Prueba2",Navigation.BACKWARD)));
		tree.addEntry("/", "pokemon", e->publisher.publishEvent(new ComponentChangeRequestEvent("PokemonList",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "running jobs", e->publisher.publishEvent(new ComponentChangeRequestEvent("JobList",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "job definitions", e->publisher.publishEvent(new ComponentChangeRequestEvent("JobClassList",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "job history", e->publisher.publishEvent(new ComponentChangeRequestEvent("JobHistory",Navigation.LINK)));
		tree.addEntry("/sistema/procesos", "scheduled jobs", e->{});
		tree.addEntry("/sistema", "usuarios", e->publisher.publishEvent(new ComponentChangeRequestEvent("Users",Navigation.LINK)));
		tree.addEntry("/sistema", "athorities", e->publisher.publishEvent(new ComponentChangeRequestEvent("Authorities",Navigation.LINK)));

	}

	public @Override void refreshData() {	
	}

}
