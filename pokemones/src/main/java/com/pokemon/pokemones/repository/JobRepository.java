package com.pokemon.pokemones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pokemon.pokemones.core.item.dto.JobDTO;

public interface JobRepository extends JpaRepository<JobDTO, Long>{
	
}
