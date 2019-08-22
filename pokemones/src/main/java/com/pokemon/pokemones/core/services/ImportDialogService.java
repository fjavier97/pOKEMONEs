package com.pokemon.pokemones.core.services;

import java.io.Reader;
import java.util.function.Consumer;

import com.pokemon.pokemones.controller.ImportDialogController;

import javafx.scene.control.ButtonType;


public class ImportDialogService extends DialogService<Reader>{

	
	public ImportDialogService() {
		super();
	}
	
	protected void showImportDialog(final Consumer<Reader> handler) {
		try {
			ImportDialogController c = (ImportDialogController)super.loadView("ImportDialog");
			show( btntype -> btntype==ButtonType.OK ? c.openStream() : null , handler);
		}catch (Exception e) {
			LOG.error("error",e);
		}
	}
}
