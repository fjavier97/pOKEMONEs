package com.pokemon.pokemones.controller;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.controller.AbstractController;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;

import javafx.fxml.FXML;

@Component("PokemonEditor")
@Scope("ComponentScope")
public class PokemonEditorController extends AbstractController{

	private @FXML void atras(){
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonList", Navigation.BACKWARD);
		publisher.publishEvent(evt);
	}
	
	@Override
	public void injectArguments(Map<String, Object> args) {
		// TODO Auto-generated method stub
		
	}

}
