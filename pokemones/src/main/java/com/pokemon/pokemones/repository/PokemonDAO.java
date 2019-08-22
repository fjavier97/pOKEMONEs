package com.pokemon.pokemones.repository;

import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.pk.PokemonPK;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonDAO extends JpaRepository<PokemonDTO, PokemonPK>{

	public PokemonDTO findPokemonByNombre(final String nombre);
	
}
