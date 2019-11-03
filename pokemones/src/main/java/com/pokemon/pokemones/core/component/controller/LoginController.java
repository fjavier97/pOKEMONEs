package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.presenter.LoginPresenter;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.LoginNotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.services.LoginService;

import javafx.fxml.FXML;

@Component("Login")
@Scope("ComponentScope")
public class LoginController extends AbstractController<LoginPresenter> {

	private final LoginService loginService;
	
	public @Autowired LoginController(final LoginService loginService){
		this.loginService = loginService;
	}
	
	protected @Override LoginPresenter initPresenter() {
		return new LoginPresenter();
	}
	
	private @FXML void login() {
 		try {
 			loginService.login(getPresenter().getUsrfield().getText(), getPresenter().getPwdfield().getText());
 			//getPresenter().getNotifiaction().setText("");
 	 		//clear(true,true);
 		}catch (UsernameNotFoundException e1) {
			getPresenter().getNotifiaction().setText("Usuario no encontrado");
			clear(true,true);
		}catch (BadCredentialsException e2) {
			getPresenter().getNotifiaction().setText("Contraseña incorrecta");
			clear(false,true);
		}catch (AuthenticationException e) {
			LOG.error("error en login", e);
			clear(true,true);
		} 		
 	}
 	
 	private void clear(final boolean clearusr, final boolean clearpwd) {
 		if(clearusr) getPresenter().getUsrfield().clear();
 		if(clearpwd) getPresenter().getPwdfield().clear();
 	}
 	
	
	public @Override void handleParams(Map<String, Object> args) {}
	
	public @Override void refreshData() {}

}
