package com.pokemon.pokemones.core;

public class LoadException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LoadException( final String msg, final Throwable cause) {
		super(msg,cause);
	}
	
	public LoadException( final String msg) {
		super(msg);
	}
	
	
	
}
