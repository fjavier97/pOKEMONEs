package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.Componente;
import com.pokemon.pokemones.ComponenteChangeCommitEvent;
import com.pokemon.pokemones.ComponenteChangeRequestEvent;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import customfx.scene.control.ActionTreeItem;
import customfx.scene.control.TreeMenu;

@Component
public class CoreController implements Initializable {

	private final ApplicationEventPublisher publisher;
	
	private @FXML MenuButton hola;
	
	private @FXML ToolBar menus;
	
	private @FXML BorderPane root;
	private boolean navigation_menu_hidden=false;
	
	private @FXML HBox navigation_menu;
	private Button show_navigation_menu;
	
	@FXML private TreeMenu<String> tree;
	
	@FXML private StackPane content;
	
	
	public @Autowired CoreController(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
	
	public void toggleHide() {
		if(navigation_menu_hidden) {
			root.setLeft(navigation_menu);
		}else {
			root.setLeft(show_navigation_menu);
		}
		navigation_menu_hidden = !navigation_menu_hidden;
		
		
	}
	
	public void setContentComponent(final Componente component) {
		System.out.println(component);
		boolean vacio = false;
		BorderPane anterior = null;
		try {
			anterior = (BorderPane)this.content.getChildren().get(0);
		}catch(IndexOutOfBoundsException ex){
			vacio = true;
		}
		this.content.getChildren().add(component.getContent());
		component.getContent().toFront();
		if(!vacio) {
			this.content.getChildren().remove(anterior);
		}
		System.out.println("componente cambiado");
	}
	
	private void setMenus(final Componente component) {
		this.menus.getItems().clear();
		if(component.hasMenu()) {			
			this.menus.getItems().addAll(component.getMenus());
		}
	}

	
	private @EventListener void onComponentChangeCommitEvent(final ComponenteChangeCommitEvent evt) {
		setContentComponent(evt.getComponente());
		setMenus(evt.getComponente());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		show_navigation_menu = new Button("->");
		show_navigation_menu.setPrefHeight(1000000000);
		show_navigation_menu.setOnAction(e->toggleHide());
		
		TreeItem<String> root = new ActionTreeItem<>();
		TreeItem<String> p = new ActionTreeItem<>("pruebas");
		TreeItem<String> p1 = new ActionTreeItem<>(
				"prueba1",
				e->{publisher.publishEvent(new ComponenteChangeRequestEvent("Prueba1"));});
		TreeItem<String> p2 = new ActionTreeItem<>(
				"prueba2",
				e->{publisher.publishEvent(new ComponenteChangeRequestEvent("Prueba2"));});
		root.getChildren().add(p);
		p.getChildren().addAll(p1,p2);
		
		tree.setShowRoot(false);
		tree.setRoot(root);
		
		hola.setTextFill(Color.WHITE);
		
	}

}
