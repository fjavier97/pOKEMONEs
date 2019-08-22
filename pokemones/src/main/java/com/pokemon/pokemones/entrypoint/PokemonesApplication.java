package com.pokemon.pokemones.entrypoint;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pokemon.pokemones.core.event.StartEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

@EnableJpaRepositories(basePackages={"com.pokemon.pokemones"})
@EntityScan("com.pokemon.pokemones")
@SpringBootApplication(scanBasePackages={"com.pokemon.pokemones"})
public class PokemonesApplication extends Application{

	private ConfigurableApplicationContext context;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		context = SpringApplication.run(PokemonesApplication.class, getParameters().getRaw().toArray(new String[0]));	
	}
	
	@Override
	public void stop() throws Exception {
		context.close();		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		context.publishEvent(new StartEvent(primaryStage));
	}

}
