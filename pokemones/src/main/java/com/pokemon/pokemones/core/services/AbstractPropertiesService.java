package com.pokemon.pokemones.core.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

public class AbstractPropertiesService {
	
	private final Properties properties;
	
	public AbstractPropertiesService() {
		this.properties=new Properties();
		try {
			final File f = new File("");
			if(!f.exists()) {
				f.createNewFile();
			}
			properties.load(new FileReader(f));
		}catch (IOException e) {
			// TODO: handle exception
		}
	}
	
}
