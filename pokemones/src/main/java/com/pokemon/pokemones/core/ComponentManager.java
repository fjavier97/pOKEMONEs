package com.pokemon.pokemones.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.controller.CoreController;
import com.pokemon.pokemones.core.event.ComponenteChangeCommitEvent;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.StartEvent;
import com.pokemon.pokemones.scopes.ComponentScope;

import customfx.scene.control.MenuCretionException;
import customfx.scene.control.MenuDefinition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class ComponentManager {

	private final Logger LOG;
	
	private final ComponentScope scope;
	
	private Stage stage;
	private final CoreController coreComponentController;
	private final ComponentLoader loader;	
	
	private String current_component_name;
	//private AbstractComponent currentComponentController;
	
	
	public @Autowired ComponentManager(ComponentLoader loader, ComponentScope scope, CoreController coreComponentController) {
		super();
		this.LOG=LoggerFactory.getLogger(ComponentManager.class);
		this.loader=loader;
		this.scope=scope;
		this.coreComponentController=coreComponentController;
	}
	
	private void changeComponent(final String nombreNuevoComponente, final boolean borrarAnterior) {
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
		
		final Componente core_component = new Componente();
		loader.load("Core",core_component);
		
		final Scene scene = new Scene(core_component.getContent());
		if(core_component.hasMenu()) {
			for(MenuDefinition md: core_component.getMenus()) {
				try {
					coreComponentController.getMenus().addSystemMenu(md);
				} catch (MenuCretionException e) {
					LOG.error("no se pudo crear el menu ["+md.getText()+"] :"+md.getPath());
				}
			}
		}
		getStage().setScene(scene);
	}
		
	private @EventListener void onStop(ContextClosedEvent evt){
		scope.removeAll();
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
		return new ComponenteChangeRequestEvent("Prueba1");
	}
	
	private @EventListener ComponenteChangeCommitEvent onComponentChangeRequest(ComponenteChangeRequestEvent evt){
		LOG.info("se solicito el cambio al componente "+evt.getNewComponent());
		
		/* compruebo que no este ya cargado */
		if(evt.getNewComponent().equals(current_component_name)) {
			LOG.info("componente solicitado ya esta cargado actualmente");
			return null;
		}

		/* compruebo que el corecontroller no este a mitad de transicion */
		if(coreComponentController!= null && coreComponentController.isAnimationRunning()) {
			LOG.info("espera a acabar la animacion");
			return null;
		}
		// Cargo el componente
		LOG.info("cargando componente solicitado");
		try{
			/* construyo y paso el estado*/
			final Componente component = new Componente();
			loader.load(evt.getNewComponent(), component, evt.getParams());
						
			/* cambio estado */
			changeComponent(evt.getNewComponent(), evt.getNavigation()!=Navigation.FORWARD);
			
			/* mando evento a coreController para que cambie su estado */
			return new ComponenteChangeCommitEvent(component,evt.getNavigation());
		}catch (Exception e) {
			LOG.error("no se ha podido cargar la clase "+evt.getNewComponent(),e);
			return null;//TODO popup
		}
	}
	
	
	
}
