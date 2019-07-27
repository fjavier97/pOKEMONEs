package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Component("Prueba1")
@Scope("ComponentScope")
public class Prueba1Controller implements Initializable {

	private @FXML void prueba1() {
		System.out.println("hola1");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
