package com.pokemon.pokemones.core.services;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pokemon.pokemones.controller.ImportDialogController;
import com.pokemon.pokemones.core.ComponentLoader;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.util.Callback;


public class DialogService<E> {

	private Dialog<E> dialog;
	
	protected final Logger LOG;	
	
	private ComponentLoader loader;
	
	public @Autowired void setLoader(ComponentLoader loader) {
		this.loader = loader;
	}

	protected @Autowired DialogService() {
		LOG = LoggerFactory.getLogger(DialogService.class);		
	}
	
	protected Object loadView(final String view) throws Exception{
		if(isShowing()) {/* si ya esta activo, pasando que va a ser el mismo */
			throw new Exception();
		}
		dialog = new Dialog<>();
		return loader.loadDialog(view, dialog.getDialogPane());
	}
	
	public boolean isShowing() {
		return this.dialog!=null;
	}
	
	protected void show( final Callback<ButtonType, E> rc, final Consumer<E> handler) {
		if(!isShowing()) {/* si ya esta activo, pasando que va a ser el mismo */
			return;
		}		
		dialog.setResultConverter(rc);
		dialog.showAndWait().ifPresent(e -> handler.accept(e));
		dialog = null;
		Platform.runLater(System::gc);
	}
	
}
