package com.pokemon.pokemones;

public class MyInt {
	private int value;

	public MyInt(int value) {
		super();
		this.value = value;
	}
	
	public MyInt() {
		super();
		this.value = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ""+value;
	}
	
	
}
