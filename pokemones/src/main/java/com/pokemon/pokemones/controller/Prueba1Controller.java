package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.controller.AbstractController;

import javafx.fxml.FXML;

@Component("Prueba1")
@Scope("ComponentScope")
public class Prueba1Controller extends AbstractController {

	public Prueba1Controller() {
		super();
	}

	private @FXML void prueba1() {
		System.out.println("hola1");
	}
	
	@Override
	public void injectArguments(Map<String, Object> args) {
		System.out.println(args.get("param1"));
		
	}

}
