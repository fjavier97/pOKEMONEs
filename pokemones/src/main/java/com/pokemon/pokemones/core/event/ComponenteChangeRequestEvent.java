package com.pokemon.pokemones.core.event;

import java.util.HashMap;
import java.util.Map;

import com.pokemon.pokemones.core.Navigation;

public class ComponenteChangeRequestEvent {

	private final String newComponent;	
	private final Navigation navigation;
	private final Map<String, Object> params;
	
//	public ComponenteChangeRequestEvent() {
//		super();
//	}

	public ComponenteChangeRequestEvent(final String newComponent, final Navigation navigation) {		
		this(newComponent, navigation, new HashMap<String, Object>());
	}
	
	public ComponenteChangeRequestEvent(final String newComponent, final Navigation navigation, final Map<String, Object> params) {
		super();
		this.params = params;
		this.newComponent = newComponent;
		this.navigation = navigation;
	}
	
	public ComponenteChangeRequestEvent(String newComponent) {
		this(newComponent, Navigation.LINK);
	}

	public String getNewComponent() {
		return newComponent;
	}
	
	public Navigation getNavigation() {
		return navigation;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

}
