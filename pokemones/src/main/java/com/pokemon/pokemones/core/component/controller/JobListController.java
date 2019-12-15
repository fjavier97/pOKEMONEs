package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.presenter.JobListPresenter;
import com.pokemon.pokemones.core.job.AbstractJobPerformable;
import com.pokemon.pokemones.core.services.JobService;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Component("JobList")
@Scope("ComponentScope")
public class JobListController extends AbstractController{
	
	private final JobService jobService;
	
	public @Autowired JobListController( final JobService jobService ) {
		this.jobService = jobService;
	}
	
	@Override
	public void handleParams(Map<String, Object> args) {
		getPresenter().get("jobsView",TableView.class).setItems(jobService.getJobs());;		
	}
	
	public @Override void refreshData() {	
	}

	
}
