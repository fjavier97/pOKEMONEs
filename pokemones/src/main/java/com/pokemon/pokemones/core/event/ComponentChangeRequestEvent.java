package com.pokemon.pokemones.core.event;

import java.util.HashMap;
import java.util.Map;

import com.pokemon.pokemones.core.Navigation;

public class ComponentChangeRequestEvent {

	private final String newComponentName;	
	private final Navigation navigation;
	private final Map<String, Object> params;
	
//	public ComponenteChangeRequestEvent() {
//		super();
//	}

	public ComponentChangeRequestEvent(final String newComponent, final Navigation navigation) {		
		this(newComponent, navigation, new HashMap<String, Object>());
	}
	
	public ComponentChangeRequestEvent(final String newComponentName, final Navigation navigation, final Map<String, Object> params) {
		super();
		this.params = params;
		this.newComponentName = newComponentName;
		this.navigation = navigation;
	}
	
	public ComponentChangeRequestEvent(String newComponent) {
		this(newComponent, Navigation.LINK);
	}

	public String getNewComponentName() {
		return newComponentName;
	}
	
	public Navigation getNavigation() {
		return navigation;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}

}
