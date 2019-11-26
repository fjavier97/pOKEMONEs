package com.pokemon.pokemones.core.item.dto;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public @Entity @Table(name="Role") class RoleDPO implements GrantedAuthority, ItemDPO<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StringProperty authorityProperty;
	private LongProperty idProperty;
	
	private ObservableList<UserDPO> users;
	private IntegerProperty usersSize;
	private ObservableList<ServiceMethodDPO> serviceMethods;
	private IntegerProperty servicemethodsSize;
	
	public RoleDPO() {
		this.authorityProperty =new SimpleStringProperty(this,"authority");
		this.idProperty = new SimpleLongProperty(this,"id");
		
		this.users = FXCollections.observableArrayList();
		this.usersSize = new SimpleIntegerProperty(this,"usersSize");
		
		this.serviceMethods = FXCollections.observableArrayList();
		this.servicemethodsSize = new SimpleIntegerProperty(this,"servicemethodsSize");
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

	
	

	public final IntegerProperty usersSizeProperty() {
		return this.usersSize;
	}
	
	@Transient
	public final int getUsersSize() {
		return this.usersSizeProperty().get();
	}
	
	public final IntegerProperty servicemethodsSizeProperty() {
		return this.servicemethodsSize;
	}
	
	@Transient
	public final int getServicemethodsSize() {
		return this.servicemethodsSizeProperty().get();
	}
	
	@ManyToMany(mappedBy="authorities")
	public List<UserDPO> getUsers() {
		return users;
	}

	@Transient
	public ObservableList<UserDPO> getUsersFx() {
		return users;
	}
	
	public void setUsers(List<UserDPO> users) {
		this.users.setAll(users);
		this.usersSize.set(users.size());
	}

	@ManyToMany(mappedBy="authorities")
	public List<ServiceMethodDPO> getServiceMethods() {
		return serviceMethods;
	}
	
	@Transient
	public ObservableList<ServiceMethodDPO> getServiceMethodsFx() {
		return serviceMethods;
	}

	public void setServiceMethods(List<ServiceMethodDPO> serviceMethods) {
		this.serviceMethods.setAll(serviceMethods);
		this.servicemethodsSize.set(serviceMethods.size());
	}
	
	

}
