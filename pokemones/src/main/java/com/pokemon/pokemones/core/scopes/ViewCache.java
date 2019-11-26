package com.pokemon.pokemones.core.scopes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.Component;

@Service
public class ViewCache{

	private final Map<String,Component> cache;
	
	public ViewCache() {
		this.cache = new HashMap<String,Component>();
	}
	
	public void put(final String nombre, final Component component) {
		cache.put(nombre, component);
	}
	
	public Component get(final String nombre) {
		return cache.get(nombre);
	}
	
	public boolean contains(final String nombre) {
		return cache.containsKey(nombre);
	}
	
}
