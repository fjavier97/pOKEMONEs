package com.pokemon.pokemones.component.presenter;

import com.pokemon.pokemones.core.locals.Lacalized;
import com.pokemon.pokemones.item.enums.Tipo;

import customfx.scene.control.EnumerationComboBoxImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class PokemonEditorPresenter {

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
	private @FXML EnumerationComboBoxImpl<Tipo> input_tipo_1;
	private @FXML EnumerationComboBoxImpl<Tipo> input_tipo_2;
	private @FXML Spinner<Integer> input_ATK;
	private @FXML Spinner<Integer> input_DEF;
	private @FXML Spinner<Integer> input_SPA;
	private @FXML Spinner<Integer> input_SPD;
	private @FXML Spinner<Integer> input_SPE;
	private @FXML Spinner<Integer> input_HP;
	public Label getLabel_nombre() {
		return label_nombre;
	}
	public Label getLabel_forma() {
		return label_forma;
	}
	public Label getLabel_pokedex_n() {
		return label_pokedex_n;
	}
	public Label getLabel_tipo_1() {
		return label_tipo_1;
	}
	public Label getLabel_tipo_2() {
		return label_tipo_2;
	}
	public Label getLabel_ATK() {
		return label_ATK;
	}
	public Label getLabel_DEF() {
		return label_DEF;
	}
	public Label getLabel_SPA() {
		return label_SPA;
	}
	public Label getLabel_SPD() {
		return label_SPD;
	}
	public Label getLabel_SPE() {
		return label_SPE;
	}
	public Label getLabel_HP() {
		return label_HP;
	}
	public TextField getInput_nombre() {
		return input_nombre;
	}
	public TextField getInput_forma() {
		return input_forma;
	}
	public Spinner<Integer> getInput_pokedex_n() {
		return input_pokedex_n;
	}
	public EnumerationComboBoxImpl<Tipo> getInput_tipo_1() {
		return input_tipo_1;
	}
	public EnumerationComboBoxImpl<Tipo> getInput_tipo_2() {
		return input_tipo_2;
	}
	public Spinner<Integer> getInput_ATK() {
		return input_ATK;
	}
	public Spinner<Integer> getInput_DEF() {
		return input_DEF;
	}
	public Spinner<Integer> getInput_SPA() {
		return input_SPA;
	}
	public Spinner<Integer> getInput_SPD() {
		return input_SPD;
	}
	public Spinner<Integer> getInput_SPE() {
		return input_SPE;
	}
	public Spinner<Integer> getInput_HP() {
		return input_HP;
	}
	
	
	
}
