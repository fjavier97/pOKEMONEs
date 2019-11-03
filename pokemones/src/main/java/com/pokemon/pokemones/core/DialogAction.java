package com.pokemon.pokemones.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.dialog.controller.AbstractDialogController;

import javafx.scene.control.Dialog;
import javafx.stage.Window;

@Component
@Scope("prototype")
public class DialogAction<E>{
	
	private final Dialog<E> dialog;
	private final AbstractDialogController<E> controller;
	
	public @Autowired DialogAction(final ComponentLoader loader, final ComponentManager manager, final String nombre){
		dialog = new Dialog<E>();
		
		final Window owner = manager.getStage();
		
		dialog.initOwner(owner);
				
		controller = (AbstractDialogController<E>) loader.loadDialog(nombre, dialog.getDialogPane());
		
		dialog.setResultConverter(controller);
	}

	/*getters*/
	public final Dialog<E> getDialog(){
		return this.dialog;
	}
	
	public AbstractDialogController<E> getController(){
		return this.controller;
	}
}