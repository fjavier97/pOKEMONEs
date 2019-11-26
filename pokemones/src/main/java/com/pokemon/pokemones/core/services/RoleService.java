package com.pokemon.pokemones.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.component.presenter.RolesPresenter;
import com.pokemon.pokemones.core.item.dto.RoleDPO;
import com.pokemon.pokemones.core.repository.SecurityRoleRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Service
public class RoleService implements SpecificationExecutor<RoleDPO>{

	private final SecurityRoleRepository repo;
	
	public @Autowired RoleService(final SecurityRoleRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Page<RoleDPO> findAll(Specification<RoleDPO> spec, Pageable pageable) {
		return repo.findAll(spec, pageable);
	}

	public void delete(final List<RoleDPO> items) {
		repo.deleteAll(items);
	}
	
	public RoleDPO create() {
		//TODO dialogo para obtener String
		final RoleDPO item = new RoleDPO("");
		return save(item);
	}
	
	public RoleDPO save(final RoleDPO item) {		
		return repo.save(item);
	}
	
	
}
