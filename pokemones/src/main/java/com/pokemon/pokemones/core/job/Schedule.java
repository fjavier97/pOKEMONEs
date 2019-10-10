package com.pokemon.pokemones.core.job;

import java.util.Map;
import java.util.concurrent.Callable;

import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.job.TestJob;

import javafx.concurrent.Worker;

public abstract class Schedule extends Thread{

	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private final Class<? extends AbstractJobPerformable<?>> jobClass;
	private final Callable<Map<String,Object>> parameterProvider;
	
	private final JobService jobService;

	protected Schedule(
			final Class<? extends AbstractJobPerformable<?>> jobclass, 
			final Callable<Map<String,Object>> parameterProvider, 
			final JobService jobservice								){
				
		this.jobClass = jobclass;
		this.parameterProvider = parameterProvider;
		this.jobService = jobservice;
	}
		
	public String getJobClassName() {
		return jobClass.getSimpleName();
	}
	
	public abstract void notify(final Object o, final Worker.State estado);
	
	protected abstract void await() throws InterruptedException;

	public @Override final void run(){
		while(true){
			try{		
				await();
				/* crear job */
				AbstractJobPerformable<?> job = null; //JobService.createJob();
				/* pasar argumentos al job */
				if(parameterProvider!=null){
					try { job.setParams(parameterProvider.call()); } catch (Exception e) {}
				}
				/* lanzar job */				
				jobService.execute(job);
			}catch(InterruptedException iex){
				break;
			}
		}
		//comprobar que el cronjob este guardado en la bbdd
	}
}
