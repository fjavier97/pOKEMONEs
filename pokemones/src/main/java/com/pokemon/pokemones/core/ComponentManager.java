package com.pokemon.pokemones.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.component.controller.AbstractController;
import com.pokemon.pokemones.core.component.controller.CoreController;
import com.pokemon.pokemones.core.event.ComponentDataRefreshEvent;
import com.pokemon.pokemones.core.event.ComponentViewRefreshEvent;
import com.pokemon.pokemones.core.event.ComponenteChangeCommitEvent;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.LanguajeChangeEvent;
import com.pokemon.pokemones.core.event.LoginNotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.event.StartEvent;
import com.pokemon.pokemones.core.scopes.ComponentScope;
import com.pokemon.pokemones.core.services.LoginService;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Service
public class ComponentManager {

	private final Logger LOG;
	
	private final ComponentScope scope;
	
	private Stage stage;
	private CoreController coreComponentController;
	private final ComponentLoader loader;	
	
	private String current_component_name;
	private AbstractController<?> currentComponentController;
	
	private final ApplicationEventPublisher publisher;
	
	private final LoginService loginService;
	
	public @Autowired ComponentManager(ComponentLoader loader, ComponentScope scope, ApplicationEventPublisher publisher,
			final LoginService loginService) {
		super();
		this.LOG=LoggerFactory.getLogger(ComponentManager.class);
		this.loader=loader;
		this.loginService = loginService;
		this.scope=scope;
		this.publisher = publisher;
	}
	
	private void changeComponent(final String nombreNuevoComponente, final boolean borrarAnterior) {
		currentComponentController.setActivo(true);
		if(current_component_name!=null && borrarAnterior) {
			scope.remove(current_component_name);
		}
		current_component_name=nombreNuevoComponente;
		
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(final Stage stage) {
		this.stage = stage;
	}
	
	public void loadCoreComponent() throws ComponentLoadException, IOException {
		
		final View core_component = new View();
		this.coreComponentController = (CoreController) loader.load("Core",core_component);
		this.coreComponentController.setActivo(true);
		
		final Scene scene = new Scene(core_component.getContent());
		if(core_component.hasMenu()) {
			coreComponentController.getMenus().addCoreMenus(core_component.getMenus());

		}
		getStage().setScene(scene);
	}
		
	private @EventListener void onLogin(final LoginNotificationEvent evt){
		System.out.println(evt.isLogin()+"-"+evt.getUsr());
		if(evt.isLogin()) {
			coreComponentController.setLoginText(evt.getUsr());
		}else {
			coreComponentController.setLoginText("");
		}
	}
	
	private @EventListener void onStop(ContextClosedEvent evt){
		scope.removeAll();
	}
	
	private @EventListener void onLanguajeChanged(LanguajeChangeEvent evt) {
		currentComponentController.refreshLabels();
	}
	
	private @EventListener ComponenteChangeRequestEvent onStart(StartEvent evt){
		setStage(evt.getStage());
		getStage().setMaximized(true);
		try{
			loadCoreComponent();
		}catch (Exception e) {
			LOG.error("no se ha podido cargar la clase principal",e);
			Platform.exit();
		}
		getStage().show();
		return new ComponenteChangeRequestEvent("Login");
	}
	
	private @EventListener void onComponentChangeRequest(ComponenteChangeRequestEvent evt){
		LOG.info("se solicito el cambio al componente "+evt.getNewComponent());
		
		if(!loginService.isAuthenticated() && !evt.getNewComponent().equals("Login")) {
			LOG.info("no has accedido al sistema");
			return ;
		}
		
		/* compruebo que no este ya cargado */
		if(evt.getNewComponent().equals(current_component_name)) {
			LOG.info("componente solicitado ya esta cargado actualmente");
			return ;
		}

		/* compruebo que el corecontroller no este a mitad de transicion */
		if(coreComponentController!= null && coreComponentController.isAnimationRunning()) {
			LOG.info("espera a acabar la animacion");
			return ;
		}
		// Cargo el componente
		LOG.info("cargando componente solicitado");
		try{
			final boolean borrarAnterior = evt.getNavigation()!=Navigation.FORWARD;
			
			/* construyo y paso el estado*/
			final AbstractController<?> antiguoController = currentComponentController;
			
			final Component newcomp = loader.load(evt.getNewComponent(), evt.getParams());
			
			final View view = new View();
			this.currentComponentController = loader.load(evt.getNewComponent(), view, evt.getParams());
			currentComponentController.setActivo(true);
			if(antiguoController!=null && borrarAnterior) {
				antiguoController.setActivo(false);
			}
			scope.clean();//limpio los beans cacheados que no se necesiten
			
			/* cambio estado */
			changeComponent(evt.getNewComponent(), borrarAnterior);
			
			
			/* mando evento a coreController para que cambie su estado */
			this.coreComponentController.onComponentChangeCommitEvent(new ComponenteChangeCommitEvent(view,evt.getNavigation()),false);
			
			return ;
		}catch (Exception e) {
			LOG.error("no se ha podido cargar la clase "+evt.getNewComponent(),e.getCause());
			e.printStackTrace();
			publisher.publishEvent(new NotificationEvent("no se ha podido cargar la clase "+evt.getNewComponent(), Threat.ERROR));
			return ;
		}
	}
	
	private @EventListener void onComponentDataRefresh(ComponentDataRefreshEvent evt){
		LOG.info("referscando datos");
		currentComponentController.refreshData();
	}
	
	private @EventListener void onComponentViewRefresh(ComponentViewRefreshEvent evt){
		// TODO cargar la vista otra vez
	}
}
