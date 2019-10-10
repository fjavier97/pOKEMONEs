package com.pokemon.pokemones.core.item.dto;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.pokemon.pokemones.core.job.AbstractJobPerformable;

import javafx.concurrent.Worker;


@Entity
@Table(name="JOBS")
public class JobDTO {

	
	private @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;
	
	private @Column String name;
	
	private @Column String estado;
	
	private @Column String excepcion;
	
	private @Column(columnDefinition = "TIMESTAMP") LocalDateTime fechaInicio;
	
	private @Column(columnDefinition = "TIMESTAMP") LocalDateTime fechaFin;
	
	private @Column(columnDefinition = "TIMESTAMP") LocalDateTime fechaUltimaModificacion;
	
	public JobDTO() {
		// para JPA
	}
	
	public JobDTO(AbstractJobPerformable<?> job) {
		this.set(job);
	}
	
	@Transient
	public void set(AbstractJobPerformable<?> job) {
		this.name = job.getName();
		this.estado = job.getState().name();
		this.fechaUltimaModificacion = LocalDateTime.now();
		if(job.getState().equals(Worker.State.SUCCEEDED)) {
			this.fechaFin = LocalDateTime.now();
		}
		if(job.getState().equals(Worker.State.SCHEDULED)) {
			this.fechaInicio = LocalDateTime.now();
		}
		if(job.getException()!=null) {
			this.excepcion = job.getException().toString();
		}
	}
	
	/* datos */
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEstado() {
		return estado;
	}	

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}
	
	/* fechas */
	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalDateTime getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	
}
