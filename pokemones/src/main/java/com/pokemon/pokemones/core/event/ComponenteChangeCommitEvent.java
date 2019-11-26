package com.pokemon.pokemones.core.event;

import com.pokemon.pokemones.core.View;
import com.pokemon.pokemones.core.Navigation;

public class ComponenteChangeCommitEvent {

	private final View componente;
	private final Navigation navigation;

	public ComponenteChangeCommitEvent(final View componente, Navigation navigation) {
		super();
		this.componente = componente;
		this.navigation = navigation;
	}
	
	public ComponenteChangeCommitEvent(final View componente) {
		this(componente, Navigation.LINK);
	}

	public View getComponente() {
		return componente;
	}
	
	public Navigation getNavigation() {
		return navigation;
	}
}
