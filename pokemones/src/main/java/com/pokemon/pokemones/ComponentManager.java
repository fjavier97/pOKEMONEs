package com.pokemon.pokemones;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.controller.CoreController;

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
	private StringProperty actualComponent;
	private final CoreController cc;
	private final ComponentLoader loader;	
	
	
	public @Autowired ComponentManager(ComponentLoader loader, ComponentScope scope, CoreController cc) {
		super();
		this.LOG=LoggerFactory.getLogger(ComponentManager.class);
		this.loader=loader;
		this.scope=scope;
		this.cc=cc;
		actualComponent = new SimpleStringProperty();
		actualComponent.addListener((ob,viejo,nuevo)->{
			if(viejo!=null) {
				scope.remove(viejo);
			}
		});
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(final Stage stage) {
		this.stage = stage;
	}
	
	public void loadCoreComponent() throws ComponentLoadException, IOException {
		final Componente core_component = loader.load("Core");
		System.out.println(core_component.getContent());
		final Scene scene = new Scene(core_component.getContent());
		if(core_component.hasMenu()) {
			for(MenuDefinition md: core_component.getMenus()) {
				try {
					cc.getMenus().addSystemMenu(md);
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
		if(evt.getNewComponent().equals(actualComponent.get())) {
			LOG.info("componente solicitado ya esta cargado actualmente");
			return null;
		}
		// Cargo el componente
		LOG.info("cargando componente");
		try{
			final Componente component = (Componente)loader.load(evt.getNewComponent());
			actualComponent.set(evt.getNewComponent());
			return new ComponenteChangeCommitEvent(component);
		}catch (Exception e) {
			LOG.error("no se ha podido cargar la clase "+evt.getNewComponent(),e);
			return null;
		}
	}
	
	
	
}
