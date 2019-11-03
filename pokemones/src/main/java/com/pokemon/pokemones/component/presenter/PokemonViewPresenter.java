package com.pokemon.pokemones.component.presenter;

import customfx.scene.chart.StarChart;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PokemonViewPresenter {

	private @FXML Text nombre;
	
	private @FXML StarChart grafico;

	public Text getNombre() {
		return nombre;
	}

	public StarChart getGrafico() {
		return grafico;
	}
	
	
	
}
