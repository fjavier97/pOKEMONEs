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

import javafx.scene.control.Tooltip;

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
	
	private final static Opcion[] opciones = 
		{new Opcion(String.class,"setText"), new Opcion(String.class,"setPromptText"), new Opcion(Tooltip.class,"setToolTip")};
	
	private static class Opcion{
		
		private final Class<?> contentClass;
		private final String methodName;
		
		public Opcion(Class<?> contentClass, String methodName) {
			super();
			this.contentClass = contentClass;
			this.methodName = methodName;
		}

		public Class<?> getContentClass() {
			return contentClass;
		}

		public String getMethodName() {
			return methodName;
		}
		
		
		
	}

	public void refreshView() {
		refreshLabels();
		refreshData();
	}
	
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
				for(Opcion opt: opciones){
					try{	
						final Class<?> c = opt.getContentClass();// tipo de la etiqueta que estoy pasando
						final Object m_arg = c.equals(String.class)?b.getString(k):c.getConstructor(String.class).newInstance(b.getString(k));// nueva etiqueta
						//llamo al metodo con la nueva etiqueta. si no es string, creo un objeto utilizando el constructor con string como pareametro.
						o.getClass().getMethod(opt.getMethodName(),c).invoke(o, m_arg);
						break;
					} catch(NoSuchMethodException me) {
						continue;
					} catch(MissingResourceException mre) {
						LOG.error("el campo "+f.getName()+" no esta localizado");
						continue;
					} catch (InvocationTargetException | SecurityException | InstantiationException e) {
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
	
	/* FLAG COMPONENTE ACTIVO ******************************************************************* */
	
	private boolean activo;	
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	/* NOMBRE *********************************************************************************** */
	
	public String getComponentName(){
		Class<?> c = getClass();
		if(c.isAnnotationPresent(Component.class)){
			return c.getAnnotation(Component.class).value();
		}else{
			return c.getSimpleName();
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
