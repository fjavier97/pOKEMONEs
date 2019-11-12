package com.pokemon.pokemones.core.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.repository.SpecificationExecutor;
import com.pokemon.pokemones.core.item.dto.ItemDPO;
import com.pokemon.pokemones.core.services.ModelManagerService;
import com.pokemon.pokemones.core.services.UserService;
import com.pokemon.pokemones.item.dto.PokemonDTO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

public abstract class ModelController<C, M extends ItemDPO<K>, K> extends AbstractController<C>{

	protected abstract ModelManagerService<M,K> getService();
	
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
			onCreationModeChange(nv);
		});
		
		modelo=new SimpleObjectProperty<>();
		modelo.addListener((obs, ov, nv)->{			
			LOG.info("model changed, loading bindings");
			if(ov!=null) unbind(ov);
			bind(nv);			
		});
	}
	
	protected abstract void onCreationModeChange(final boolean nowcreating); 
	
	protected abstract void bind(final M model);
	
	protected abstract void unbind(final M model);

	public @Override void handleParams(Map<String, Object> args) {		
		
		setCreationMode(!args.containsKey("model"));
		
		if(args.containsKey("model")) {
			modelo.set(isCrationMode()?getService().create():(M)args.get("model"));
			LOG.info("model:"+getModel().toString());
		}
		
		LOG.info("openenig editor in "+(isCrationMode()?"creating":"editing")+" mode");	
	}
		
	public @Override void refreshData() {
		setModel(isCrationMode()?getService().create():getService().findById(getModel().getPK()));
	}
}
