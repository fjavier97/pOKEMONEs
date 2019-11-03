package com.pokemon.pokemones.core.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.presenter.PagedTablePresenter;
import com.pokemon.pokemones.core.item.dto.JobDTO;
import com.pokemon.pokemones.core.repository.JobRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Component("JobHistory")
@Scope("ComponentScope")
public class JobHistoryController extends PagedTableAbstractController<JobDTO,PagedTablePresenter<JobDTO>>{

	private final JobRepository jobRepository;

	public @Autowired JobHistoryController(final JobRepository jobRepository) {
		super();
		this.jobRepository = jobRepository;
	}
//	
//	protected @Override Page<JobDTO> getPage(final int i){
//		return jobRepository.findAll(PageRequest.of(i, elements_per_page, Sort.by("name"))); 
//	}

	@Override
	protected SpecificationExecutor<JobDTO> getRepo() {
		return jobRepository;
	}

	@Override
	protected void provideFilters(List<Specification<JobDTO>> especificaciones) {
				
	}

	@Override
	protected PagedTablePresenter<JobDTO> initPresenter() {
		// TODO Auto-generated method stub
		return new PagedTablePresenter<JobDTO>();
	}	


	
}
