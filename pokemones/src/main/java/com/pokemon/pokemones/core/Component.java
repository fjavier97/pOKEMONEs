package com.pokemon.pokemones.core;

import com.pokemon.pokemones.core.component.controller.AbstractController;

public class Component {

	private View view;
	
	private AbstractController controller;

	public Component() {
		this(new View(), null);
	}
	
	public Component(View view, AbstractController controller) {
		super();
		this.view = view;
		this.controller = controller;
	}
	
	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public AbstractController getController() {
		return controller;
	}

	public void setController(AbstractController controller) {
		this.controller = controller;
	}


	
	
	
}
