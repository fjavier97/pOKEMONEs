package com.pokemon.pokemones.core.item.dto;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.userdetails.UserDetails;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//////////////////////////////////////

@Entity
@Table(name="USER")
public class UserDPO implements UserDetails, ItemDPO<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5785296724231322660L;
	
	
	public @Override Long getPK(){
		return id;
	}
	
	public UserDPO(){//valores por defecto
		this.authorities=new LinkedList<AuthorityDPO>();
		this.enabled=false;
	}
	
	/* ID ----------------------------------------------------------  */
	@Column
	@Id
	@GeneratedValue
	private long id;
	public long getId(){
		return id;
	}
	@Transient private LongProperty _id = null;
	public LongProperty idProperty(){
		if(_id == null){
			_id = new SimpleLongProperty(this,"id",id);
			_id.addListener((obs,nv,ov)->id=nv.longValue());
		}
		return _id;
	}
	
	/* USERNAME -----------------------------------------------------  */
	@Column(nullable=false, unique=true)
	private String username;
	public @Override String getUsername(){
		return username;
	}
	public void setUsername(final String un){
		if(this._username==null){
			username=un;
		}else{
			this._username.set(un);
		}
	}
	@Transient private StringProperty _username;
	public StringProperty usernameProperty(){
		if(_username == null){
			_username= new SimpleStringProperty(this,"username",username);
			_username.addListener((obs,nv,ov)->username=nv);
		}
		return _username;
	}
	
	/* PASSWORD -----------------------------------------------------  */
	@Column(nullable=false, unique=true)
	private String password;
	public String getPassword(){
		return password;
	}
	public void setPassword(final String pw){
		if(this._password==null){
			password=pw;
		}else{
			this._password.set(pw);
		}
	}
	@Transient private StringProperty _password;
	public StringProperty passwordProperty(){
		if(_password == null){
			_password= new SimpleStringProperty(this,"password",password);
			_password.addListener((obs,nv,ov)->password=nv);
		}
		return _password;
	}
	
	/* ENABLED -----------------------------------------------------  */
	@Column(nullable=false)
	private Boolean enabled;
	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(final Boolean e){
		if(this._enabled==null){
			enabled=e;
		}else{
			this._enabled.set(e);
		}
	}
	@Transient private BooleanProperty _enabled;
	public BooleanProperty enabledProperty(){
		if(_enabled == null){
			_enabled= new SimpleBooleanProperty(this,"enabled",enabled);
			_enabled.addListener((obs,nv,ov)->enabled=nv);
		}
		return _enabled;
	}
	
	/* AUTHORITIES --------------------------------------------------  */
	@ManyToMany(cascade={CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable
	private List<AuthorityDPO> authorities;
	public List<AuthorityDPO> getAuthorities(){
		return authorities;
	}
	public void setAuthorities(final List<AuthorityDPO> as){
		if(this._authorities==null){
			authorities=as;
		}else{
			this._authorities.setAll(as);
		}
	}
	public void addAuthority(final AuthorityDPO a){
		if(this._authorities==null){
			authorities.add(a);
		}else{
			this._authorities.add(a);
		}
	}
	public void removeAuthority(final AuthorityDPO a){
		if(this._authorities==null){
			authorities.remove(a);
		}else{
			this._authorities.remove(a);
		}
	}
	@Transient private ObservableList<AuthorityDPO> _authorities;
	public ObservableList<AuthorityDPO> authoritiesProperty(){
		if(_authorities == null){
			_authorities=FXCollections.observableArrayList(); 
			Bindings.bindContent(authorities,_authorities);
		}
		return _authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
}
