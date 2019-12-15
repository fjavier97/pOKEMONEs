package com.pokemon.pokemones.component.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.controller.ModelController;
import com.pokemon.pokemones.core.event.ComponentChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.services.ModelManagerService;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.pk.PokemonPK;
import com.pokemon.pokemones.service.PokemonService;

import customfx.scene.control.EnumerationComboBoxImpl;
import javafx.beans.binding.Bindings; 
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

@Component("PokemonEditor")
@Scope("ComponentScope")
public class PokemonEditorController extends ModelController<PokemonDTO, PokemonPK>{

	private final PokemonService pokemonService;
	
	private @Autowired PokemonEditorController(final PokemonService pokemonService){
		this.pokemonService = pokemonService;
	}

	protected @Override ModelManagerService<PokemonDTO, PokemonPK> getService() {
		return pokemonService;
	}

	protected @Override void onCreationModeChange(boolean nowcreating) {
		getPresenter().get("input_pokedex_n",Node.class).setDisable(!nowcreating);
		getPresenter().get("input_forma",Node.class).setDisable(!nowcreating);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final @Override void bind(final PokemonDTO model){
		Bindings.bindBidirectional((Property)getPresenter().get("input_pokedex_n",Spinner.class).getValueFactory().valueProperty(), model.pokedexNoProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_forma",TextField.class).textProperty(), model.formaProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_nombre",TextField.class).textProperty(), model.nombreProperty());
		
		Bindings.bindBidirectional((Property)getPresenter().get("input_tipo_1",EnumerationComboBoxImpl.class).valueProperty(), model.tipo1Property());
		Bindings.bindBidirectional((Property)getPresenter().get("input_tipo_2",EnumerationComboBoxImpl.class).valueProperty(), model.tipo2Property());
		
		Bindings.bindBidirectional((Property)getPresenter().get("input_ATK",Spinner.class).getValueFactory().valueProperty(), model.baseATKProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_DEF",Spinner.class).getValueFactory().valueProperty(), model.baseDEFProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_SPA",Spinner.class).getValueFactory().valueProperty(), model.baseSPAProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_SPD",Spinner.class).getValueFactory().valueProperty(), model.baseSPDProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_SPE",Spinner.class).getValueFactory().valueProperty(), model.baseSPEProperty());
		Bindings.bindBidirectional((Property)getPresenter().get("input_HP",Spinner.class).getValueFactory().valueProperty(), model.baseHPProperty());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final @Override void unbind(final PokemonDTO model){
				
		Bindings.unbindBidirectional((Property)getPresenter().get("input_pokedex_n",Spinner.class).getValueFactory().valueProperty(), model.pokedexNoProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_forma",TextField.class).textProperty(), model.formaProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_nombre",TextField.class).textProperty(), model.nombreProperty());
		
		Bindings.unbindBidirectional((Property)getPresenter().get("input_tipo_1",EnumerationComboBoxImpl.class).valueProperty(), model.tipo1Property());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_tipo_2",EnumerationComboBoxImpl.class).valueProperty(), model.tipo2Property());
		
		Bindings.unbindBidirectional((Property)getPresenter().get("input_ATK",Spinner.class).getValueFactory().valueProperty(), model.baseATKProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_DEF",Spinner.class).getValueFactory().valueProperty(), model.baseDEFProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_SPA",Spinner.class).getValueFactory().valueProperty(), model.baseSPAProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_SPD",Spinner.class).getValueFactory().valueProperty(), model.baseSPDProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_SPE",Spinner.class).getValueFactory().valueProperty(), model.baseSPEProperty());
		Bindings.unbindBidirectional((Property)getPresenter().get("input_HP",Spinner.class).getValueFactory().valueProperty(), model.baseHPProperty());
	}
	
	/* operaciones */
	
	private @FXML void refrescar() {
		refreshData();
	}
	
	private @FXML void guardar() {	
		try {
			pokemonService.save(getModel());					
			publisher.publishEvent(new NotificationEvent("pokemon guardado", Threat.SUCCESS));
			setCreationMode(false);
		}catch (AccessDeniedException e1) {
			publisher.publishEvent(new NotificationEvent("no tiene la autoridad para realizar esta accion", Threat.WARNING));
		}catch (Exception e2) {
			LOG.error("excepcion no esperada",e2);
			publisher.publishEvent(new NotificationEvent("error al guardar, ver log para mas informacion", Threat.ERROR));
		}
				
	}
	
	private @FXML void nuevo() {
		setCreationMode(true);
		setModel(pokemonService.create());
	}
	
	private @FXML void atras(){
		final ComponentChangeRequestEvent evt = new ComponentChangeRequestEvent("PokemonList", Navigation.BACKWARD);
		publisher.publishEvent(evt);
	}

}
