package com.pokemon.pokemones.job;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.job.AbstractJobPerformable;
import com.pokemon.pokemones.core.job.JobPerformable;

@JobPerformable
public class TestJob extends AbstractJobPerformable<Void> {

	public @Autowired TestJob() {
		System.out.println("CREO BEAN JOB");
	}
	
	@Override
	public Void perform() {
		//updateProgress(-1, 1);
		for(int i = 1; i <= 100 ; i++) {
			updateProgress(i, 100);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		updateProgress(1, 1);
		return null;
	}

	@Override
	public void setParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}	
}
