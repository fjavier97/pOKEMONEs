package com.pokemon.pokemones.core.event;

public class NotificationEvent {

	public static enum Threat{
		INFO, SUCCESS, WARNING, ERROR
	}
	
	private String message;
	private Threat threat;
	
	public NotificationEvent(String message, Threat threat) {
		super();
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

	public Threat getThreat() {
		return threat;
	}

	public void setThreat(Threat threat) {
		this.threat = threat;
	}
	
	
	
}
