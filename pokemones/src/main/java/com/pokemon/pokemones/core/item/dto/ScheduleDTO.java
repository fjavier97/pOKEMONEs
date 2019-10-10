package com.pokemon.pokemones.core.item.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pokemon.pokemones.core.job.ClassTriggerSchedule;
import com.pokemon.pokemones.core.job.CronSchedule;
import com.pokemon.pokemones.core.job.Schedule;

@Entity
@Table(name="JOBS")	
public class ScheduleDTO {
	
	private @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;
	
	private @Column String job;
	
	private @Column String type;
	
	private @Column String triggerExpression;
	
	public ScheduleDTO() {
		
	}
	
	public ScheduleDTO(final Schedule schedule) {
		this();
		set(schedule);
	}
	
	public void set(final Schedule schedule) {
		this.job = schedule.getJobClassName();
		if(schedule instanceof CronSchedule) {
			this.type = "CRON";
//			this.triggerExpression = 
		}
		if(schedule instanceof ClassTriggerSchedule) {
			this.type = "CLASS_TRIGGER";
		}
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTriggerExpression() {
		return triggerExpression;
	}

	public void setTriggerExpression(String triggerExpression) {
		this.triggerExpression = triggerExpression;
	}

}
