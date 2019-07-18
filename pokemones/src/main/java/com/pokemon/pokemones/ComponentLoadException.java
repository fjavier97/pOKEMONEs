package com.pokemon.pokemones;

public class ComponentLoadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216143920899927834L;
	
	private final String componentID;
	private final String msg;
	
	public ComponentLoadException(final String componentID, final String msg) {
		this.componentID=componentID;
		this.msg=msg;
	}
	
	public String getComponentID() {
		return this.componentID;
	}
	
	public String getMSG() {
		return this.msg;
	}
	
	

}
