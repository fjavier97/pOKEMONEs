package com.pokemon.pokemones;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

public class ActionTreeItem<T> extends TreeItem<T> {

	private EventHandler<ActionEvent> handler;
	private Node graphic;
	
	public ActionTreeItem(final EventHandler<ActionEvent> handler){
		super();
		this.setHandler(handler);
	}
	
	public ActionTreeItem(final T arg, final EventHandler<ActionEvent> handler){
		super(arg);
		this.setHandler(handler);
	}
	
	public ActionTreeItem(final T arg1, final Node arg2, final EventHandler<ActionEvent> handler){
		super(arg1, arg2);
		this.setHandler(handler);
	}

	public EventHandler<ActionEvent> getHandler() {
		return handler;
	}

	public void setHandler(EventHandler<ActionEvent> handler) {
		this.handler = handler;
	}
	
	
}
