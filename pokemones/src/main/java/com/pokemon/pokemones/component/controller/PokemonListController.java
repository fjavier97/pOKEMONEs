package com.pokemon.pokemones.component.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.services.DialogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.controller.PagedTableAbstractController;
import com.pokemon.pokemones.core.event.ComponentChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.repository.PokemonRepository;
import com.pokemon.pokemones.service.PokemonService;

import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component("PokemonList")
@Scope("ComponentScope")
public class PokemonListController extends PagedTableAbstractController<PokemonDTO>{	
		
	private final DialogService dialogService;
	
	private final PokemonService pokemonservice;
		
	protected @Override SpecificationExecutor<PokemonDTO> getRepo(){
		return pokemonservice;
	}
	
	public @Autowired PokemonListController(final PokemonService pokemonservice, final DialogService dialogService) {
		super();
		this.pokemonservice = pokemonservice;
		this.dialogService = dialogService;
		filterProperties.put("filtro.forma", new SimpleStringProperty());
		filterProperties.put("filtro.nombre", new SimpleStringProperty());
	}
	
	/*######################################################################################*/
	/*###							 metodos de los menus 								####*/
	/*######################################################################################*/
	
	private @FXML void modificar() {
		
		final PokemonDTO selectedItem = (PokemonDTO) getPresenter().get("tableview",TableView.class).getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}	
		LOG.info("sending model [id="+selectedItem.getPokedexNo()+",forma="+selectedItem.getForma()+"]");	
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("model",selectedItem);
		final ComponentChangeRequestEvent evt = new ComponentChangeRequestEvent("PokemonEditor", Navigation.FORWARD, map);
		publisher.publishEvent(evt);
	}
	
	private @FXML void crear() {
		final ComponentChangeRequestEvent evt = new ComponentChangeRequestEvent("PokemonEditor", Navigation.FORWARD);
		publisher.publishEvent(evt);
	}
	
	private @FXML void eliminar() {
		
		final PokemonDTO selectedItem = (PokemonDTO) getPresenter().get("tableview",TableView.class).getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}	
		
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setTitle("");
		alert.setContentText("borrar elementos?");
		ButtonType btnSI = new ButtonType("SI");
		ButtonType btnNO = new ButtonType("NO");
		alert.getButtonTypes().setAll(btnSI,btnNO);
		if(alert.showAndWait().get()==btnSI) {
			pokemonservice.remove(selectedItem);
			publisher.publishEvent(new NotificationEvent("elemento eliminado", Threat.SUCCESS));
			refreshData();
		}
	}
	
	private @FXML void importar() {
		dialogService.prompt("importDialog");
	}	
	
	private @FXML void ver() {
		
		final PokemonDTO selectedItem = (PokemonDTO) getPresenter().get("tableview",TableView.class).getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}	
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("item",selectedItem);
		
		final ComponentChangeRequestEvent evt = new ComponentChangeRequestEvent("PokemonView", Navigation.FORWARD, map);
		publisher.publishEvent(evt);
	}
	
	/*######################################################################################*/
	/*###								 initialize 									####*/
	/*######################################################################################*/
	
	public @Override void handleParams(Map<String, Object> args) {
		super.handleParams(args);	
	}

	@Override
	protected void provideFilters(List<Specification<PokemonDTO>> especificaciones) {
		especificaciones.add(PokemonRepository.stringContains("nombre", ((StringProperty)filterProperties.get("filtro.nombre")).get()));
		especificaciones.add(PokemonRepository.stringContains("forma", ((StringProperty)filterProperties.get("filtro.forma")).get()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void bindFilters() {
		Bindings.bindBidirectional(getPresenter().get("filtro_nombre",TextField.class).textProperty(),(Property)filterProperties.get("filtro.nombre"));	
		Bindings.bindBidirectional(getPresenter().get("filtro_forma",TextField.class).textProperty(),(Property)filterProperties.get("filtro.forma"));
	}
	
	public @FXML @Override void refreshData() {	
		System.err.println(((StringProperty)filterProperties.get("filtro.nombre")).get());
		System.err.println(((StringProperty)filterProperties.get("filtro.forma")).get());
		super.refreshData();
	}





}
