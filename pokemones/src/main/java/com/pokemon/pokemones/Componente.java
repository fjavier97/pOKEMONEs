package com.pokemon.pokemones;

import java.util.Map;

import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

public class Componente {
	
	private Map<String,Menu> menus;
	private BorderPane content;
	
	public Componente() {
		this.content=null;
		this.menus = null;
	}
	
	public Componente(BorderPane content) {
		this.content=content;
		this.menus = null;
	}
	
	public Componente(BorderPane content, Map<String,Menu> menus) {
		this.content=content;
		this.menus = menus;
	}

	public Map<String,Menu> getMenus() {
		return menus;
	}

	public void setMenus(Map<String,Menu> menus) {
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
