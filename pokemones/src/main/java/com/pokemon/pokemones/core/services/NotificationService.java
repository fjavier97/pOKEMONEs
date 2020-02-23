package com.pokemon.pokemones.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.event.NotificationEvent;
import com.pokemon.pokemones.core.item.NotificationInfo;
import com.pokemon.pokemones.core.repository.NotificationColorRepository;

import javafx.scene.paint.Color;
import javafx.util.Duration;

@Service
public class NotificationService {
	
	private final NotificationColorRepository colorRepository;

	public @Autowired NotificationService (final NotificationColorRepository colorRepository){
		this.colorRepository = colorRepository;
	}
	
	public NotificationInfo getNotificationInfo(final NotificationEvent evt) {
		final String t = evt.getThreat();
		return new NotificationInfo(getColorFromThreat(t), getDurationFromThreat(t) , evt.getMessage());
	}
	
	private Color getColorFromThreat(final String threat) {
		return colorRepository.get(threat);
	}
	
	private Duration getDurationFromThreat(final String threat) {
		return Duration.seconds(7);
	}
	
}
