package com.pokemon.pokemones.core.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationManager implements AuthenticationManager, AuthenticationProvider {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	private final static Logger LOG = LoggerFactory.getLogger(CustomAuthenticationManager.class);
	
	public @Autowired CustomAuthenticationManager( final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder){
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}
	
	public @Override Authentication authenticate(Authentication authentication) throws AuthenticationException{
				
		final String request_usr = (String) authentication.getPrincipal();
		
		final String request_pwd = (String) authentication.getCredentials();
		
		final UserDetails response = userDetailsService.loadUserByUsername(request_usr);
		
		final boolean eq = passwordEncoder.matches(request_pwd, response.getPassword());
		
		if(!eq){
			throw new BadCredentialsException("bad credentials");
		}
			
		final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request_usr,request_pwd,response.getAuthorities());
		LOG.info("autenticado token:"+token.toString());
		return token;
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
