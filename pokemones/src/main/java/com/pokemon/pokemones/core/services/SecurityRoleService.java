package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.item.dto.RoleDPO;
import com.pokemon.pokemones.core.repository.SecurityRoleRepository;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;

@Service
public class SecurityRoleService implements SpecificationExecutor<RoleDPO>, ModelManagerService<RoleDPO,Long>{

	private final SecurityRoleRepository repo;
	
	public @Autowired SecurityRoleService(final SecurityRoleRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public RoleDPO create() {
		return new RoleDPO();
	}

	@Override
	public RoleDPO findById(Long key) {
		// TODO Auto-generated method stub
		return repo.findById(key).get();
	}

	@Override
	public void save(final RoleDPO object) {
		repo.save(object);
	}

	@Override
	public Page<RoleDPO> findAll(Specification<RoleDPO> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return repo.findAll(spec, pageable);
	}

}
