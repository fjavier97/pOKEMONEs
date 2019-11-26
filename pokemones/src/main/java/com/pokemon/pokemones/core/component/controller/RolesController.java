package com.pokemon.pokemones.core.component.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.presenter.RolesPresenter;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.item.dto.RoleDPO;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.services.RoleService;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

@Component("Role")
@Scope("ComponentScope")
public class RolesController extends PagedTableAbstractController<RoleDPO, RolesPresenter>{

	private final RoleService roleService;
	
	public @Autowired RolesController(final RoleService roleService) {
		this.roleService=roleService;
	}
	
	private @FXML void delete() {
		final ObservableList<RoleDPO> items = getPresenter().getTableview().getSelectionModel().getSelectedItems(); 
		if(items.isEmpty()) return;
		if(!canDelete(items)) {
			publisher.publishEvent(new NotificationEvent("no se puede borra, hay elementos usados",Threat.ERROR));
			return;
		}
		try {
			roleService.delete(items);
			refreshData();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private boolean canDelete(ObservableList<RoleDPO> items) {
		for(RoleDPO item:items) {
			if(item.getUsersSize()!=0 || item.getServicemethodsSize()!=0) {
				return false;
			}
		}
		return true;
	}

	private @FXML void create() {
		try {
			roleService.create();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private @FXML void edit() {
		if(getPresenter().getTableview().getSelectionModel().getSelectedItems().size()!=1) return;
		final RoleDPO item = getPresenter().getTableview().getSelectionModel().getSelectedItem(); 
		try {
			roleService.save(item);
			refreshData();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	@Override
	protected RolesPresenter initPresenter() {
		return new RolesPresenter();
	}

	@Override
	protected SpecificationExecutor<RoleDPO> getRepo() {
		return roleService;
	}

	@Override
	protected void provideFilters(List<Specification<RoleDPO>> especificaciones) {
		// TODO Auto-generated method stub
		
	}


}
