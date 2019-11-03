package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.repository.SecurityUserRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Service
public class SecurityUserService implements UserDetailsService, SpecificationExecutor<UserDPO> {

	private final SecurityUserRepository repo;
	
	public @Autowired SecurityUserService(final SecurityUserRepository repo){
		this.repo=repo;
	}
	
	public @Override UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException{
		final UserDetails res = repo.findOneByUsername(username);
		if(res != null){
			return res;
		}		
		throw new UsernameNotFoundException("username notfound");
	}
	
	public void save(final UserDPO model) {
		repo.save(model);
	}

	@Override
	public Page<UserDPO> findAll(Specification<UserDPO> spec, Pageable pageable) {
		return repo.findAll(spec, pageable);
	}
	
	public UserDPO findById(final String id) {
		return repo.findById(id).get();
	}
}
