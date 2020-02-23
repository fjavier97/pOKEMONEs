package com.pokemon.pokemones.customcomfx;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@Component
@Scope("prototype")
public class AbstractNotificationCellController {

	private @FXML Button closeButton;	
	
	private @FXML Pane colorPane;	
	
	private @FXML Label texto;
	
	public void setCloseOperation(final EventHandler<ActionEvent> eh) {
		if(closeButton!=null && closeButton.getOnAction()==null) {
			closeButton.setOnAction(eh);
		}
	}
	
	public void setColor(final Color color) {
		this.colorPane.setBackground(new Background(new BackgroundFill(color,CornerRadii.EMPTY,Insets.EMPTY)));
	}
	
	public void setText(final String text) {
		this.texto.setText(text);
	}
}