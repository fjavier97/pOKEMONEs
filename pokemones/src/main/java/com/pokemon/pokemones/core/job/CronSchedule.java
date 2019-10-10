package com.pokemon.pokemones.core.job;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.core.services.JobService;

import javafx.concurrent.Worker;

@Component
@Scope("prototype")
public class CronSchedule extends Schedule{
	
	private final CronSequenceGenerator csg;
	
	public @Autowired CronSchedule(
		final Class<? extends AbstractJobPerformable<?>> jobclass, final Callable<Map<String,Object>> parameterProvider,  final JobService jobservice,
		final String expression){

		super(jobclass, parameterProvider, jobservice);
		this.csg = new CronSequenceGenerator(expression);
		start();
	}

	public void notify(final Object o, final Worker.State estado){
		return; /* esta implementacion no depende de notificaciones */
	};
	
	protected void await() throws InterruptedException{
		final Date now = new Date();
		wait(csg.next(now).getTime()-now.getTime());
	}
	
}