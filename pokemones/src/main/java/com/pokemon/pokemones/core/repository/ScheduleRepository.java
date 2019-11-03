package com.pokemon.pokemones.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pokemon.pokemones.core.item.dto.ScheduleDTO;

public interface ScheduleRepository extends JpaRepository<ScheduleDTO, Long>, JpaSpecificationExecutor<ScheduleDTO>, SpecificationExecutor<ScheduleDTO>{

}
