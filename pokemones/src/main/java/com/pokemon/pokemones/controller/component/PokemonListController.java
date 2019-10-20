package com.pokemon.pokemones.controller.component;

import java.util.HashMap;
import java.util.Map;

import com.pokemon.pokemones.core.locals.Lacalized;
import com.pokemon.pokemones.core.services.DialogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.controller.component.PagedTableAbstractController;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.repository.PokemonRepository;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;

@Component("PokemonList")
@Scope("ComponentScope")
public class PokemonListController extends PagedTableAbstractController<PokemonDTO>{	
	
	private @Lacalized @FXML TableColumn<PokemonDTO, ?> cabecera_texto;
	private @Lacalized @FXML TableColumn<PokemonDTO, ?> cabecera_numero;
	private @Lacalized @FXML TableColumn<PokemonDTO, ?> cabecera_tipos;
	private @Lacalized @FXML TableColumn<PokemonDTO, ?> cabecera_stats;
	
	private @Lacalized @FXML MenuItem crear;
	private @Lacalized @FXML MenuItem modificar;
	private @Lacalized @FXML MenuItem eliminar;
	private @Lacalized @FXML MenuItem ver;
	private @Lacalized @FXML MenuItem importar;
	private @Lacalized @FXML Menu editar;
	private @Lacalized @FXML Menu archivo;
	
	private final DialogService dialogService;
	
	private final PokemonRepository pokemonDAO;
	
	public @Autowired PokemonListController(final PokemonRepository pokemonDAO, final DialogService dialogService) {
		super();
		this.pokemonDAO = pokemonDAO;
		this.dialogService = dialogService;
	}
	
	protected @Override Page<PokemonDTO> getPage(final int i){
		return pokemonDAO.findAll(PageRequest.of(i, elements_per_page, Sort.by("pokedexNo"))); 
	}

	/*######################################################################################*/
	/*###							 metodos de los menus 								####*/
	/*######################################################################################*/
	
	private @FXML void modificar() {
		
		final PokemonDTO selectedItem = tableview.getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}	
		LOG.info("sending model [id="+selectedItem.getPokedexNo()+",forma="+selectedItem.getForma()+"]");	
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("model",selectedItem);
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonEditor", Navigation.FORWARD, map);
		publisher.publishEvent(evt);
	}
	
	private @FXML void crear() {
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonEditor", Navigation.FORWARD);
		publisher.publishEvent(evt);
	}
	
	private @FXML void eliminar() {
		
		final PokemonDTO selectedItem = tableview.getSelectionModel().getSelectedItem();
		
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
			System.out.println("eliminar");
			//pokemonDAO.delete(selectedItem);
			
		}else {
			System.out.println("nada");
		}
	}
	
	private @FXML void importar() {
		dialogService.prompt("importDialog");
	}	
	
	private @FXML void ver() {
		
		final PokemonDTO selectedItem = tableview.getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}	
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("item",selectedItem);
		
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonView", Navigation.FORWARD, map);
		publisher.publishEvent(evt);
	}
	
	/*######################################################################################*/
	/*###								 initialize 									####*/
	/*######################################################################################*/
	
	public @Override void handleParams(Map<String, Object> args) {
		super.handleParams(args);		
	}



}
