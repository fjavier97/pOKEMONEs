package com.pokemon.pokemones.core.item.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Entity
@Table(name="AUTHORITY")
public class AuthorityDPO implements GrantedAuthority, ItemDPO<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2719941175097548644L;
	
	public AuthorityDPO() {
	}
	
	public AuthorityDPO(final String authority) {
		setName(authority);
	}
	
	public @Override Long getPK(){
		return id;
	}
	
	/* ID ----------------------------------------------------------  */
	@Column
	@Id
	@GeneratedValue
	private Long id;
	public Long getId(){
		return id;
	}
	@Transient private LongProperty _id;
	public LongProperty idProperty(){
		if(_id == null){
			_id = new SimpleLongProperty(this,"id",id);
			_id.addListener((obs,nv,ov)->id=nv.longValue());
		}
		return _id;
	}
	
	/* NAME --------------------------------------------------------  */
	@Column(nullable=false, unique=true)
	private String name;
	public String getName(){
		return name;
	}
	public void setName(final String n){
		if(this._name==null){
			name=n;
		}else{
			this._name.set(n);
		}
	}
	@Transient private StringProperty _name;
	public StringProperty nameProperty(){
		if(_name == null){
			_name= new SimpleStringProperty(this,"name",name);
			_name.addListener((obs,nv,ov)->name=nv);
		}
		return _name;
	}
	
	@ManyToMany(mappedBy="authorities")
	private List<UserDPO> users;
	public List<UserDPO> getUsers(){
		return users;
	}
	@Transient private ObservableList<UserDPO> _users;
	public ObservableList<UserDPO> usersProperty(){
		if(_users == null){
			_users=FXCollections.observableArrayList();
			Bindings.bindContent(users,_users);
		}
		return _users;
	}
	
//	@ManyToMany(mappedBy="authorities")
//	private List<SeviceMethod> _serviceMethod;
//	public List<SeviceMethod> getServiceMethod(){
//		return _serviceMethod;
//	}
//	private ObservableList<SeviceMethod> serviceMethod;
//	public ObservableList<SeviceMethod> serviceMethodProperty(){
//		if(serviceMethod == null){
//			serviceMethod=FXCollection.ObserbableArrayList();
//			Bindings.bindContent(_serviceMethod,serviceMethod);
//		}
//		return serviceMethod;
//	}
	
//	@ManyToMany(mappedBy="authorities")
//	private List<Component> _component;
//	public List<Component> getComponent(){
//		return _component;
//	}
//	private ObservableList<Component> component;
//	public ObservableList<Component> componentProperty(){
//		if(component == null){
//			component=FXCollection.ObserbableArrayList();
//			Bindings.bindContent(_component,component);
//		}
//		return component;
//	}
	
	
	
	@Override
	public String getAuthority() {
		return getName();
	}
}