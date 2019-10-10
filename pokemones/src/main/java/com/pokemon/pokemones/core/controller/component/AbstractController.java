package com.pokemon.pokemones.core.controller.component;

import java.lang.reflect.Field;

import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.ComponentManager;

import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;

import com.pokemon.pokemones.core.locals.Lacalized;
import com.pokemon.pokemones.core.services.LocalizationService;

public abstract class AbstractController {
	
	/* referencia al component manager */
	protected ComponentManager manager;
	
	public @Autowired void setManager(final ComponentManager manager) {
		this.manager = manager;
	}
	
	/* publicador de eventos */
	protected ApplicationEventPublisher publisher;
	
	public @Autowired void setPublisher(final ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}	
	
	/* servicio de localizacion */
	protected LocalizationService localization_service;
	
	public @Autowired void setLocalizationService(final LocalizationService localization_service) {
		this.localization_service = localization_service;
	}
	
	/* log */
	protected final Logger LOG;
	
	protected AbstractController() {
		LOG = LoggerFactory.getLogger(getClass());
	}

	public abstract void handleParams(final Map<String, Object> args);
	
	public abstract void refreshData();
	
	public void refreshLabels() {
		for (Field f :getClass().getDeclaredFields()) {
			if(f.isAnnotationPresent(Lacalized.class)) {
				try {
					f.setAccessible(true);
					final Object o = f.get(this);
					
					final String key = o.getClass().getSimpleName()+"."+f.getName();
					
					final ResourceBundle bundle = localization_service.getResourceFor(getClass().getAnnotation(Component.class).value());
					if(bundle==null) {
						LOG.warn("no resource boundel defined for component ["+getClass().getAnnotation(Component.class).value()+"] ");
						return;
					}
					if(o instanceof TableColumn) {
						((TableColumn<?,?>) o).setText(bundle.getString(key));
					}
					else if(o instanceof Labeled) {
						((Labeled) o).setText(bundle.getString(key));
					}
					else if(o instanceof MenuItem) {
						((MenuItem) o).setText(bundle.getString(key));
					}
				} catch (Exception e) {
					LOG.error("could not set value for label ["+getClass().getAnnotation(Component.class).value()+" - "+f.getName()+"] due to access restrictions");
				}
			}
		}
	}

}
