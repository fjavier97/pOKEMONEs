package com.pokemon.pokemones.core;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.controller.component.AbstractController;
import com.pokemon.pokemones.core.controller.dialog.AbstractDialogController;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.paint.Color;


@Service
public class ComponentLoader {
	
	private final Logger LOG;
	
	private final ApplicationContext ctx;
	
	private final String fx_prefix;
	private final String fx_suffix;
	
	private final String fx_css_prefix;
	private final String fx_css_suffix;	
	
	public @Autowired ComponentLoader(ApplicationContext ctx,
			@Value("${fx.view.prefix}")String fx_prefix,
			@Value("${fx.view.suffix}")String fx_suffix,
			@Value("${fx.css.prefix}")String fx_css_prefix, 
			@Value("${fx.css.suffix}")String fx_css_suffix)	{
		super();
		this.LOG=LoggerFactory.getLogger(ComponentLoader.class);

		this.ctx=ctx;
		
		this.fx_prefix=fx_prefix;
		this.fx_suffix=fx_suffix;
		this.fx_css_prefix=fx_css_prefix;
		this.fx_css_suffix=fx_css_suffix;
	}

	public AbstractController load(final String name, final Componente res) throws ComponentLoadException, IOException{
		return load(name, res, new HashMap<String,Object>());
	}	
	
	public AbstractController load(final String name, final Componente res, final Map<String,Object> params) throws ComponentLoadException, IOException{
		final String fulltemplatepath = fx_prefix+name+fx_suffix;
		final String csspath = fx_css_prefix+name+fx_css_suffix;	
		
		
		FXMLLoader loader = new FXMLLoader();
		
		/* configuro la vista */
		final URL templateurl = getClass().getResource(fulltemplatepath);
		
		if(templateurl==null) {
			LOG.error("error registrando componente "+name+", no se ha encontrado clase a cargar "+fulltemplatepath);
			throw new ComponentLoadException(name, "no se ha encontrado el fichero de definicion del componenet");
		}else {
			loader.setLocation(templateurl);
		}		
		
		LOG.info(templateurl.toString());
		
		/* configuro controlador */
		loader.setControllerFactory(ctx::getBean);
		
		/* cargo el componente y añado la hoja de css */
		try {
			
			loader.setRoot(res);
			
			loader.load();
			
			/*inyecto los argumentos, el contralodor seencarga de gestionar su estado */
			((AbstractController)loader.getController()).handleParams(params);
			((AbstractController)loader.getController()).refreshLabels();
			
			/* estilos */
			final URL cssurl = getClass().getResource(csspath);
			if(cssurl==null){
				LOG.info("no se ha encontrado hoja de estilos asociada con el componente"+name+"");
			}else {
				if(res.getContent()!=null) {
					res.getContent().getStylesheets().add(csspath);
				}
			}
			/* TODO comprobar esto mejor */
			try {
				if(res.getContent().getBackground().getFills().get(0).getFill().equals(Color.TRANSPARENT)) {
					res.getContent().setStyle("-fx-background-color: gray");
				}
			}
			catch (NullPointerException ne) {
				res.getContent().setStyle("-fx-background-color: gray");
			}		
			
			/* para debug imprimimos estado del componente */
			LOG.debug(res.toString());
			if(res.getContent()!=null)
				LOG.debug(res.getContent().toString());
			if(res.getMenus()!=null)
				LOG.debug(res.getMenus().toString());
						
			/* devuelvo el controlador */
			return loader.getController();
			
		}catch (ClassCastException e1) {
			e1.printStackTrace();
			throw new ComponentLoadException(name, "la definicion del componente no tiene el formato correcto");
		}catch (Exception e) {
			throw e;
		}			
	}
	
	public  AbstractDialogController<?> loadDialog(final String name, final DialogPane dp) {
		final String fulltemplatepath = fx_prefix+"/dialog/"+name+fx_suffix;		
		
		FXMLLoader loader = new FXMLLoader();
		
		/* configuro la vista */
		final URL templateurl = getClass().getResource(fulltemplatepath);
		
		if(templateurl==null) {
			LOG.error("error registrando componente "+name+", no se ha encontrado clase a cargar "+fulltemplatepath);
		}else {
			loader.setLocation(templateurl);
		}		
		
		LOG.info(templateurl.toString());
		
		/* configuro controlador */
		loader.setControllerFactory(ctx::getBean);
				
		/* cargo el componente y añado la hoja de css */
		try {
			
			loader.setRoot(dp);
			
			loader.load();
			
			((AbstractDialogController<?>)loader.getController()).setOwner(dp.getScene().getWindow());	
			
			return loader.getController();
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}			
	}
}
