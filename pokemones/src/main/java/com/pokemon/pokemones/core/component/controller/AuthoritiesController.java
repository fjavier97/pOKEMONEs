package com.pokemon.pokemones.core.component.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.pokemon.pokemones.core.item.dto.AuthorityDPO;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.services.AuthoriyService;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Component("Authority")
@Scope("ComponentScope")
public class AuthoritiesController extends AbstractController{

	@Override
	public void handleParams(Map<String, Object> args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshData() {
		// TODO Auto-generated method stub
		
	}

//	private final AuthoriyService roleService;
//	
//	public @Autowired AuthoritiesController(final AuthoriyService roleService) {
//		this.roleService=roleService;
//	}
//	
//	private @FXML void delete() {
//		final ObservableList<AuthorityDPO> items = getPresenter().get("tableview", TableView.class).getSelectionModel().getSelectedItems(); 
//		if(items.isEmpty()) return;
//		final List<AuthorityDPO> error_items = new LinkedList<AuthorityDPO>();
//		for(final AuthorityDPO item: items) {
//			if(!canDelete(item)) {
//				items.remove(item);
//				error_items.add(item);
//			}
//		}
//		
//		try {
//			roleService.delete(items);
//			refreshData();
//			// notificar que no se han eliminado error_items
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	
//	private boolean canDelete(AuthorityDPO item) {
//		return item.getUsers().size()==0 /*|| item.getServicemethodsSize()==0*/;		
//	}
//
//	private @FXML void create() {
//		try {
//			roleService.create();
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//	
//	private @FXML void edit() {
//		if(getPresenter().get("tableview",TableView.class).getSelectionModel().getSelectedItems().size()!=1) return;
//		final AuthorityDPO item = (AuthorityDPO) getPresenter().get("tableview",TableView.class).getSelectionModel().getSelectedItem();
//		try {
//			roleService.save(item);
//			refreshData();
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
//
//	@Override
//	protected SpecificationExecutor<AuthorityDPO> getRepo() {
//		return roleService;
//	}
//
//	@Override
//	protected void provideFilters(List<Specification<AuthorityDPO>> especificaciones) {
//				
//	}
//
//	@Override
//	protected void bindFilters() {
//		// TODO Auto-generated method stub
//		
//	}


}
