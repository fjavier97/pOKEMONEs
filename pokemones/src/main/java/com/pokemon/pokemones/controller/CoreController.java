package com.pokemon.pokemones.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import com.pokemon.pokemones.*;

@Component
public class CoreController implements Initializable {

	@FXML private VBox navigation_menu;
	@FXML private SplitPane splitpane;
	@FXML private TreeView<String> tree;
	
	@FXML private StackPane content;
	
	@Autowired
	public CoreController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		this.tree.setCellFactory(e ->new MenuTreeCell<String>());
		
		TreeItem<String> root = new TreeItem<>();
		TreeItem<String> i1 = new ActionTreeItem<>("hola",e->{System.out.println("hola1");});
		TreeItem<String> i2 = new ActionTreeItem<>("hola",e->{System.out.println("hola2");});
		TreeItem<String> i8 = new ActionTreeItem<>("hola",e->{System.out.println("hola8");});
		TreeItem<String> i3 = new ActionTreeItem<>("hola",e->{System.out.println("hola3");});
		TreeItem<String> i4 = new ActionTreeItem<>("hola",e->{System.out.println("hola4");});
		TreeItem<String> i5 = new ActionTreeItem<>("hola",e->{System.out.println("hola5");});
		TreeItem<String> i6 = new ActionTreeItem<>("hola",e->{System.out.println("hola6");});
		TreeItem<String> i7 = new ActionTreeItem<>("hola",e->{System.out.println("hola7");});
		
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
