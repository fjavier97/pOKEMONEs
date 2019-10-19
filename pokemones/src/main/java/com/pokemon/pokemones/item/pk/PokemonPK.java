package com.pokemon.pokemones.item.pk;

import java.io.Serializable;

public class PokemonPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8145795096713648550L;
	
	private int pokedexNo;
	private String forma;
	
	public PokemonPK() {
		
	}
	
	public PokemonPK(final int pokedexNo, final String forma) {
		this.pokedexNo = pokedexNo;
		this.forma = forma;
	}

	public int getPokedexNo() {
		return pokedexNo;
	}

	public void setPokedexNo(int pokedexNo) {
		this.pokedexNo = pokedexNo;
	}

	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forma == null) ? 0 : forma.hashCode());
		result = prime * result + pokedexNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonPK other = (PokemonPK) obj;
		if (forma == null) {
			if (other.forma != null)
				return false;
		} else if (!forma.equals(other.forma))
			return false;
		if (pokedexNo != other.pokedexNo)
			return false;
		return true;
	}
	
	
}
