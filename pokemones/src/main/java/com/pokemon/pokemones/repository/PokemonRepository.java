package com.pokemon.pokemones.repository;

import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.enums.Tipo;
import com.pokemon.pokemones.item.pk.PokemonPK;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PokemonRepository extends JpaRepository<PokemonDTO, PokemonPK>, JpaSpecificationExecutor<PokemonDTO>{


	public PokemonDTO findPokemonByNombre(final String nombre);

	//	@Query("SELECT COUNT(p) FROM Pokemon p")
	//	public Page<Long> countAll(Pageable pageable);

	public static Specification<PokemonDTO> empty(){
		return null;
	}
	
	public static Specification<PokemonDTO> numberGreater(final String attributeName, final int min) {
		return min == 0 ? null : (PokemonDTO, cq, cb) -> cb.lessThanOrEqualTo(PokemonDTO.get(attributeName), min);
	}

	public static Specification<PokemonDTO> numberLess(final String attributeName, final int max) {
		return max == 0 ? null : (PokemonDTO, cq, cb) -> cb.greaterThanOrEqualTo(PokemonDTO.get(attributeName), max);
	}

	public static Specification<PokemonDTO> numberBetween(final String attributeName, final int min, final int max ) {
		return Specification.where(empty()).and(numberLess(attributeName,max)).and(numberGreater(attributeName,min));
	}

	public static Specification<PokemonDTO> stringContains(final String attributeName, final String name) {
		return name == null || name.isEmpty() ? null : (PokemonDTO, cq, cb) -> cb.like(PokemonDTO.get(attributeName), "%" + name + "%");
	}

	public static Specification<PokemonDTO> stringEquals(final String attributeName, final String name) {
		return name == null || name.isEmpty() ? null : (PokemonDTO, cq, cb) -> cb.like(PokemonDTO.get(attributeName), name);
	}

	public static Specification<PokemonDTO> tipo(final Tipo tipo){
		return (PokemonDTO, cq, cb) -> cb.or(cb.equal(PokemonDTO.get("tipo1"), tipo),cb.equal(PokemonDTO.get("tipo2"), tipo));
	}
	
	public static Specification<PokemonDTO> tipos(final boolean op, final List<Tipo> tipos) {
		if(tipos==null || tipos.isEmpty()) return null;
		Specification<PokemonDTO> res = null;
		int i,j;
		for(i = 0; i < tipos.size() && res == null; i++){
			if(tipos.get(i) != null)
				res = tipo(tipos.get(i));
		}
		for(j = i; j < tipos.size(); j++){
			if(op){
				res = res.and(tipo(tipos.get(i)));
			}else{
				res = res.or(tipo(tipos.get(i)));	
			}
		}
		return res;
	}

//	static Specification<PokemonDTO> listContains(final String attributeName, final boolean op, final List<?> tipos) {
//		if(tipos==null || tipos.isEmpty()) return null;
//		Specification<PokemonDTO> res;
//		int i, j;
//		for(i = 0; i < specs.size() && res == null; i++){
//			if(specs[i] != null)
//				res = (PokemonDTO, cq, cb) -> cb.equals(PokemonDTO.get(attributeName), tipos.get(i));
//		}
//		for(j = i; j < specs.lenght; j++){
//			if(op){
//				res = res.and((PokemonDTO, cq, cb) -> cb.equals(PokemonDTO.get(attributeName), tipos.get(j)));
//			}else{
//				res = res.or((PokemonDTO, cq, cb) -> cb.equals(PokemonDTO.get(attributeName), tipos.get(j)));	
//			}
//		}
//		return res;
//	}

}
