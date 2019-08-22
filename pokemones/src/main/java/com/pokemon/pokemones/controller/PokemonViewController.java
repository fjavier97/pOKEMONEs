package com.pokemon.pokemones.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.controller.AbstractController;
import com.pokemon.pokemones.core.event.ComponenteChangeRequestEvent;
import com.pokemon.pokemones.item.dto.PokemonDTO;

import customfx.scene.chart.StarChart;
import customfx.scene.chart.StarChart.Data;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

@Component("PokemonView")
@Scope("ComponentScope")
public class PokemonViewController extends AbstractController {

	private @FXML StarChart grafico;
	final private ObjectProperty<PokemonDTO> model;
	
	public @Autowired PokemonViewController(){
		model = new SimpleObjectProperty<PokemonDTO>(null);
		model.addListener((o,v,n)->{
			grafico.getData().add(new Data((double)n.getBase_HP(),"HP"));
			grafico.getData().add(new Data((double)n.getBase_ATK(),"ATK"));
			grafico.getData().add(new Data((double)n.getBase_SPA(),"SPA"));
			grafico.getData().add(new Data((double)n.getBase_SPE(),"SPE"));
			grafico.getData().add(new Data((double)n.getBase_DEF(),"DEF"));
			grafico.getData().add(new Data((double)n.getBase_SPD(),"SPD"));

		});
	}
	
	private @FXML void atras() {
		final ComponenteChangeRequestEvent evt = new ComponenteChangeRequestEvent("PokemonList", Navigation.BACKWARD);
		publisher.publishEvent(evt);
	}
	
	@Override
	public void injectArguments(Map<String, Object> args) {
			if(model.get() == null && args.containsKey("item")) {
				model.set((PokemonDTO) args.get("item"));
			}
			
//		grafico.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0))));

	}

}
