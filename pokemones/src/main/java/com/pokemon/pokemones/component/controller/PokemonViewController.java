package com.pokemon.pokemones.component.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.pokemon.pokemones.core.Navigation;
import com.pokemon.pokemones.core.component.controller.AbstractController;
import com.pokemon.pokemones.core.event.ComponentChangeRequestEvent;
import com.pokemon.pokemones.item.dto.PokemonDTO;

import customfx.scene.chart.StarChart;
import customfx.scene.chart.StarChart.Data;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

@Component("PokemonView")
@Scope("ComponentScope")
public class PokemonViewController extends AbstractController {

	
	final private ObjectProperty<PokemonDTO> model;

	public @Autowired PokemonViewController() {
		model = new SimpleObjectProperty<PokemonDTO>(null);
		model.addListener((o, v, n) -> {
			
			getPresenter().get("nombre",Text.class).setText(n.getNombre());
			
			getPresenter().get("grafico",StarChart.class).getData().clear();
			getPresenter().get("grafico",StarChart.class).getData().add(new Data((double) n.getBaseHP(), "HP"));
			getPresenter().get("grafico",StarChart.class).getData().add(new Data((double) n.getBaseATK(), "ATK"));
			getPresenter().get("grafico",StarChart.class).getData().add(new Data((double) n.getBaseSPA(), "SPA"));
			getPresenter().get("grafico",StarChart.class).getData().add(new Data((double) n.getBaseSPE(), "SPE"));
			getPresenter().get("grafico",StarChart.class).getData().add(new Data((double) n.getBaseDEF(), "DEF"));
			getPresenter().get("grafico",StarChart.class).getData().add(new Data((double) n.getBaseSPD(), "SPD"));
		});
	}

	private @FXML void atras() {
		final ComponentChangeRequestEvent evt = new ComponentChangeRequestEvent("PokemonList", Navigation.BACKWARD);
		publisher.publishEvent(evt);
	}

	@Override
	public void handleParams(Map<String, Object> args) {
		
		if (model.get() == null && args.containsKey("item")) {
			model.set((PokemonDTO) args.get("item"));
		}

//		grafico.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0))));

	}

	public @Override void refreshData() {
	}

}
