package com.pokemon.pokemones.dialog.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.dialog.controller.ImportDialogController;
import com.pokemon.pokemones.core.job.AbstractJobPerformable;
import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.job.PokemonCsvImportJob;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

@Component
@Scope("prototype")
public class PokemonCsvImportDialogController extends ImportDialogController{
	
	private final JobService jobservice;
	
	public @Autowired PokemonCsvImportDialogController(final JobService jobservice){
		super();
		this.jobservice = jobservice;
	}
	
	public @Override void accept(final Reader reader){
		final Map<String,Object> args = new HashMap<String, Object>();
		args.put("reader", reader);
		final AbstractJobPerformable<?> job = jobservice.create(PokemonCsvImportJob.class, args);
		jobservice.execute(job);
	}
	
	/*  controlador de la vista  */

	private @FXML TabPane options;
	
	private @FXML TextArea textarea;
	
	private @FXML TextField fileview;	
	private File file;

	
	private @FXML void chooseFile() {
		FileChooser chooser = new FileChooser();
		file = chooser.showOpenDialog(getOwner());
		if(file!=null) {
			fileview.setText(file.getName());
		}
	}

	private Reader openStreamFromFile() {
		try { return new FileReader(file); } catch (FileNotFoundException | NullPointerException e) { return null; }
	}
	
	private Reader openStreamFromText() {
		return textarea.getText() == null || textarea.getText().isEmpty() ? null : new StringReader(textarea.getText());		
	}
	
	protected @Override Reader openStream() {
		switch (options.getSelectionModel().getSelectedItem().getId()) {
			case "script":
				return openStreamFromText();
				
			case "file":
				return openStreamFromFile();
				
			default:
				return null;
		}	
	}

}
