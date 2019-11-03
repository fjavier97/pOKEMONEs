package com.pokemon.pokemones.core.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.item.dto.ScheduleDTO;
import com.pokemon.pokemones.core.job.AbstractJobPerformable;
import com.pokemon.pokemones.core.job.ScanJobs;
import com.pokemon.pokemones.core.job.Schedule;
import com.pokemon.pokemones.core.job.jobFinishedEvent;
import com.pokemon.pokemones.core.repository.JobRepository;
import com.pokemon.pokemones.core.repository.ScheduleRepository;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@ScanJobs(packages = {"com.pokemon.pokemones"})
public class JobService {

	private final ObservableList<AbstractJobPerformable<?>> running;
	private final ObservableList<Schedule> schedule;
	
	/* aqui estan los diferentes trabajos definidos en el sistema */
	private final ObservableList<String> trabajos_disponibles;
	
	private final ApplicationContext ctx;
	
	public ObservableList<String> getAvailableJobs(){
		return trabajos_disponibles;
	}
	
	public ObservableList<AbstractJobPerformable<?>> getJobs(){
		return running;
	}
	
	private  final ScheduleRepository scheduleRepository;
	
	public @Autowired JobService(final ApplicationContext ctx, 
			final ClassPathScanningCandidateComponentProvider jobScanner, final ScheduleRepository scheduleRepository){
		
		this.ctx=ctx;
		this.scheduleRepository=scheduleRepository;

		this.schedule = FXCollections.observableArrayList();
		this.running = FXCollections.observableArrayList();
		
		// leer de BBDD los jobs planificados
		
		
		// job definitions
		final String[] dirs = getClass().getAnnotation(ScanJobs.class).packages();
		trabajos_disponibles=FXCollections.observableArrayList();
		for(String dir : dirs) {
			 for (BeanDefinition beanDef : jobScanner.findCandidateComponents(dir)) {
				 trabajos_disponibles.add(beanDef.getBeanClassName());
		     }
		}
	}
			
 	public AbstractJobPerformable<?> create(final String jobClassName, final Map<String,Object> args){
		
		final AbstractJobPerformable<?> job = create(jobClassName);
		
		if(job != null) {			
			job.setParams(args);
		}
		
		return job;
	}
	
	public AbstractJobPerformable<?> create(final String jobClassName){		
		try {
			
			final AbstractJobPerformable<?> job;
			job = (AbstractJobPerformable<?>) ctx.getBean(jobClassName);
			return job;
			
		}catch (ClassCastException ccex) {
			return null;
		}		
	}
 	
 	public AbstractJobPerformable<?> create(final Class<?extends AbstractJobPerformable<?>> jobClass, final Map<String,Object> args){
		
		final AbstractJobPerformable<?> job = create(jobClass);
		job.setParams(args);
		
		return job;
	}
	
	public AbstractJobPerformable<?> create(final Class<?extends AbstractJobPerformable<?>> jobClass){
		
		final AbstractJobPerformable<?> job = ctx.getBean(jobClass);
		
		return job;
	}

	public void execute(AbstractJobPerformable<?> task){		
		running.add(task);
		
		Thread t = new Thread(task);
		
		t.start();
	}
	
	private @EventListener void finished(final jobFinishedEvent evt) {
		this.running.remove(evt.getTask());
		for(Schedule s : this.schedule) {
			s.notify(evt.getTask().getClass(), evt.getEstado());
		}
		Platform.runLater(System::gc);
	}
	
	public void addSchedule(final Schedule schedule){
		/*guardo en BBDD y asigno id alcronjob*/
		schedule.setId(scheduleRepository.save(new ScheduleDTO(schedule)).getId());
		/*añado a la lista*/
		this.schedule.add(schedule);
	}
	
	public void removeSchedule(final long id){
		/*elimino de la BBDD*/
		scheduleRepository.deleteById(id);
		/*elimino de lalista*/
		Schedule r = null;
		for(Schedule s:this.schedule) {
			if(s.getId() == id) {
				r=s;
				break;
			}
		}
		if(r!=null) {
			r.interrupt();
			schedule.remove(r);
		}
	}
	
	public void editSchedule(final long id, final Schedule schedule) {
		removeSchedule(id);
		addSchedule(schedule);
	}

}
