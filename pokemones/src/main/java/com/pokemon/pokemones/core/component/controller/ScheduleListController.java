package com.pokemon.pokemones.core.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.presenter.PagedTablePresenter;
import com.pokemon.pokemones.core.item.dto.ScheduleDTO;
import com.pokemon.pokemones.core.repository.ScheduleRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.services.JobService;

@Component("ScheduleList")
@Scope("ComponentScope")
public class ScheduleListController extends PagedTableAbstractController<ScheduleDTO,PagedTablePresenter<ScheduleDTO>>{

	private final ScheduleRepository scheduleRepository;
	
	public @Autowired ScheduleListController(final ScheduleRepository scheduleRepository) {
		this.scheduleRepository=scheduleRepository;
	}
	
	
//	@Override
//	protected Page<ScheduleDTO> getPage(int i) {
//		return scheduleRepository.findAll(PageRequest.of(i, elements_per_page)); 
//	}


	@Override
	protected SpecificationExecutor<ScheduleDTO> getRepo() {
		return scheduleRepository;
	}


	@Override
	protected void provideFilters(List<Specification<ScheduleDTO>> especificaciones) {
		
	}


	@Override
	protected PagedTablePresenter<ScheduleDTO> initPresenter() {
		// TODO Auto-generated method stub
		return new PagedTablePresenter<ScheduleDTO>();
	}

}
