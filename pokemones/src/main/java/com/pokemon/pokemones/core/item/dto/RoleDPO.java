package com.pokemon.pokemones.core.item.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public @Entity @Table(name="Role") class RoleDPO implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StringProperty authorityProperty;
	
	public RoleDPO() {
		this.authorityProperty =new SimpleStringProperty(this,"authority");
	}
	
	public RoleDPO(final String authority){
		this();
		setAuthority(authority);
	}
	
	public StringProperty authorityProperty() {
		return authorityProperty;
	}
	
	public @Id @Column @Override String getAuthority(){
		return authorityProperty.get();
	}
	
	public void setAuthority(final String authority){
		this.authorityProperty.set(authority);
	}
	
	public @Transient @Override String toString() {
		return getAuthority();
	}

}
