package com.pokemon.pokemones;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@Component
public class ComponentManager {

	private final Logger LOG;
	
	private Stage stage;
	private String actualComponent;
//	private final Myscope myscope
	private final ComponentLoader loader;	
	
	
	public @Autowired ComponentManager(ComponentLoader loader) {
		super();
		this.LOG=LoggerFactory.getLogger(ComponentManager.class);
		this.loader=loader;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(final Stage stage) {
		this.stage = stage;
	}
	
	public void loadCoreComponent() throws ComponentLoadException, IOException {
		final Componente core_component = loader.load("Core");
		final Scene scene = new Scene(core_component.getContent());
		getStage().setScene(scene);
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
		if(evt.getNewComponent().equals(actualComponent)) {
			LOG.info("componente solicitado ya esta cargado actualmente");
			return null;
		}
		// Cargo el componente
		LOG.info("cargando componente");
		try{
			final Componente component = (Componente)loader.load(evt.getNewComponent());
			return new ComponenteChangeCommitEvent(component);
		}catch (Exception e) {
			LOG.error("no se ha podido cargar la clase "+evt.getNewComponent(),e);
			return null;
		}
	}
	
	
	
}
