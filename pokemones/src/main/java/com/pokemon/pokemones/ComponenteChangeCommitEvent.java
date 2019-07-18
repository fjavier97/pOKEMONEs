package com.pokemon.pokemones;

import javafx.scene.layout.BorderPane;

public class ComponenteChangeCommitEvent {

	private final BorderPane componente;

	public ComponenteChangeCommitEvent(final BorderPane componente) {
		super();
		this.componente = componente;
	}

	public BorderPane getComponente() {
		return componente;
	}
	
}
