package com.pokemon.pokemones.core.component.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.ComponentManager;

import com.pokemon.pokemones.core.services.LocalizationService;

public abstract class AbstractController<C> {	
	
	/* PRESENTADOR ****************************************************************************** */
	private C presenter;
	
	public C getPresenter() {
		return presenter;
	}

	private void setPresenter(C presenter) {
		this.presenter = presenter;
	}
	
	protected abstract C initPresenter();

	/* MANAGER ********************************************************************************** */
	protected ComponentManager manager;
	
	public @Autowired void setManager(final ComponentManager manager) {
		this.manager = manager;
	}
	
	/* PUBLISHER ******************************************************************************** */
	protected ApplicationEventPublisher publisher;
	
	public @Autowired void setPublisher(final ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}	
	
	/* LOCALIZACION ***************************************************************************** */
	protected LocalizationService localizationService;
	
	public @Autowired void setLocalizationService(final LocalizationService localizationService) {
		this.localizationService = localizationService;
	}
	
	private final static String[] opciones = {"setText","setPromptText"/*setToolTip*/};

	public void refreshLabels(){
		if(getPresenter()==null){
			LOG.info("no presenter found to update");
			return;
		}
		final ResourceBundle b = localizationService.getResourceFor(getClass().getAnnotation(Component.class).value());
			if(b==null){
			return;
		}
	    for (Field f : getPresenter().getClass().getDeclaredFields()) {
	    	final boolean wasaccesible = f.isAccessible();
	    	f.setAccessible(true);
			try {				
				final Object o = f.get(getPresenter());
				
				if(o==null) {
					continue;
				}
		
				final String k = o.getClass().getSimpleName()+"."+f.getName();
				for(String opt: opciones){
					try{			
						o.getClass().getMethod(opt,String.class).invoke(o, b.getString(k));
						break;
					} catch(NoSuchMethodException me) {
						continue;
					} catch(MissingResourceException mre) {
						LOG.error("el campo "+f.getName()+" no esta localizado");
						continue;
					} catch (InvocationTargetException | SecurityException e) {
						LOG.error("error inyectando valor:"+e.getMessage());
						continue;
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LOG.error("error accediendo al campo:"+e.getMessage());
			}
			f.setAccessible(wasaccesible);
	    }
	}
	
	/* LOG ************************************************************************************** */
	protected final Logger LOG;
	
	protected AbstractController() {
		LOG = LoggerFactory.getLogger(getClass());
		setPresenter(initPresenter());
	}

	public abstract void handleParams(final Map<String, Object> args);
	
	public abstract void refreshData();
		
}
