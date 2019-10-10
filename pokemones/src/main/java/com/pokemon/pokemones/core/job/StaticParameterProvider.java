package com.pokemon.pokemones.core.job;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class StaticParameterProvider extends HashMap<String,Object> implements Callable<Map<String,Object>>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public @Override HashMap<String,Object> call(){
		return this;
	}
	
}