package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.repository.SecurityUserRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Service
public class UserService implements SpecificationExecutor<UserDPO>, ModelManagerService<UserDPO,Long>{

	private SecurityUserRepository repo;
	
	public @Autowired UserService(final SecurityUserRepository repo){
		this.repo=repo;
	}
		
	@PreAuthorize("canUse()")
	public void save(final UserDPO model) {
		System.out.println("guardado");
		repo.save(model);
	}

	@Override
	public Page<UserDPO> findAll(Specification<UserDPO> spec, Pageable pageable) {
		final Page<UserDPO> res = repo.findAll(spec, pageable);
		return res;
	}
	
	public UserDPO findById(final Long id) {
		return repo.findById(id).get();
	}

	@Override
	public UserDPO create() {
		return new UserDPO();
	}
}
