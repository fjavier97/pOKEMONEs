package com.pokemon.pokemones.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.pokemon.pokemones.core.dialog.controller.AbstractDialogController;
import com.pokemon.pokemones.core.item.NotificationInfo;
import com.pokemon.pokemones.customcomfx.AbstractNotificationCellController;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import org.springframework.stereotype.Component;

@Component
public class FragmentLoader extends Loader{

	private final Logger LOG;

	public FragmentLoader(ApplicationContext ctx,
			@Value("${fx.view.prefix}")String fx_prefix,
			@Value("${fx.view.suffix}")String fx_suffix,
			@Value("${fx.css.prefix}")String fx_css_prefix, 
			@Value("${fx.css.suffix}")String fx_css_suffix)	{
		super(ctx, fx_prefix, fx_suffix, fx_css_prefix, fx_css_suffix);
		// TODO Auto-generated constructor stub
		
		this.LOG=LoggerFactory.getLogger(FragmentLoader.class);
	}


	public Object loadFragment(final String name, final String location, final Pane v) throws LoadException {
		final String fulltemplatepath = fx_prefix+(location==null?"":location+"/")+name+fx_suffix;		
	
		FXMLLoader loader = new FXMLLoader();
		
		/* configuro la vista */
		final InputStream input = getClass().getResourceAsStream(fulltemplatepath);
		
		if(input==null) {
			LOG.error("error registrando componente "+name+", no se ha encontrado plantilla a cargar "+fulltemplatepath);
			throw new LoadException("error resolving template for fragment "+name);
		}	
				
		/* cargo el componente y añado la hoja de css */
		try {	
			
			/* configuro controlador */
			loader.setControllerFactory(ctx::getBean);
			loader.setRoot(v);
			
			loader.load(input);
			
			if(loader.getController()==null) {
				throw new LoadException("error resolving controller for fragment "+name);
			}
						
			return loader.getController();
		}catch (IOException e1) {
			throw new LoadException("error loading fragment "+name,e1);
		}catch (NullPointerException e2) {
			throw new LoadException("error resolving template for fragment "+name,e2);
		}
	}
	
	public AbstractDialogController<?> loadDialog(final String name, final DialogPane v) throws LoadException{
		try {
			final AbstractDialogController<?> controller = (AbstractDialogController<?>)loadFragment(name, "dialog", v);
			controller.setOwner(v.getScene().getWindow());
			return controller;
		}catch (ClassCastException e) {
			throw new LoadException("error creating controller for "+name);
		}			
	}
	
	public AbstractNotificationCellController loadNotification(final NotificationInfo n, final GridPane v, final List<NotificationInfo> list) throws LoadException{
		try {
			final AbstractNotificationCellController controller = (AbstractNotificationCellController)loadFragment("empty", "notification", v);			System.err.println(controller==null);
			controller.setCloseOperation(e->list.remove(n));
			controller.setColor(n.getColor());
			controller.setText(n.getMessage());
			return controller;
		}catch (ClassCastException e) {
			throw new LoadException("error creating controller for "+n.getMessage());
		}			
	}
	
	
}
