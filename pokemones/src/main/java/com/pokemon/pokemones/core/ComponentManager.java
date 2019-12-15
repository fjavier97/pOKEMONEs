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
import com.pokemon.pokemones.core.event.ComponentChangeRequestEvent;
import com.pokemon.pokemones.core.event.LanguajeChangeEvent;
import com.pokemon.pokemones.core.event.LoginNotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.StartEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
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
	
	private AbstractController currentComponentController;
	
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
		if(evt.isLogin()) {
			coreComponentController.setLoginText(evt.getUsr());
		}else {
			coreComponentController.setLoginText("");
		}
	}
	
	private @EventListener void onStop(ContextClosedEvent evt){
		scope.removeAll();
	}
		
	private @EventListener ComponentChangeRequestEvent onStart(StartEvent evt){
		setStage(evt.getStage());
		getStage().setMaximized(true);
		try{
			loadCoreComponent();
		}catch (Exception e) {
			LOG.error("no se ha podido cargar la clase principal",e);
			Platform.exit();
		}
		getStage().show();
		publisher.publishEvent(new NotificationEvent("aplicacion iniciada",Threat.INFO));
		return new ComponentChangeRequestEvent("Login");
	}
	
	private final @EventListener void onComponentChangeRequest(final ComponentChangeRequestEvent evt){
		
		LOG.info("componente requerido: "+evt.getNewComponentName());
	
		/* comprobaciones */
		
		// comprobar seguridad
		if(!loginService.isAuthenticated()){
			if(!evt.getNewComponentName().equals("Login")){
				LOG.error("no estas autenticado, login para acceder");
				// ocultar el menu si no se puede usar
				return;
			}
		}else{		
//			if(checkAuthorization()){
//				LOG.error("no estas autorizado");
//				// tambien notificacion
//				return;
//			}
		}
		
		// comprobar que no este ya cargado
		if(currentComponentController!=null && currentComponentController.getComponentName().equals(evt.getNewComponentName())){
			return;
		}
		
		/* comprobaciones terminadas */
		
		// pido permiso al coreContreller para obtener el lock, si lo acepta, el se encarga de liberarlo. si no lo acepta salimos
		if(!coreComponentController.requestComponentChangeTransaction(this/*referencia al objeto que lo esta utilizando*/)){
			LOG.info("core controller is currently busy");
			return;
		}
		
		try{// cargamos el componente y lo insertamos
		
			/* carga */
			final View view = new View();
			final AbstractController newController = loader.load(evt.getNewComponentName(), view, evt.getParams());
			
			/* visualizacion */
			currentComponentController = newController;		
			coreComponentController.changeContentComponent(view, evt.getNavigation(), this);// la animacion que lo decida el coreControler de un PropertyService
			
			/* limpio cache de controladores */
			if(evt.getNavigation() == Navigation.LINK){
				scope.clean();
			}
			
			LOG.debug("componente cambiado");
		
		}catch(Throwable ex){
			// si hay cualqier excepcion o error avisamos al CoreController para que libere el lock
			coreComponentController.freeComponentChangeTransaction(this);
			LOG.error("error", ex);
		}	
	}
	
	private @EventListener void onLanguajeChanged(LanguajeChangeEvent evt) {
		currentComponentController.refreshLabels();
	}
	
	private @EventListener void onComponentDataRefresh(ComponentDataRefreshEvent evt){
		LOG.info("referscando datos");
		currentComponentController.refreshData();
	}
	
	private @EventListener void onComponentViewRefresh(ComponentViewRefreshEvent evt){
		LOG.info("referscando datos");
		currentComponentController.refreshView();
	}
	
	private @EventListener void onNotificationEvent(final NotificationEvent evt) {
		if(coreComponentController!=null)
			coreComponentController.publishNotification(evt);		
	}
}
