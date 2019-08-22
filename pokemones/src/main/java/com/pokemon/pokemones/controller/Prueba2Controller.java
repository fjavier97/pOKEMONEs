package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.controller.AbstractController;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Component("Prueba2")
@Scope("ComponentScope")
public class Prueba2Controller extends AbstractController {

	public Prueba2Controller() {
		super();
		// TODO Auto-generated constructor stub
	}


	private @FXML void prueba2() {
		Map<String,Object> params = new HashMap<>();
		params.put("param1","hola");
		publisher.publishEvent(new ComponenteChangeRequestEvent("Prueba1", Navigation.FORWARD, params));
		
	}
	

	@Override
	public void injectArguments(Map<String, Object> args) {
		// TODO Auto-generated method stub
		
	}

}
