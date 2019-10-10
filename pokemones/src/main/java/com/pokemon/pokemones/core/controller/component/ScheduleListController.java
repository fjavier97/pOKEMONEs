package com.pokemon.pokemones.core.controller.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.item.dto.ScheduleDTO;
import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.repository.ScheduleRepository;

@Component("ScheduleList")
@Scope("ComponentScope")
public class ScheduleListController extends PagedTableAbstractController<ScheduleDTO>{

	private final ScheduleRepository scheduleRepository;
	
	public @Autowired ScheduleListController(final ScheduleRepository scheduleRepository) {
		this.scheduleRepository=scheduleRepository;
	}
	
	
	@Override
	protected Page<ScheduleDTO> getPage(int i) {
		return scheduleRepository.findAll(PageRequest.of(i, elements_per_page)); 
	}

}
