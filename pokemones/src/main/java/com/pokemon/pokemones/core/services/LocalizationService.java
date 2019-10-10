package com.pokemon.pokemones.core.services;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.event.LanguajeChangeEvent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

@Service
public class LocalizationService {

	private ObjectProperty<Locale> languaje;
	
	private ApplicationEventPublisher publisher;
	
	public @Autowired LocalizationService(ApplicationEventPublisher publisher){
		this.publisher = publisher;
		languaje = new SimpleObjectProperty<Locale>(new Locale("es"));
		languaje.addListener((o,a,n)->{
			publisher.publishEvent(new LanguajeChangeEvent());
		});
	}

	public Locale getLanguaje() {
		return languaje.get();
	}
	
	public void changeLanguaje(String languaje) {
		this.languaje.set(new Locale(languaje));
	}
	
	public ResourceBundle getResourceFor(final String component) {
		try {
			final String path = "localization/"+component+"/label";
			final ResourceBundle bundle = ResourceBundle.getBundle(path, getLanguaje());
			return bundle;
		}catch (MissingResourceException e) {
			return null;
		}
	}
}
