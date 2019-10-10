package com.pokemon.pokemones.core.job;

import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.services.JobService;
import com.pokemon.pokemones.core.services.JobService;

import javafx.concurrent.Worker;

@Component
@Scope("prototype")
public class ClassTriggerSchedule extends Schedule{
	
	public enum State{
		SUCCEDDED,FAILED,FINISHED
	}
	
	private final Class<?> triggerClass;
	private final State triggerState;
	
	public @Autowired ClassTriggerSchedule(
		final Class<? extends AbstractJobPerformable<?>> jobclass, final Callable<Map<String,Object>> parameterProvider,  final JobService jobservice, 
		final Class<?> triggerClass, final State triggerState){
			
		super(jobclass, parameterProvider, jobservice);
		this.triggerClass=triggerClass;
		this.triggerState=triggerState;
		start();
	}

	public void notify(final Object o, final Worker.State estado){
		if(o !=null && o instanceof Class && triggerClass.equals((Class<?>)o)){
			
			if(	(estado.equals(Worker.State.SUCCEEDED) && !triggerState.equals(State.FAILED)) || 
				(estado.equals(Worker.State.FAILED) && !triggerState.equals(State.SUCCEDDED)) )
			{				
				notify();
			}
		}
	};
	
	protected void await() throws InterruptedException{
		wait();
	}
	
}