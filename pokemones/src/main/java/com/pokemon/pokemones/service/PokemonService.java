package com.pokemon.pokemones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.enums.Tipo;
import com.pokemon.pokemones.item.pk.PokemonPK;
import com.pokemon.pokemones.repository.PokemonRepository;

@Service
public class PokemonService {

	private final PokemonRepository pokemonRepository;
	
	public @Autowired PokemonService(final PokemonRepository pokemonRepository){
		this.pokemonRepository = pokemonRepository;
	}	
	
	public PokemonDTO findById(final PokemonPK pk) {		
		return pokemonRepository.findById(pk).get();
	}
	
	public void save(final PokemonDTO model) {
		pokemonRepository.save(model);
	}
	
	public PokemonDTO create() {
		return new PokemonDTO();
	}
	
	
	
//	//@SECURED
//	public void create(final int pokedexNo, final String forma, final String nombre,
//			final Tipo tipo1,
//			final Tipo tipo2,
//			final int ATK,
//			final int DEF,
//			final int SPA,
//			final int SPD,
//			final int SPE,
//			final int HP					) {
//		//PokemonDTO pokemon = pokemonRepository.findById(new PokemonPK());
//		final PokemonDTO res = new PokemonDTO(pokedexNo, forma, nombre);
//		res.setBase_ATK(ATK);
//		res.setBase_DEF(DEF);
//		res.setBase_HP(HP);
//		res.setBase_SPA(SPA);
//		res.setBase_SPD(SPD);
//		res.setBase_SPE(SPE);
//		
//		pokemonRepository.save(res);
//	}
	
}
