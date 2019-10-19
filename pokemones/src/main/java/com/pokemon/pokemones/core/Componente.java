package com.pokemon.pokemones.core;

import java.util.List;

import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Componente {
	
	private List<Menu> menus;
	private Pane content;
	
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
	
	public Pane getContent() {
		return content;
	}

	public void setContent(Pane content) {
		this.content = content;
	}

}
