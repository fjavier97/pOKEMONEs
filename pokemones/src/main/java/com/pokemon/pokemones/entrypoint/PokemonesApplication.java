package com.pokemon.pokemones.entrypoint;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.pokemon.pokemones.core.event.StartEvent;
import com.pokemon.pokemones.core.job.JobPerformable;
import com.pokemon.pokemones.core.job.ScanJobs;
import com.pokemon.pokemones.core.scopes.ComponentScope;
import com.pokemon.pokemones.core.security.MethodSecurityConfig;

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
	
	@Override
	public void init() throws Exception {
		final Class<?>[] clases = {MethodSecurityConfig.class,PokemonesApplication.class};
		context = SpringApplication.run(clases, getParameters().getRaw().toArray(new String[0]));	

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
    	System.out.println("creo comp scanner");
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(JobPerformable.class));
        return provider;
    }    
	
    /* configuracion del scope */
	public @Bean static CustomScopeConfigurer customScopeConfigurer(final ComponentScope scope)
	{
		System.out.println("creo bean scope");
		final CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.addScope("ComponentScope", scope);
		return configurer;
	}

	
//	@Bean(name="dataSource")
//	public DataSource getDataSource(	@Value("com.mysql.jdbc.Driver")final String driver,
//										@Value("${spring.datasource.url}")final String url,
//										@Value("${spring.datasource.username}")final String usr,
//										@Value("${spring.datasource.password}")final String pwd) {
//
////		 dataSourceBuilder.driverClassName(driver);
//	    ;
//    	return  DataSourceBuilder.create().url(url).username(usr).password(pwd).build();
//	}

}
