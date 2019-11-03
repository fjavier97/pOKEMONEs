package com.pokemon.pokemones.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pokemon.pokemones.core.item.dto.JobDTO;

public interface JobRepository extends JpaRepository<JobDTO, Long>, JpaSpecificationExecutor<JobDTO>, SpecificationExecutor<JobDTO>{
	
}
