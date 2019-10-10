package com.pokemon.pokemones.core.controller.component;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.services.JobService;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

@Component("JobClassList")
@Scope("ComponentScope")
public class JobClassListController extends AbstractController{

	private final JobService jobservice;
	
	private @FXML ListView<String> jobView;
	
	public @Autowired JobClassListController(final JobService jobservice){
		this.jobservice = jobservice;
	}
	
	
	
	public @Override void handleParams(Map<String, Object> args) {
			refreshData();
	}
	
	public @Override void refreshData() {
		jobView.setItems(jobservice.getAvailableJobs());	
	}
	
}
