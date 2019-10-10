package com.pokemon.pokemones.item.dto;

import javax.persistence.*;


import com.pokemon.pokemones.item.enums.Tipo;
import com.pokemon.pokemones.item.pk.PokemonPK;

@Entity
@Table(name="POKEMON")
@IdClass(PokemonPK.class)
public class PokemonDTO {

	private @Id int pokedexNo;
	private @Id String forma;
	
	private @Column String nombre;
	
	private @Column Tipo tipo1;
	private @Column Tipo tipo2;
	
	private @Column int base_ATK;
	private @Column int base_DEF;
	private @Column int base_SPA;
	private @Column int base_SPD;
	private @Column int base_SPE;
	private @Column int base_HP;
	
	public PokemonDTO() {
		
	}
	
	public PokemonDTO(final int pokedex_no, final String forma, final String nombre) {
		this.pokedexNo = pokedex_no;
		this.forma = forma;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Tipo getTipo1() {
		return tipo1;
	}

	public void setTipo1(Tipo tipo1) {
		this.tipo1 = tipo1;
	}

	public Tipo getTipo2() {
		return tipo2;
	}

	public void setTipo2(Tipo tipo2) {
		this.tipo2 = tipo2;
	}

	public int getBase_ATK() {
		return base_ATK;
	}

	public void setBase_ATK(int base_ATK) {
		this.base_ATK = base_ATK;
	}

	public int getBase_DEF() {
		return base_DEF;
	}

	public void setBase_DEF(int base_DEF) {
		this.base_DEF = base_DEF;
	}

	public int getBase_SPA() {
		return base_SPA;
	}

	public void setBase_SPA(int base_SPA) {
		this.base_SPA = base_SPA;
	}

	public int getBase_SPD() {
		return base_SPD;
	}

	public void setBase_SPD(int base_SPD) {
		this.base_SPD = base_SPD;
	}

	public int getBase_SPE() {
		return base_SPE;
	}

	public void setBase_SPE(int base_SPE) {
		this.base_SPE = base_SPE;
	}

	public int getBase_HP() {
		return base_HP;
	}

	public void setBase_HP(int base_HP) {
		this.base_HP = base_HP;
	}
	
	
	
}
