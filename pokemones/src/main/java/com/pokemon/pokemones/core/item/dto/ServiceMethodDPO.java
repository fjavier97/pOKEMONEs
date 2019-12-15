package com.pokemon.pokemones.core.item.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@Entity
//@Table(uniqueConstraints={
//	    @UniqueConstraint(columnNames = {"serviceName", "methodName"})
//	}) 
public class ServiceMethodDPO implements ItemDPO<Long> {

//	private LongProperty idProperty;
//	private StringProperty serviceNameProperty;
//	private StringProperty methodNameProperty;
//	private ObservableList<RoleDPO> authorities;
//	
//	public ServiceMethodDPO() {
//		idProperty = new SimpleLongProperty(this,"id");
//		serviceNameProperty = new SimpleStringProperty(this,"serviceName");
//		methodNameProperty = new SimpleStringProperty(this,"methodName");
//		authorities = FXCollections.observableArrayList();
//	}
//	
	@Override
	@Transient
	public Long getPK() {
		//return getId();
		return null;
	}
//
//	public final LongProperty idProperty() {
//		return this.idProperty;
//	}
//	
//	@Column
//	@Id
//	@GeneratedValue
//	public final long getId() {
//		return this.idProperty().get();
//	}
//	
//
//	public final void setId(final long id) {
//		this.idProperty().set(id);
//	}
//	
//
//	public final StringProperty serviceNameProperty() {
//		return this.serviceNameProperty;
//	}
//	
//	@Column(nullable=false)
//	public final String getServiceName() {
//		return this.serviceNameProperty().get();
//	}
//	
//
//	public final void setServiceName(final String serviceName) {
//		this.serviceNameProperty().set(serviceName);
//	}
//	
//
//	public final StringProperty methodNameProperty() {
//		return this.methodNameProperty;
//	}
//	
//	@Column(nullable=false)
//	public final String getMethodName() {
//		return this.methodNameProperty().get();
//	}
//	
//
//	public final void setMethodName(final String methodName) {
//		this.methodNameProperty().set(methodName);
//	}
//	
//	
//	@ManyToMany 
//	@JoinTable(name="servicemethod_role",joinColumns=@JoinColumn(name="servicemethod_id"),inverseJoinColumns=@JoinColumn(name="role_id"))
//	public final List<RoleDPO> getAuthorities(){
//		return authorities;
//	}
//	
//	@Transient
//	public final ObservableList<RoleDPO> getAuthoritiesFx(){
//		return authorities;
//	}
//	
//	public void setAuthorities(List<RoleDPO> authorities) {
//		this.authorities.setAll(authorities);
//	}

}
