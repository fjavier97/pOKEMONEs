package com.pokemon.pokemones.core.dialog.controller;

import java.io.Reader;

import javafx.scene.control.ButtonType;

public abstract class ImportDialogController extends AbstractDialogController<Reader>{
	
	protected abstract Reader openStream();
	
	public @Override Reader call(final ButtonType btntype){
		return btntype.equals(ButtonType.OK)?openStream():null;
	}
	
	//public abstract @Override accept(final Reader reader);	
}
