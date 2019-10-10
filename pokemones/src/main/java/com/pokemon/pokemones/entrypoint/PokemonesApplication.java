package com.pokemon.pokemones.entrypoint;

import java.util.Map;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pokemon.pokemones.core.event.StartEvent;
import com.pokemon.pokemones.core.job.JobPerformable;
import com.pokemon.pokemones.core.job.ScanJobs;
import com.pokemon.pokemones.core.scopes.ComponentScope;

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
	
	/* CONFIGURACION */
	
	
	/* configuracion de jobs */
    public @Bean ClassPathScanningCandidateComponentProvider createComponentScanner() {
        // Don't pull default filters (@Component, etc.):
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(JobPerformable.class));
        return provider;
    }    
	
    /* configuracion del scope */
	public @Bean static CustomScopeConfigurer customScopeConfigurer(final ComponentScope scope)
	{
		final CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.addScope("ComponentScope", scope);
		return configurer;
	}

}