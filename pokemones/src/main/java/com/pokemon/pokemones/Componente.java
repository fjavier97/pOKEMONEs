package com.pokemon.pokemones;

import java.util.LinkedList;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class Componente {
	
	private LinkedList<Button> menus;
	private BorderPane content;
	
	public Componente() {
		this.content=null;
		this.menus = null;
	}
	
	public Componente(BorderPane content) {
		this.content=content;
		this.menus = null;
	}
	
	public Componente(BorderPane content, LinkedList<Button> menus) {
		this.content=content;
		this.menus = menus;
	}

	public LinkedList<Button> getMenus() {
		return menus;
	}

	public void setMenus(LinkedList<Button> menus) {
		this.menus = menus;
	}
	
	public boolean hasMenu() {
		return this.menus!=null;
	}
	
	public BorderPane getContent() {
		return content;
	}

	public void setContent(BorderPane content) {
		this.content = content;
	}

}
