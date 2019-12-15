package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.item.dto.AuthorityDPO;
import com.pokemon.pokemones.core.repository.SecurityAuthorityRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Service
public class SecurityAuthorityService implements SpecificationExecutor<AuthorityDPO>, ModelManagerService<AuthorityDPO,Long>{

	private final SecurityAuthorityRepository repo;
	
	public @Autowired SecurityAuthorityService(final SecurityAuthorityRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public AuthorityDPO create() {
		return new AuthorityDPO();
	}

	@Override
	public AuthorityDPO findById(Long key) {
		// TODO Auto-generated method stub
		return repo.findById(key).get();
	}

	@Override
	public void save(final AuthorityDPO object) {
		repo.save(object);
	}

	@Override
	public Page<AuthorityDPO> findAll(Specification<AuthorityDPO> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(spec, pageable);
	}

}
