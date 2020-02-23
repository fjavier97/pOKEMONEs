package com.pokemon.pokemones.core.item;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public class NotificationInfo{
	
	private final Color color;
	
	private final Duration timeout;
	
	private final String message;
	
	private String description;
	
	private String link;

	public NotificationInfo(Color color, Duration timeout, String message) {
		super();
		this.color = color;
		this.timeout = timeout;
		this.message = message;
		this.description = null;
		this.link = null;
	}

	public Color getColor() {
		return color;
	}

	public Duration getTimeout() {
		return timeout;
	}

	public String getMessage() {
		return message;
	}
	
	
	
	public NotificationInfo withDescription(final String description){
		this.description = description;
		return this;
	}
	
	public NotificationInfo withLinkTo(final String link){
		this.link = link;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public String getLink() {
		return link;
	}
	
	public boolean hasDescription() {
		return this.description==null;
	}
	
	public boolean hasLink() {
		return this.link==null;
	}
	
}