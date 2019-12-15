package com.pokemon.pokemones.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.fxml.FXMLLoader;

public class Presenter {
	
	private final Map<String, Object> references;
	
	public Presenter() {
		references = new HashMap<String, Object>();
	}
	
	public void put(final String k, final Object o) {
		references.put(k, o);
	}
	
	public <E> E get(final String k, final Class<E> c) {
		final Object o = references.get(k);
		return o==null?null:c.cast(o);
	}
	
	public Set<String> getKeys(){
		return references.keySet();
	}
	
}
