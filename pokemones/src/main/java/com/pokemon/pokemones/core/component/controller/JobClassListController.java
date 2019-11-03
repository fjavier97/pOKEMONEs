package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.presenter.JobClassListPresenter;
import com.pokemon.pokemones.core.services.JobService;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

@Component("JobClassList")
@Scope("ComponentScope")
public class JobClassListController extends AbstractController<JobClassListPresenter>{

	private final JobService jobservice;	
	
	
	public @Autowired JobClassListController(final JobService jobservice){
		this.jobservice = jobservice;
	}	
	
	public @Override void handleParams(Map<String, Object> args) {
			refreshData();
	}
	
	public @Override void refreshData() {
		getPresenter().getJobView().setItems(jobservice.getAvailableJobs());	
	}

	@Override
	protected JobClassListPresenter initPresenter() {
		// TODO Auto-generated method stub
		return new JobClassListPresenter();
	}
	
}
