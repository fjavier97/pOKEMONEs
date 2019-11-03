package com.pokemon.pokemones.core.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.presenter.UsersPresenter;
import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.services.SecurityUserService;

@Component("Users")
@Scope("ComponentScope")
public class UsersController extends PagedTableAbstractController<UserDPO , UsersPresenter>{
	
	private final SecurityUserService userService;
	
	public @Autowired UsersController(final SecurityUserService userService) {
		super();
		this.userService = userService;
	}
	
	@Override
	protected SpecificationExecutor<UserDPO> getRepo() {
		return userService;
	}

	@Override
	protected void provideFilters(List<Specification<UserDPO>> especificaciones) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UsersPresenter initPresenter() {
		return new UsersPresenter();
	}

}
