package com.pokemon.pokemones.core.services;

public interface ModelManagerService<E,PK> {

	public E create();
	
	public E findById(final PK key);
	
	public void save(final E object);
	
}
