package com.pokemon.pokemones.component.controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.controller.AbstractController;
import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.job.TestJob;

import javafx.fxml.FXML;

@Component("Prueba1")
@Scope("ComponentScope")
public class Prueba1Controller extends AbstractController<Void> {

	private final JobService js;
	
	public @Autowired Prueba1Controller(final JobService js) {
		super();
		this.js = js;
	}

	private @FXML void prueba1() {
		System.out.println("lanzando testjob");
		TestJob tj = (TestJob) js.create(TestJob.class);
		js.execute(tj);
	}
	
	@Override
	public void handleParams(Map<String, Object> args) {
		System.out.println(args.get("param1"));
		
	}
	
	public @Override void refreshData() {	
	}

	@Override
	protected Void initPresenter() {
		return null;
	}
	
}
