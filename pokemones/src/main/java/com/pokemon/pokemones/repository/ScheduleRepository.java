package com.pokemon.pokemones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pokemon.pokemones.core.item.dto.ScheduleDTO;

public interface ScheduleRepository extends JpaRepository<ScheduleDTO, Long>{

}
