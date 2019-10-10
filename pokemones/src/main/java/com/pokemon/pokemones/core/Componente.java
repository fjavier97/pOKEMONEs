package com.pokemon.pokemones.core;

import java.util.List;

import customfx.scene.control.MenuDefinition;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

public class Componente {
	
	private List<Menu> menus;
	private BorderPane content;
	
	public Componente() {
		this.content=null;
		this.menus = null;
	}
	
	public Componente(BorderPane content) {
		this.content=content;
		this.menus = null;
	}
	
	public Componente(BorderPane content, List<Menu> menus) {
		this.content=content;
		this.menus = menus;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
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