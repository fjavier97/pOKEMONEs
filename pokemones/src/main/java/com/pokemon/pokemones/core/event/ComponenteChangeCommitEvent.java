package com.pokemon.pokemones.core.event;

import com.pokemon.pokemones.core.Componente;
import com.pokemon.pokemones.core.Navigation;

public class ComponenteChangeCommitEvent {

	private final Componente componente;
	private final Navigation navigation;

	public ComponenteChangeCommitEvent(final Componente componente, Navigation navigation) {
		super();
		this.componente = componente;
		this.navigation = navigation;
	}
	
	public ComponenteChangeCommitEvent(final Componente componente) {
		this(componente, Navigation.LINK);
	}

	public Componente getComponente() {
		return componente;
	}
	
	public Navigation getNavigation() {
		return navigation;
	}
}
