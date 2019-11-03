package com.pokemon.pokemones.core.component.presenter;

import com.pokemon.pokemones.core.job.AbstractJobPerformable;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class JobListPresenter {

	private @FXML TableView<AbstractJobPerformable<?>> jobsView;

	public TableView<AbstractJobPerformable<?>> getJobsView() {
		return jobsView;
	} 
	
}
