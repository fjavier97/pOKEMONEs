package com.pokemon.pokemones.component.presenter;

import com.pokemon.pokemones.core.component.presenter.PagedTablePresenter;
import com.pokemon.pokemones.core.locals.Lacalized;
import com.pokemon.pokemones.item.dto.PokemonDTO;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class PokemonListPresenter extends PagedTablePresenter<PokemonDTO>{
	
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
	
	private @FXML TextField filtro_nombre;
	private @FXML TextField filtro_forma;
	private @FXML Spinner<Integer> filtro_id;
	public TableColumn<PokemonDTO, ?> getCabecera_texto() {
		return cabecera_texto;
	}
	public TableColumn<PokemonDTO, ?> getCabecera_numero() {
		return cabecera_numero;
	}
	public TableColumn<PokemonDTO, ?> getCabecera_tipos() {
		return cabecera_tipos;
	}
	public TableColumn<PokemonDTO, ?> getCabecera_stats() {
		return cabecera_stats;
	}
	public MenuItem getCrear() {
		return crear;
	}
	public MenuItem getModificar() {
		return modificar;
	}
	public MenuItem getEliminar() {
		return eliminar;
	}
	public MenuItem getVer() {
		return ver;
	}
	public MenuItem getImportar() {
		return importar;
	}
	public Menu getEditar() {
		return editar;
	}
	public Menu getArchivo() {
		return archivo;
	}
	public TextField getFiltro_nombre() {
		return filtro_nombre;
	}
	public TextField getFiltro_forma() {
		return filtro_forma;
	}
	public Spinner<Integer> getFiltro_id() {
		return filtro_id;
	}
	
	
	
}
