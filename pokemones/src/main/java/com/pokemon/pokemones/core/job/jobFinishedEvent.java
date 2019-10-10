package com.pokemon.pokemones.core.job;

import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;

public class jobFinishedEvent {

	private final Worker.State estado;
	
	private final AbstractJobPerformable<?> task;
	
	public jobFinishedEvent(final State estado, final AbstractJobPerformable<?> task) {
		super();
		this.estado = estado;
		this.task = task;
	}

	public Worker.State getEstado() {
		return estado;
	}

	public AbstractJobPerformable<?> getTask() {
		return task;
	}
		
}
