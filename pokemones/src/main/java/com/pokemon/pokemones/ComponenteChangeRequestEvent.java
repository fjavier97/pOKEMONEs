package com.pokemon.pokemones;

public class ComponenteChangeRequestEvent {

	private String newComponent;	
	
	public ComponenteChangeRequestEvent() {
		super();
	}

	public ComponenteChangeRequestEvent(String newComponent) {
		super();
		this.newComponent = newComponent;
	}

	public String getNewComponent() {
		return newComponent;
	}

	public void setNewComponent(String newComponent) {
		this.newComponent = newComponent;
	}
}
