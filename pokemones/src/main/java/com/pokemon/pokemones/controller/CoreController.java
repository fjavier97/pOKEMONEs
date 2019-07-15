package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.StackPane;

@Component
public class CoreController implements Initializable {

	@FXML private ToolBar menus;
	
	@FXML private StackPane content;
	
	@Autowired
	public CoreController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Button btn = new Button("prueba");
		btn.setOnAction(System.out::println);
		menus.getItems().add(btn);
		
	}

}
