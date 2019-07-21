package com.pokemon.pokemones;

import javafx.scene.layout.BorderPane;

public class ComponenteChangeCommitEvent {

	private final Componente componente;

	public ComponenteChangeCommitEvent(final Componente componente) {
		super();
		this.componente = componente;
	}

	public Componente getComponente() {
		return componente;
	}
	
}
