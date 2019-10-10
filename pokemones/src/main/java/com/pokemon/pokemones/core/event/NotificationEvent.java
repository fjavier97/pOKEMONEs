package com.pokemon.pokemones.core.event;

import javafx.scene.layout.Region;

public class NotificationEvent {

	public static enum Threat{
		INFO, SUCCESS, WARNING, ERROR
	}
	
	private String message;
	private Region node;
	private Threat threat;
	
	public NotificationEvent(String message, Threat threat) {
		super();
		this.node=null;
		this.message = message;
		this.threat = threat;
	}
	
	public NotificationEvent(String message, Region node, Threat threat) {
		super();
		this.node = node;
		this.message = message;
		this.threat = threat;
	}
	
	public NotificationEvent() {
		super();
		this.message = null;
		this.threat = Threat.INFO;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Region getNode() {
		return node;
	}

	public void seNode(Region node) {
		this.node = node;
	}

	public Threat getThreat() {
		return threat;
	}

	public void setThreat(Threat threat) {
		this.threat = threat;
	}
	
	
	
}
