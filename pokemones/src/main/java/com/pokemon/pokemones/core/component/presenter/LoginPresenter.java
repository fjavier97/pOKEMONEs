package com.pokemon.pokemones.core.component.presenter;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginPresenter {

 	private @FXML TextField usrfield;
 	private @FXML PasswordField pwdfield;
 	
 	private @FXML Text notifiaction;

	public TextField getUsrfield() {
		return usrfield;
	}

	public PasswordField getPwdfield() {
		return pwdfield;
	}

	public Text getNotifiaction() {
		return notifiaction;
	}
 	
 	
	
}
