package com.pokemon.pokemones.core.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PWDEncoder implements PasswordEncoder {

	private final static String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

	public String getRandomPassword() {
		return null;
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		// TODO Auto-generated method stub
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// TODO Auto-generated method stub
		return rawPassword.equals(encodedPassword);
	}		
	
}
