package com.pokemon.pokemones.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.controller.AbstractController;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.item.dto.PokemonDTO;
import com.pokemon.pokemones.repository.PokemonDAO;
import com.pokemon.pokemones.service.PokemonImportService;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import net.bytebuddy.utility.privilege.GetSystemPropertyAction;

@Component("PokemonList")
@Scope("ComponentScope")
public class PokemonListController extends AbstractController{

	private int current_index;
	private int elements_per_page;
	
	private final PokemonImportService importService;
	
	private final PokemonDAO pokemonDAO;
	
	private @FXML Pagination paginator;
	
	private @FXML TableView<PokemonDTO> tableview;
	
	public @Autowired PokemonListController(final PokemonDAO pokemonDAO, final PokemonImportService importService) {
		super();
		this.pokemonDAO = pokemonDAO;
		this.importService = importService;
		current_index=0;
	}
	
	private void setElementsperPage(final int elements_per_page) {
		this.elements_per_page=elements_per_page;
	}
	
	/**
	 * metodo encargado de rellenar la tabla en funcion de los datos de la pagina
	 * @param i, indice
	 * @return tabla
	 */
	private Node navigate(final int i){
		// cambiar items de la tabla
		tableview.getItems().clear();
		final List<PokemonDTO> res = pokemonDAO.findAll(PageRequest.of(i, elements_per_page, Sort.by("pokedexNo"))).getContent();
		tableview.getItems().addAll(res);
		// devolver la vista
		current_index=i;
		return tableview;
	}

	/*######################################################################################*/
	/*###							 metodos de los menus 								####*/
	/*######################################################################################*/
	
	private @FXML void modificar() {
		
		final PokemonDTO selectedItem = tableview.getSelectionModel().getSelectedItem();
		
		if(selectedItem == null) {
			return;
		}	
		
		System.out.println("modificar");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("item",tableview.getSelectionModel().getSelectedItem());
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonEditor", Navigation.FORWARD, map);
//		publisher.publishEvent(evt);
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
		
		}else {
			System.out.println("nada");
		}
	}
	
	private @FXML void importar() {
		importService.show();

		navigate(paginator.getCurrentPageIndex());
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
	
	public @Override void injectArguments(Map<String, Object> args) {
		final Integer pageindex = (args.containsKey("index")?(Integer)args.get("index"):current_index);
		setElementsperPage(3);//TODO cambiar esto a bien
		paginator.setCurrentPageIndex(pageindex);
		paginator.setPageFactory(this::navigate);
	}

}
