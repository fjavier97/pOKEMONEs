package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.LoginNotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;


@Service
public class LoginService {
	
	private final AuthenticationManager authenticationManager;
	
	private final ApplicationEventPublisher publisher;
	
	public @Autowired LoginService(final AuthenticationManager authenticationManager, final ApplicationEventPublisher publisher) {
		this.authenticationManager = authenticationManager;
		this.publisher = publisher;
	}

	public void login(final String usr, final String pwd) throws AuthenticationException {
		if(SecurityContextHolder.getContext().getAuthentication() != null) {
			System.err.println("error, ya hay algien autenticado");
			return;
		}
		/* creamos token y lo autenticamos */
		final Authentication unauthorizedToken = new UsernamePasswordAuthenticationToken(usr,pwd);
		final Authentication authorizedToken = authenticationManager.authenticate(unauthorizedToken);
		/* lo guardamos en el contexto de seguridad */
		SecurityContextHolder.getContext().setAuthentication(authorizedToken);
		/* publicamos evento para cambiar usuario en UI */
		publisher.publishEvent(new LoginNotificationEvent(true,usr));
		/* publicamos evento para cambiar usuario en UI */
		publisher.publishEvent(new NotificationEvent("login successful",Threat.SUCCESS));
		/* cambiamos el componente al componente inicial */
		publisher.publishEvent(new ComponenteChangeRequestEvent("Prueba1", Navigation.LINK));
	}
	
	public void logout(){
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			return;
		}
		/*limpiamos el contexto de seguridad */
		SecurityContextHolder.getContext().setAuthentication(null);
		/* publicamos evento para cambiar usuario en UI */
 		publisher.publishEvent(new LoginNotificationEvent(false));
		/* cambiamos el componente al componente de login */
 		publisher.publishEvent(new ComponenteChangeRequestEvent("Login", Navigation.LINK));
	}
	
	public boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication()==null?false:SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}
	
}
