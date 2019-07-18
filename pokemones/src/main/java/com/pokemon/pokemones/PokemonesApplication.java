package com.pokemon.pokemones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
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
