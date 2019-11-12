package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.presenter.UserPresenter; 
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.item.dto.RoleDPO;
import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.services.ModelManagerService;
import com.pokemon.pokemones.core.services.UserService;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;


@Component("User")
@Scope("ComponentScope")
public class UserController extends ModelController<UserPresenter, UserDPO, Long>{
					
		private final UserService userService;
		
		private @Autowired UserController(final UserService userService){
			this.userService = userService;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected final @Override void bind(final UserDPO model){
			Bindings.bindBidirectional((Property)getPresenter().getInput_nombre().textProperty(), (Property)getModel().usernameProperty());
			Bindings.bindBidirectional((Property)getPresenter().getInput_pwd().textProperty(), (Property)getModel().passwordProperty());
			Bindings.bindBidirectional((BooleanProperty)getPresenter().getInput_enabled().selectedProperty(), (BooleanProperty)getModel().enabledProperty());
			Bindings.bindContentBidirectional((ObservableList)getPresenter().getInput_roles().getValues(), (ObservableList)getModel().getAuthoritiesFx());
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		protected final @Override void unbind(final UserDPO model){
			Bindings.unbindBidirectional((Property)getPresenter().getInput_nombre().textProperty(), (Property)getModel().usernameProperty());
			Bindings.unbindBidirectional((Property)getPresenter().getInput_pwd().textProperty(), (Property)getModel().passwordProperty());
			Bindings.unbindBidirectional((BooleanProperty)getPresenter().getInput_enabled().selectedProperty(), (BooleanProperty)getModel().enabledProperty());
			Bindings.unbindContentBidirectional((ObservableList)getPresenter().getInput_roles().getValues(), (ObservableList)getModel().getAuthoritiesFx());
		}

		@Override
		protected ModelManagerService<UserDPO, Long> getService() {
			return userService;
		}

		@Override
		protected void onCreationModeChange(boolean nowcreating) {
			getPresenter().getInput_pwd().setDisable(!nowcreating);			
		}

		@Override
		protected UserPresenter initPresenter() {
			return new UserPresenter();
		}		
		
		
		/* operaciones */	
		
		private @FXML void atras(){
			final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("Users", Navigation.BACKWARD);
			publisher.publishEvent(evt);
		}
		
		private @FXML void guardar() {	
			try {
				userService.save(getModel());					
				publisher.publishEvent(new NotificationEvent("usuario guardado", Threat.SUCCESS));
				setCreationMode(false);
			}catch (AccessDeniedException e1) {
				publisher.publishEvent(new NotificationEvent("no tiene la autoridad para realizar esta accion", Threat.WARNING));
			}catch (Exception e2) {
				e2.printStackTrace();
				publisher.publishEvent(new NotificationEvent("error al guardar: "+e2.getMessage(), Threat.ERROR));
			}
					
		}

		private @FXML void refrescar() {
			setModel(isCrationMode()?userService.create():userService.findById(getModel().getPK()));
		}
		
		private @FXML void nuevo() {
			setCreationMode(true);
			setModel(userService.create());
		}
		
		private @FXML void cambio_pwd() {

		}
		
//		@Override
//		public void handleParams(Map<String, Object> args) {
//		// TODO Auto-generated method stub
//		super.handleParams(args);
//		getPresenter().getInput_roles().getItems().addAll(new RoleDPO("ROLE_1"),new RoleDPO("ROLE_2"),new RoleDPO("ROLE_3"));
//		}
		
}
