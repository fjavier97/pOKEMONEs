package com.pokemon.pokemones.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ImportDialogController {
	
	private Window ownerwindow;

	private @FXML TabPane options;
	
	private @FXML TextArea textarea;
	
	private @FXML TextField fileview;	
	private File file;

	public ImportDialogController() {
		file = null;
		ownerwindow = null;
	}
		
	private @FXML void chooseFile() {
		FileChooser chooser = new FileChooser();
		
		file = chooser.showOpenDialog(ownerwindow);
		fileview.setText(file.getName());
	}

	private Reader openStreamFromFile() {
		try { return new FileReader(file); } catch (FileNotFoundException e) { return null; }
	}
	
	private Reader openStreamFromText() {
		return new StringReader(textarea.getText());		
	}
	
	public Reader openStream() {
		switch (options.getSelectionModel().getSelectedItem().getId()) {
			case "script":
				return openStreamFromText();
				
			case "file":
				return openStreamFromFile();
				
			default:
				return null;
		}	
	}

	public void setOwnerwindow(Window ownerwindow) {
		this.ownerwindow = ownerwindow;
	}
	
	
}
