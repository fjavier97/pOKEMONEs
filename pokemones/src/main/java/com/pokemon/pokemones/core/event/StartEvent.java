package com.pokemon.pokemones.core.event;

import javafx.stage.Stage;

public class StartEvent {

	private final Stage stage;
	
	public StartEvent(final Stage stage) {
		this.stage=stage;
	}
	
	public Stage getStage() {
		return stage;
	}
	
}
