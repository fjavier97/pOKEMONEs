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
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
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
public class PokemonEditorController extends AbstractController<PokemonEditorPresenter>{
		
	private final ObjectProperty<PokemonDTO> modelo;
	private final BooleanProperty creating;
	
	private final PokemonDTO getModel() {
		return modelo.get();
	}
	
	private final void setModel(final PokemonDTO newmodel) {
		modelo.set(newmodel);
	}
	
	private final boolean isCrationMode() {
		return creating.get();
	}
	
	private final void setCreationMode(final boolean mode) {
		creating.set(mode);
	}
	
	private final PokemonService pokemonService;
	
	private @Autowired PokemonEditorController(final PokemonService pokemonService){
		this.pokemonService = pokemonService;

		creating = new SimpleBooleanProperty(true);
		creating.addListener((obs, ov, nv)->{
			getPresenter().getInput_pokedex_n().setDisable(!nv);
			getPresenter().getInput_forma().setDisable(!nv);
		});
		
		modelo=new SimpleObjectProperty<PokemonDTO>();
		modelo.addListener((obs, ov, nv)->{			
			LOG.info("model changed, loading bindings");	
			if(ov!=null) {
				unbind(ov);
			}
			bind(nv);			
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final /*@Override*/ void bind(final PokemonDTO model){
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
	protected final /*@Override*/ void unbind(final PokemonDTO model){
				
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
	
	
	/* operaciones */	
	
	private @FXML void atras(){
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonList", Navigation.BACKWARD);
		publisher.publishEvent(evt);
	}
	
	private @FXML void guardar() {	
		try {
			System.out.println("al llamar:\n"+SecurityContextHolder.getContext().getAuthentication().toString());
			pokemonService.save(modelo.get());					
			publisher.publishEvent(new NotificationEvent("pokemon guardado", Threat.SUCCESS));
			setCreationMode(false);
		}catch (AccessDeniedException e1) {
			publisher.publishEvent(new NotificationEvent("no tiene la autoridad para realizar esta accion", Threat.WARNING));
			System.out.println("tras la excepcion:\n"+SecurityContextHolder.getContext().getAuthentication().toString());
		}catch (Exception e2) {
			e2.printStackTrace();
			publisher.publishEvent(new NotificationEvent("error al guardar: "+e2.getMessage(), Threat.ERROR));
		}
				
	}

	private @FXML void refrescar() {
		setModel(isCrationMode()?pokemonService.create():pokemonService.findById(getModel().getPK()));
	}
	
	private @FXML void nuevo() {
		setCreationMode(true);
		setModel(pokemonService.create());
	}
	
	
	/* metodos heredados */
	public @Override void handleParams(Map<String, Object> args) {		
		
		setCreationMode(!args.containsKey("model"));
		
		modelo.set(isCrationMode()?pokemonService.create():(PokemonDTO)args.get("model"));

		
		if(!isCrationMode())
			LOG.info("containing model [id="+modelo.get().getPokedexNo()+",forma="+modelo.get().getForma()+"]");
		
		LOG.info("openenig editor in "+(isCrationMode()?"creating":"editing")+" mode");	
	}
	
	public @Override void refreshData() {
		final PokemonPK pk = new PokemonPK(getPresenter().getInput_pokedex_n().getValue(), getPresenter().getInput_forma().getText());
		this.modelo.set(pokemonService.findById(pk));
	}

	@Override
	protected PokemonEditorPresenter initPresenter() {
		return new PokemonEditorPresenter();
	}
		
//	private void clearInputs() {
//		getPresenter().getInput_pokedex_n().getValueFactory().setValue(0);
//		getPresenter().getInput_nombre().clear();
//		getPresenter().getInput_forma().clear();
//		getPresenter().getInput_tipo_1().setValue(null);
//		getPresenter().getInput_tipo_2().setValue(null);		
//		getPresenter().getInput_ATK().getValueFactory().setValue(0);
//		getPresenter().getInput_DEF().getValueFactory().setValue(0);
//		getPresenter().getInput_SPA().getValueFactory().setValue(0);
//		getPresenter().getInput_SPD().getValueFactory().setValue(0);
//		getPresenter().getInput_SPD().getValueFactory().setValue(0);
//		getPresenter().getInput_HP().getValueFactory().setValue(0);
//	}

}
