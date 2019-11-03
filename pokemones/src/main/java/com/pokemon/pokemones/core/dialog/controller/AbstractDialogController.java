package com.pokemon.pokemones.core.dialog.controller;

import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import javafx.util.Callback;

public abstract class AbstractDialogController<E> implements Consumer<E>, Callback<ButtonType,E>{
		
	private Window owner;

	protected Window getOwner() {
		return owner;
	}

	public void setOwner(Window owner) {
		this.owner = owner;
	}
}
