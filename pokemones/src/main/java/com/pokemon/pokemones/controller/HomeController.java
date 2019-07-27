package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.Initializable;



@Component("Home")
@Scope("ComponentScope")
public class HomeController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
	}

}
