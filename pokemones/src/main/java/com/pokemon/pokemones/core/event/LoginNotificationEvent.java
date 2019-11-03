package com.pokemon.pokemones.core.event;

public class LoginNotificationEvent {

	private boolean login;
	
	private String usr;
	
	public LoginNotificationEvent() {
		super();
		this.login = false;
		this.usr = null;
	}
	
	public LoginNotificationEvent(boolean login) {
		super();
		this.login = login;
		this.usr = null;
	}

	public LoginNotificationEvent(boolean login, String usr) {
		super();
		this.login = login;
		this.usr = usr;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}
	
	
}
