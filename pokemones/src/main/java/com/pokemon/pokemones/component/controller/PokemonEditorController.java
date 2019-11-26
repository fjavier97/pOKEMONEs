package com.pokemon.pokemones.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.component.presenter.PokemonEditorPresenter;
import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.controller.AbstractController;
import com.pokemon.pokemones.core.component.controller.ModelController;
import com.pokemon.pokemones.core.event.ComponentChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.services.ModelManagerService;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.pk.PokemonPK;
import com.pokemon.pokemones.service.PokemonService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

@Component("PokemonEditor")
@Scope("ComponentScope")
public class PokemonEditorController extends ModelController<PokemonEditorPresenter,PokemonDTO, PokemonPK>{

	private final PokemonService pokemonService;
	
	private @Autowired PokemonEditorController(final PokemonService pokemonService){
		this.pokemonService = pokemonService;
	}

	protected @Override ModelManagerService<PokemonDTO, PokemonPK> getService() {
		return pokemonService;
	}

	protected @Override void onCreationModeChange(boolean nowcreating) {
		getPresenter().getInput_pokedex_n().setDisable(!nowcreating);
		getPresenter().getInput_forma().setDisable(!nowcreating);
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final @Override void bind(final PokemonDTO model){
		Bindings.bindBidirectional((Property)getPresenter().getInput_pokedex_n().getValueFactory().valueProperty(), model.pokedexNoProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_forma().textProperty(), model.formaProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_nombre().textProperty(), model.nombreProperty());
		
		Bindings.bindBidirectional((Property)getPresenter().getInput_tipo_1().valueProperty(), model.tipo1Property());
		Bindings.bindBidirectional((Property)getPresenter().getInput_tipo_2().valueProperty(), model.tipo2Property());
		
		Bindings.bindBidirectional((Property)getPresenter().getInput_ATK().getValueFactory().valueProperty(), model.baseATKProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_DEF().getValueFactory().valueProperty(), model.baseDEFProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_SPA().getValueFactory().valueProperty(), model.baseSPAProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_SPD().getValueFactory().valueProperty(), model.baseSPDProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_SPE().getValueFactory().valueProperty(), model.baseSPEProperty());
		Bindings.bindBidirectional((Property)getPresenter().getInput_HP().getValueFactory().valueProperty(), model.baseHPProperty());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final @Override void unbind(final PokemonDTO model){
				
		Bindings.unbindBidirectional((Property)getPresenter().getInput_pokedex_n().getValueFactory().valueProperty(), model.pokedexNoProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_forma().textProperty(), model.formaProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_nombre().textProperty(), model.nombreProperty());
		
		Bindings.unbindBidirectional((Property)getPresenter().getInput_tipo_1().valueProperty(), model.tipo1Property());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_tipo_2().valueProperty(), model.tipo2Property());
		
		Bindings.unbindBidirectional((Property)getPresenter().getInput_ATK().getValueFactory().valueProperty(), model.baseATKProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_DEF().getValueFactory().valueProperty(), model.baseDEFProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_SPA().getValueFactory().valueProperty(), model.baseSPAProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_SPD().getValueFactory().valueProperty(), model.baseSPDProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_SPE().getValueFactory().valueProperty(), model.baseSPEProperty());
		Bindings.unbindBidirectional((Property)getPresenter().getInput_HP().getValueFactory().valueProperty(), model.baseHPProperty());
	}

	
	protected @Override PokemonEditorPresenter initPresenter() {
		return new PokemonEditorPresenter();
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
