package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.repository.SecurityUserRepository;

@Service
public class SecurityUserService implements UserDetailsService{

	private SecurityUserRepository repo;
	
	protected @Autowired void setRepo(final SecurityUserRepository repo){
		this.repo=repo;
	}
	
	public @Override UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException{
		final UserDetails res = repo.findOneByUsername(username);
		if(res != null){
			return res;
		}		
		throw new UsernameNotFoundException("username notfound");
	}
	
}
