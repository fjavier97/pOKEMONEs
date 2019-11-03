package com.pokemon.pokemones.core.item.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.userdetails.UserDetails;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Entity @Table(name="User")
public class UserDPO implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ObservableList<RoleDPO> authorities;
	private StringProperty usernameProperty;
	private StringProperty passwordProperty;
	private BooleanProperty enabledProperty;
	
	public UserDPO() {
		this.usernameProperty = new SimpleStringProperty(this,"username");
		this.passwordProperty = new SimpleStringProperty(this,"password");
		this.enabledProperty = new SimpleBooleanProperty(this,"enabled");
		this.authorities = FXCollections.observableArrayList();
	}
	
	public UserDPO(final String username, final String password) {
		this();
		setUsername(username);
		setPassword(password);
	}
	
	
	@Transient
	public ObservableList<RoleDPO> getAuthoritiesFx(){
		return authorities;
	}
	
	@ManyToMany @JoinTable(name="role",joinColumns=@JoinColumn(name="username"),inverseJoinColumns=@JoinColumn(name="authority")) @Override
	public List<RoleDPO> getAuthorities(){
		return authorities;
	}
	
	public void setAuthorities(ObservableList<RoleDPO> authorities) {
		this.authorities = authorities;
	}
	
	public void setAuthorities(List<RoleDPO> authorities) {
		this.authorities.setAll(authorities);
	}
	

	public final StringProperty usernameProperty() {
		return this.usernameProperty;
	}
	
	@Id @Column @Override
	public final String getUsername() {
		return this.usernameProperty().get();
	}
	

	public final void setUsername(final String username) {
		this.usernameProperty().set(username);
	}
	

	public final StringProperty passwordProperty() {
		return this.passwordProperty;
	}
	
	@Column @Override
	public final String getPassword() {
		return this.passwordProperty().get();
	}
	

	public final void setPassword(final String password) {
		this.passwordProperty().set(password);
	}
	

	public final BooleanProperty enabledProperty() {
		return this.enabledProperty;
	}
	
	@Column @Override
	public final boolean isEnabled() {
		return this.enabledProperty().get();
	}
	

	public final void setEnabled(final boolean enabled) {
		this.enabledProperty().set(enabled);
	}

	
	public @Transient @Override boolean isAccountNonExpired(){
		return true;
	}
	public @Transient @Override boolean isAccountNonLocked(){
		return true;
	}
	public @Transient @Override boolean isCredentialsNonExpired(){
		return true;
	}
}
