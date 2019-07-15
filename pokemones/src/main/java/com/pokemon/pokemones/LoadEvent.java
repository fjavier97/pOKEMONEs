package com.pokemon.pokemones;

import javafx.stage.Stage;

public class LoadEvent {

	private Stage stage;
	
	public LoadEvent(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
