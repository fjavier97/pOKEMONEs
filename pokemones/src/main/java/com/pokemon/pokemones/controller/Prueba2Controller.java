package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.AbstractController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Component("Prueba2")
@Scope("ComponentScope")
public class Prueba2Controller extends AbstractController {

	private @FXML void prueba2() {
		System.out.println("hola2");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public void injectArguments(Map<String, Object> args) {
		// TODO Auto-generated method stub
		
	}

}
