package com.pokemon.pokemones.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetails;

import com.pokemon.pokemones.core.item.dto.UserDPO;

public interface SecurityUserRepository extends JpaRepository<UserDPO, String>, JpaSpecificationExecutor<UserDPO>{

	public UserDetails findOneByUsername(final String username);
	
}
