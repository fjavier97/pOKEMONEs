package com.pokemon.pokemones.core.controller.component;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.item.dto.JobDTO;
import com.pokemon.pokemones.repository.JobRepository;

@Component("JobHistory")
@Scope("ComponentScope")
public class JobHistoryController extends PagedTableAbstractController<JobDTO>{

	private final JobRepository jobRepository;

	public @Autowired JobHistoryController(final JobRepository jobRepository) {
		super();
		this.jobRepository = jobRepository;
	}
	
	protected @Override Page<JobDTO> getPage(final int i){
		return jobRepository.findAll(PageRequest.of(i, elements_per_page, Sort.by("name"))); 
	}	

//	public @Override void refreshData() {
//	//	super.refresh();
//	}
//	
//	public @Override void handleParams(Map<String, Object> args) {
//		super.handleParams(args);
//	}
	
}
