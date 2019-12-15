package com.pokemon.pokemones.core;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.component.controller.AbstractController;
import com.pokemon.pokemones.core.dialog.controller.AbstractDialogController;
import com.pokemon.pokemones.core.scopes.ViewCache;
import com.pokemon.pokemones.core.services.AspectPropertyService;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import static com.pokemon.pokemones.core.util.CustomReflectionUtils.getAllFields;

@Service
public class ComponentLoader {
	
	private final Logger LOG;
	
	private final ApplicationContext ctx;
	
	private AspectPropertyService aspectPropertyService;
	
	public @Autowired void setAspectPropertyService(final AspectPropertyService aspectPropertyService){
		this.aspectPropertyService = aspectPropertyService;
	}
	
	private final ViewCache cache;
	
	private final String fx_prefix;
	private final String fx_suffix;
	
	private final String fx_css_prefix;
	private final String fx_css_suffix;	
	
	public @Autowired ComponentLoader(ApplicationContext ctx, ViewCache cache,
			@Value("${fx.view.prefix}")String fx_prefix,
			@Value("${fx.view.suffix}")String fx_suffix,
			@Value("${fx.css.prefix}")String fx_css_prefix, 
			@Value("${fx.css.suffix}")String fx_css_suffix)	{
		super();
		this.LOG=LoggerFactory.getLogger(ComponentLoader.class);

		this.ctx=ctx;
		this.cache = cache;
		
		this.fx_prefix=fx_prefix;
		this.fx_suffix=fx_suffix;
		this.fx_css_prefix=fx_css_prefix;
		this.fx_css_suffix=fx_css_suffix;
	}

	public Component load(final String name, final Map<String,Object> params) throws ComponentLoadException, IOException {
		final Component res;		
		if(cache.contains(name)) {
			res = cache.get(name);
		}else {
			res=new Component();
			res.setController(load(name,res.getView(),params));
		}		
		return res;
	}
	
	public AbstractController load(final String name, final View res) throws ComponentLoadException, IOException{
		return load(name, res, new HashMap<String,Object>());
	}	
	
	public AbstractController load(final String name, final View res, final Map<String,Object> params) throws ComponentLoadException, IOException{
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
			System.out.println(res);
			loader.setRoot(res);
			
			loader.load();
			
			/*inyecto componentes de la vista en el presentador, los argumentos, el contralodor seencarga de gestionar su estado */
			injectFieldsIntoPresenter(loader.getNamespace(),((AbstractController)loader.getController()).getPresenter());
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
			/* default background */
			if(res.getContent()==null || res.getContent().getBackground()==null || res.getContent().getBackground().equals(Background.EMPTY)) {
				final Color c = aspectPropertyService.getDefaultBackgroundColor();	
				res.getContent().setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, res.getContent().getInsets())));
				LOG.info("overriding with default background color "+c.toString());
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
			LOG.error("la definicion del componente no tiene el formato correcto",e1);
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
	
//	private void injectFieldsIntoPresenter(Map<String, Object> namespace, Object presenter) {
//		if(presenter == null) {
//			LOG.info("no presenter found");
//			return;
//		}
//		if(namespace == null) {
//			LOG.error("no items where found to inject");
//			return;
//		}
//		
//		for (Field field : getAllFields(presenter.getClass())) {
//	        final boolean wasAccessible = field.isAccessible() ;
//	        field.setAccessible(true);
//			if (namespace.containsKey(field.getName())) {
//				if (field.getType().isInstance(namespace.get(field.getName()))){					
//					try {
//						LOG.debug("inyectando campo "+field.getName());
//						field.set(presenter, namespace.get(field.getName()));
//					} catch (IllegalArgumentException | IllegalAccessException e) {
//						LOG.error("error injectando componente con nombre "+field.getName());
//					}
//				}else{
//					LOG.error("error injectando componente con nombre "+field.getName()+":el tipo es ["+
//						namespace.get(field.getName()).getClass().getName()+"], pero se encontro ["+
//						field.getType().getClass().getName()+"]");
//				}
//			}else{
//				LOG.info("no se ha encontrado valor para injectar en el atributo "+field.getName());
//			}
//	        field.setAccessible(wasAccessible);
//	    }
//		
//		LOG.info("fields injected into presenter");
//	}
	
	
	private final static String[] NS_K2IGNORE = {"controller","location","resources"};
	
	private void injectFieldsIntoPresenter(final Map<String, Object> ns, final Presenter presenter) {
		if(presenter == null) {
			LOG.info("no presenter found");
			return;
		}
		if(ns == null) {
			LOG.error("no items where found to inject");
			return;
		}
		
		ns.keySet().stream()
			.filter(k -> !Arrays.asList(NS_K2IGNORE).contains(k))
			.forEach(k -> presenter.put(k, ns.get(k)));		
		
		
		LOG.info("fields injected into presenter");
	}
	
}
