package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.services.SecurityUserService;
import com.pokemon.pokemones.item.dto.PokemonDTO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class ModelController<C, M> extends AbstractController<C>{

	private final ObjectProperty<M> modelo;
	private final BooleanProperty creating;
	
	protected final M getModel() {
		return modelo.get();
	}
	
	protected final void setModel(final M newmodel) {
		modelo.set(newmodel);
	}
	
	protected final boolean isCrationMode() {
		return creating.get();
	}
	
	protected final void setCreationMode(final boolean mode) {
		creating.set(mode);
	}

	public ModelController() {
		creating = new SimpleBooleanProperty(true);
		creating.addListener((obs, ov, nv)->{	
			/* settear a disable lo que no puede cambiarse */
		});
		
		modelo=new SimpleObjectProperty<>();
		modelo.addListener((obs, ov, nv)->{			
			LOG.info("model changed, loading bindings");	
			if(ov!=null) {
				unbind(ov);
			}
			bind(nv);			
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected abstract /*@Override*/ void bind(final M model);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected abstract /*@Override*/ void unbind(final M model);
	
	/* metodos heredados */
	public @Override void handleParams(Map<String, Object> args) {		
		
		setCreationMode(!args.containsKey("model"));
		
		//modelo.set(isCrationMode()?pokemonService.create():(PokemonDTO)args.get("model"));
		
		LOG.info("openenig editor in "+(isCrationMode()?"creating":"editing")+" mode");	
	}
	
	public @Override void refreshData() {
		this.modelo.set(userService.findById(pk));
	}
}
