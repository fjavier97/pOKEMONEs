package com.pokemon.pokemones.core.component.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.presenter.UsersPresenter;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.services.UserService;
import com.pokemon.pokemones.item.dto.PokemonDTO;

import javafx.fxml.FXML;

@Component("Users")
@Scope("ComponentScope")
public class UsersController extends PagedTableAbstractController<UserDPO , UsersPresenter>{
	
	private final UserService userService;
	
	public @Autowired UsersController(final UserService userService) {
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
	
	private @FXML void modificar() {
		
		final UserDPO selectedItem = getPresenter().getTableview().getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("model",selectedItem);
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("User", Navigation.FORWARD, map);
		publisher.publishEvent(evt);
	}

}
