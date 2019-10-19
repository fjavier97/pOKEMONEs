package com.pokemon.pokemones.repository;

import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.pk.PokemonPK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PokemonRepository extends JpaRepository<PokemonDTO, PokemonPK>{


	public PokemonDTO findPokemonByNombre(final String nombre);
	
//	@Query("SELECT COUNT(p) FROM Pokemon p")
//	public Page<Long> countAll(Pageable pageable);
	
}
