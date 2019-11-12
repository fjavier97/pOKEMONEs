package com.pokemon.pokemones.core.component.presenter;

import com.pokemon.pokemones.core.item.dto.RoleDPO;

import customfx.scene.control.MultiSelectionComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class UserPresenter {

	private TextField input_nombre;
	private PasswordField input_pwd;
	private MultiSelectionComboBox<RoleDPO> input_roles;
	private ListView<RoleDPO> selected_roles;
	private CheckBox input_enabled;
	
	private PasswordField input_pwd1;
	private PasswordField input_pwd2;
	
	private Tab tab_objeto;
	private Tab tab_pwd;
	
	private Button button_nuevo;
	private Button button_atras;
	private Button button_guardar;
	private Button button_chang_pwd;
	private Button button_recargar;
	
	public TextField getInput_nombre() {
		return input_nombre;
	}
	public PasswordField getInput_pwd() {
		return input_pwd;
	}
	public MultiSelectionComboBox<RoleDPO> getInput_roles() {
		return input_roles;
	}
	public ListView<RoleDPO> getSelected_roles() {
		return selected_roles;
	}
	public CheckBox getInput_enabled() {
		return input_enabled;
	}
	public PasswordField getInput_pwd1() {
		return input_pwd1;
	}
	public PasswordField getInput_pwd2() {
		return input_pwd2;
	}
	public Tab getTab_objeto() {
		return tab_objeto;
	}
	public Tab getTab_pwd() {
		return tab_pwd;
	}
	public Button getButton_nuevo() {
		return button_nuevo;
	}
	public Button getButton_atras() {
		return button_atras;
	}
	public Button getButton_guardar() {
		return button_guardar;
	}
	public Button getButton_chang_pwd() {
		return button_chang_pwd;
	}
	public Button getButton_recargar() {
		return button_recargar;
	}
	
	
}
