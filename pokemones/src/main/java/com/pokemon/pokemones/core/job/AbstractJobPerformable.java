package com.pokemon.pokemones.core.job;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.item.dto.JobDTO;
import com.pokemon.pokemones.core.repository.JobRepository;
import com.pokemon.pokemones.core.services.JobService;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;

public abstract class AbstractJobPerformable <E> extends Task<E>{

	private final Logger LOG;
	
//	private List<NewAbstractJobPerformable<?>> nextJobs;
	
	protected JobRepository jobrepo;
	
	private long id;
	
	public long getId() {
		return id;
	}
		
	protected ApplicationEventPublisher publisher;
	
	private boolean start = false;
	private boolean finish = false;
	private boolean error = true;
	
	private @Autowired void setJobRepo(final JobRepository jobrepo) {
		this.jobrepo = jobrepo;
	}
	
	private @Autowired void setPublisher(final ApplicationEventPublisher publisher) {
		this.publisher=publisher;
	}	
	
	protected @Autowired AbstractJobPerformable() {		
		LOG = LoggerFactory.getLogger(AbstractJobPerformable.class);
		
		this.stateProperty().addListener((o,a,n)->{
			final JobDTO jobdto = jobrepo.findById(id).orElse(new JobDTO());
			jobdto.set(this);
			this.id = jobrepo.save(jobdto).getId();
		});
	}
	
	public String getName() {
		return getClass().getSimpleName();
	}	
	
	/*
	 * metodos encargados de la mascara de notificaciones cuando hay un cambio en el estado
	 * */
	public final void setNotificationMask(final boolean start, final boolean finish, final boolean error) {
		this.start = start;
		this.finish = finish;
		this.error = error;
	}	
	
	public final boolean getStartMask() {
		return start;
	}

	public final boolean getFinishMask() {
		return finish;
	}

	public final boolean getErroMask() {
		return error;
	}

	/**
	 * Metodo para recivir los parametros del trabajo
	 * @param params
	 */
	public abstract void setParams(final Map<String, Object> params);
	
	/**
	 * metod con el codigo a ejecutar por el trabajo
	 * @return
	 */
	public abstract E perform() throws Exception;
	
	protected final @Override E call() throws Exception {
		try {
			LOG.info("trabajo "+getName()+" iniciado");	
			final E result = perform();
			LOG.info("trabajo "+getName()+" finalizado correctamente");
			return result;
		}catch (Exception e) {
			LOG.warn("trabajo "+getName()+" fallido :"+e.toString());
			throw e;
		}
	}
	
	
	/* Metodos llamados durante el ciclo de vida del proceso.
	 * 
	 * Por defecto ponen notificaciones. Se pueden sobreescribir para realizar acciones mas concretas, pero asegurarse de llamar 
	 * al metodo respectivo de esta clase para heredar la funcionalidad en caso de sobreescribirse 
	 */
	
	protected @Override void failed() {
		final String msg = "trabajo "+getName()+" fallido :"+getException().toString();	
		if(error) {
			publisher.publishEvent(new NotificationEvent(msg,Threat.WARNING));
		}
	}
	
	protected @Override void scheduled() {
		final String msg = "trabajo "+getName()+" planificado";
		if(start) {
			publisher.publishEvent(new NotificationEvent(msg,Threat.INFO));
		}
	}

	protected @Override void succeeded() {
		final String msg = "trabajo "+getName()+" finalizado";
		if(finish) {
			publisher.publishEvent(new NotificationEvent(msg,Threat.INFO));
		}
	}
	
	/*Metodo que se ejecuta al acabar  el trabajo, sea por error o correctamente*/
	protected final @Override void done(){
		System.err.println(1);
		Platform.runLater(()->publisher.publishEvent(new jobFinishedEvent(getState(), this)));
		System.err.println(2);
//		if(getState().equals(Worker.State.SUCCEEDED)) {
//			for(NewAbstractJobPerformable<?> job : nextJobs) {
//				jobService.execute(job);
//			}
//		}
	}

}
