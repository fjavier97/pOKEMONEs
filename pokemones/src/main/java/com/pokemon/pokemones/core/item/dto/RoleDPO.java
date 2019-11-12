package com.pokemon.pokemones.core.item.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public @Entity @Table(name="Role") class RoleDPO implements GrantedAuthority, ItemDPO<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StringProperty authorityProperty;
	private LongProperty idProperty;
	
	public RoleDPO() {
		this.authorityProperty =new SimpleStringProperty(this,"authority");
		this.idProperty = new SimpleLongProperty(this,"id");
	}
	
	public RoleDPO(final String authority){
		this();
		setAuthority(authority);
	}
	
	public LongProperty idProperty() {
		return idProperty;
	}
	
	public @Id @GeneratedValue(strategy=GenerationType.AUTO) @Column Long getId(){
		return idProperty().get();
	}
	
	public void setId(final long id) {
		idProperty().set(id);
	}
	
	public StringProperty authorityProperty() {
		return authorityProperty;
	}
	
	public @Column(nullable=false, unique=true) @Override String getAuthority(){
		return authorityProperty.get();
	}
	
	public void setAuthority(final String authority){
		this.authorityProperty.set(authority);
	}
	
	public @Transient @Override String toString() {
		return getAuthority();
	}

	@Override @Transient
	public Long getPK() {
		return getId();
	}

}
