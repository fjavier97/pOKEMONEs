package com.pokemon.pokemones.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pokemon.pokemones.core.item.dto.AuthorityDPO;

public interface SecurityAuthorityRepository extends JpaRepository<AuthorityDPO, Long>, JpaSpecificationExecutor<AuthorityDPO>{


	
}