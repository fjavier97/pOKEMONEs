package com.pokemon.pokemones.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationExecutor<T> {
 	public Page<T> findAll(Specification<T> spec, Pageable pageable);
}
