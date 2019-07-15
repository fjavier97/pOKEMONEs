package com.pokemon.pokemones;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import recursos.config.Componente;
import recursos.config.DesktopXmlConfig;

@Component
public class ComponentLoader {

	private Stage stage;
	
	private final ApplicationContext ctx;
	private final Logger logger;
	
	private final String fx_prefix;
	private final String fx_suffix;
	
	private final String fx_css_prefix;
	private final String fx_css_suffix;

	
	@Autowired
	public ComponentLoader(final ApplicationContext ctx,
			@Value("${fx.view.prefix}")String tpre,
			@Value("${fx.view.suffix}")String tsu,
			@Value("${fx.css.prefix}")String cpre, 
			@Value("${fx.css.suffix}")String csu){
		this.logger=LoggerFactory.getLogger(ComponentLoader.class);
		this.ctx=ctx;
		//TODO cambiar esto
		this.fx_prefix=tpre;
		this.fx_suffix=tsu;
		this.fx_css_prefix=cpre;
		this.fx_css_suffix=csu;
	}
		
	@EventListener
	public void onLoadEvent(LoadEvent evt) throws IOException {
		this.stage=evt.getStage();
		if(this.loadCore()) {
			//this.loadComponents();
		}
	}
	
	public boolean loadCore(){
		if(stage==null) {
			logger.error("error cargando componente principal, la ventana no existe");
			return false;
		}
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fx_prefix+"Core"+fx_suffix));
			loader.setControllerFactory(ctx::getBean);
			stage.setScene(new Scene(loader.load()));
			stage.show();
			return true;
		}catch (Exception e) {
			logger.error("error cargando componente principal");
			return false;
		}
	}
	
	public void loadComponents() {
		DesktopXmlConfig desktopconfig=null;
		
		try {
			final JAXBContext jc = JAXBContext.newInstance(DesktopXmlConfig.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();		
			desktopconfig = (DesktopXmlConfig) unmarshaller.unmarshal(getClass().getResourceAsStream("/desktop-config.xml"));
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("error cargando componentes");
			Platform.exit();
		}
		
		for(Componente c : desktopconfig.getComponentes()){
			register(c);
		}
	}
		
	private void register(final Componente componente) {
		final String nombre = componente.getNombre();
		final String templatepath = componente.getComponent();
		final String fulltemplatepath = fx_prefix+"template"+fx_suffix;
		
		
		final URL templateurl = getClass().getResource(fulltemplatepath);
		if(templateurl==null) {
			logger.error("error registrando componente "+nombre+", no se ha encontrado clase a cargar "+fulltemplatepath);
		}
		
		MenuItem menuitem = new MenuItem(nombre);
		
		menuitem.setOnAction(e->{	
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(templateurl);
			loader.setControllerFactory(ctx::getBean);
		});	
		
				
			
			
		
		
	}
}






