package com.pokemon.pokemones.controller.component;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.controller.component.AbstractController;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.event.NotificationEvent.Threat;
import com.pokemon.pokemones.core.locals.Lacalized;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.item.enums.Tipo;
import com.pokemon.pokemones.item.pk.PokemonPK;
import com.pokemon.pokemones.service.PokemonService;

import customfx.scene.control.EnumerationComboBox;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

@Component("PokemonEditor")
@Scope("ComponentScope")
public class PokemonEditorController extends AbstractController{
	
	private @Lacalized @FXML Label label_nombre;
	private @Lacalized @FXML Label label_forma;
	private @Lacalized @FXML Label label_pokedex_n;
	private @Lacalized @FXML Label label_tipo_1;
	private @Lacalized @FXML Label label_tipo_2;
	private @Lacalized @FXML Label label_ATK;
	private @Lacalized @FXML Label label_DEF;
	private @Lacalized @FXML Label label_SPA;
	private @Lacalized @FXML Label label_SPD;
	private @Lacalized @FXML Label label_SPE;
	private @Lacalized @FXML Label label_HP;
	
	private @FXML TextField input_nombre;
	private @FXML TextField input_forma;
	private @FXML Spinner<Integer> input_pokedex_n;
	private @FXML EnumerationComboBox input_tipo_1;
	private @FXML EnumerationComboBox input_tipo_2;
	private @FXML Spinner<Integer> input_ATK;
	private @FXML Spinner<Integer> input_DEF;
	private @FXML Spinner<Integer> input_SPA;
	private @FXML Spinner<Integer> input_SPD;
	private @FXML Spinner<Integer> input_SPE;
	private @FXML Spinner<Integer> input_HP;
	
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
			System.out.println("disable="+!nv);
			this.input_pokedex_n.setDisable(!nv);
			this.input_forma.setDisable(!nv);
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
		Bindings.bindBidirectional((Property)input_pokedex_n.getValueFactory().valueProperty(), model.pokedexNoProperty());
		Bindings.bindBidirectional((Property)input_forma.textProperty(), model.formaProperty());
		Bindings.bindBidirectional((Property)input_nombre.textProperty(), model.nombreProperty());
		
		Bindings.bindBidirectional((Property)input_tipo_1.valueProperty(), model.tipo1Property());
		Bindings.bindBidirectional((Property)input_tipo_2.valueProperty(), model.tipo2Property());
		
		Bindings.bindBidirectional((Property)input_ATK.getValueFactory().valueProperty(), model.baseATKProperty());
		Bindings.bindBidirectional((Property)input_DEF.getValueFactory().valueProperty(), model.baseDEFProperty());
		Bindings.bindBidirectional((Property)input_SPA.getValueFactory().valueProperty(), model.baseSPAProperty());
		Bindings.bindBidirectional((Property)input_SPD.getValueFactory().valueProperty(), model.baseSPDProperty());
		Bindings.bindBidirectional((Property)input_SPE.getValueFactory().valueProperty(), model.baseSPEProperty());
		Bindings.bindBidirectional((Property)input_HP.getValueFactory().valueProperty(), model.baseHPProperty());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected final /*@Override*/ void unbind(final PokemonDTO model){
				
		Bindings.unbindBidirectional((Property)input_pokedex_n.getValueFactory().valueProperty(), model.pokedexNoProperty());
		Bindings.unbindBidirectional((Property)input_forma.textProperty(), model.formaProperty());
		Bindings.unbindBidirectional((Property)input_nombre.textProperty(), model.nombreProperty());
		
		Bindings.unbindBidirectional((Property)input_tipo_1.valueProperty(), model.tipo1Property());
		Bindings.unbindBidirectional((Property)input_tipo_2.valueProperty(), model.tipo2Property());
		
		Bindings.unbindBidirectional((Property)input_ATK.getValueFactory().valueProperty(), model.baseATKProperty());
		Bindings.unbindBidirectional((Property)input_DEF.getValueFactory().valueProperty(), model.baseDEFProperty());
		Bindings.unbindBidirectional((Property)input_SPA.getValueFactory().valueProperty(), model.baseSPAProperty());
		Bindings.unbindBidirectional((Property)input_SPD.getValueFactory().valueProperty(), model.baseSPDProperty());
		Bindings.unbindBidirectional((Property)input_SPE.getValueFactory().valueProperty(), model.baseSPEProperty());
		Bindings.unbindBidirectional((Property)input_HP.getValueFactory().valueProperty(), model.baseHPProperty());
	}
	
	
	/* operaciones */	
	
	private @FXML void atras(){
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonList", Navigation.BACKWARD);
		publisher.publishEvent(evt);
	}
	
	private @FXML void guardar() {	
		try {
			pokemonService.save(modelo.get());					
			publisher.publishEvent(new NotificationEvent("pokemon guardado", Threat.SUCCESS));
			setCreationMode(false);
//		}catch (AuthenticationCredentialsNotFoundException e1) {
//			publisher.publishEvent(new NotificationEvent("no tiene la autoridad para realizar esta accion", Threat.WARNING));
		}catch (Exception e2) {
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
		this.modelo.set(pokemonService.findById(new PokemonPK(input_pokedex_n.getValue(), input_forma.getText())));
	}
		
	private void clearInputs() {
		this.input_pokedex_n.getValueFactory().setValue(0);
		this.input_nombre.clear();
		this.input_forma.clear();
		this.input_tipo_1.setValue(null);
		this.input_tipo_2.setValue(null);		
		this.input_ATK.getValueFactory().setValue(0);
		this.input_DEF.getValueFactory().setValue(0);
		this.input_SPA.getValueFactory().setValue(0);
		this.input_SPD.getValueFactory().setValue(0);
		this.input_SPE.getValueFactory().setValue(0);
		this.input_HP.getValueFactory().setValue(0);
	}

}
