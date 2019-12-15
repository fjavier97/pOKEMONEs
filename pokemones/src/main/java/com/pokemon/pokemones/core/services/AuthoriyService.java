package com.pokemon.pokemones.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.component.presenter.RolesPresenter;
import com.pokemon.pokemones.core.item.dto.AuthorityDPO;
import com.pokemon.pokemones.core.item.dto.AuthorityDPO;
import com.pokemon.pokemones.core.repository.SecurityAuthorityRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Service
public class AuthoriyService implements SpecificationExecutor<AuthorityDPO>{

	private final SecurityAuthorityRepository repo;
	
	public @Autowired AuthoriyService(final SecurityAuthorityRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Page<AuthorityDPO> findAll(Specification<AuthorityDPO> spec, Pageable pageable) {
		return repo.findAll(spec, pageable);
	}

	public void delete(final List<AuthorityDPO> items) {
		repo.deleteAll(items);
	}
	
	public AuthorityDPO create() {
		//TODO dialogo para obtener String
		final AuthorityDPO item = new AuthorityDPO();
		return save(item);
	}
	
	public AuthorityDPO save(final AuthorityDPO item) {		
		return repo.save(item);
	}
	
	
}
