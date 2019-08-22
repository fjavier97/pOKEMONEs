package com.pokemon.pokemones.core.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.pokemon.pokemones.core.ComponentManager;

import javafx.fxml.Initializable;

public abstract class AbstractController {
	
	/* referencia al component manager */
	protected ComponentManager manager;
	
	public @Autowired void setManager(final ComponentManager manager) {
		this.manager = manager;
	}
	
	public @Autowired void setPublisher(final ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	/* publicador de eventos */
	protected ApplicationEventPublisher publisher;
	
	/* log */
	protected final Logger LOG;
	
	public @Autowired AbstractController() {
		LOG = LoggerFactory.getLogger(AbstractController.class);
	}

	public abstract void injectArguments(final Map<String, Object> args);

}
