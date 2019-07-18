package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import customfx.scene.control.ActionTreeItem;
import customfx.scene.control.TreeMenu;

@Component
public class CoreController implements Initializable {

	private @FXML StackPane navigation_menu_container;
	private boolean navigation_menu_hidden=false;
	
	private @FXML VBox navigation_menu;
	private Button show_navigation_menu;
	
	@FXML private SplitPane splitpane;
	@FXML private TreeMenu<String> tree;
	
	@FXML private StackPane content;
	
	@Autowired
	public CoreController() {
		// TODO Auto-generated constructor stub
	}
	
	public void toggleHide() {
		navigation_menu_container.getChildren().clear();
		if(navigation_menu_hidden) {
			navigation_menu_container.setMaxWidth(-1.0);
			navigation_menu_container.setMinWidth(-1.0);
			navigation_menu_container.setPrefWidth(500.0);
			navigation_menu_container.getChildren().add(navigation_menu);
		}else {
			navigation_menu_container.setMaxWidth(35.0);
			navigation_menu_container.setMinWidth(35.0);
			navigation_menu_container.getChildren().add(show_navigation_menu);
		}
		navigation_menu_hidden = !navigation_menu_hidden;
		
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		show_navigation_menu = new Button("->");
		show_navigation_menu.setOnAction(e->toggleHide());
			
		TreeItem<String> root = new TreeItem<>();
		TreeItem<String> i1 = new ActionTreeItem<>("menu1",e->{System.out.println("hola1");});
		TreeItem<String> i2 = new ActionTreeItem<>("menu2 (sin accion)");
		TreeItem<String> i8 = new ActionTreeItem<>("submenu8 (sin accion)");
		TreeItem<String> i3 = new ActionTreeItem<>("menu3",e->{System.out.println("hola3");});
		TreeItem<String> i4 = new ActionTreeItem<>("submenu4",e->{System.out.println("hola4");});
		TreeItem<String> i5 = new ActionTreeItem<>("submenu5",e->{System.out.println("hola5");});
		TreeItem<String> i6 = new ActionTreeItem<>("submenu6",e->{System.out.println("hola6");});
		TreeItem<String> i7 = new ActionTreeItem<>("submenu7",e->{System.out.println("hola7");});
		
		root.getChildren().addAll(i1,i2,i3);
		i1.getChildren().addAll(i4,i5);
		i2.getChildren().addAll(i7);
		i3.getChildren().addAll(i8);
		i7.getChildren().addAll(i6);
		
		tree.setShowRoot(false);
		tree.setRoot(root);
		
		splitpane.setDividerPositions(0.2);
		
	}

}
