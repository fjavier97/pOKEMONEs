package com.pokemon.pokemones.core.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javafx.scene.paint.Color;

@Repository
public class NotificationColorRepository {
	
	private Logger LOG = LoggerFactory.getLogger(NotificationColorRepository.class);
	private final Map<String, Color> memoryRepo;
	
	public NotificationColorRepository() {
		memoryRepo = new HashMap<String, Color>();
		load();
	}
	
	private String getPropertyFileName() {
		return "Threat2Color.properties";
	}
	
	private void load() {
		memoryRepo.clear();
		final Properties p = new Properties();
		final URL loc = getClass().getResource("/"+getPropertyFileName());
		if(loc==null){
			LOG.info("property file "+getPropertyFileName()+" does not exist");
			if(true){
				try {
					new File(new File(getClass().getResource("/").toURI()),File.separator+getPropertyFileName()).createNewFile();
					LOG.info("file created");
				} catch (IOException | URISyntaxException e) {
					LOG.error("could not create property file ["+getPropertyFileName()+"]",e);
				}
			}
		}else {
			System.err.println("-------------------existe prop de color");
		}
		try(final InputStream is = getClass().getResourceAsStream("/"+getPropertyFileName())){
			p.load(is);
			p.entrySet().stream().forEach(entry -> memoryRepo.put(entry.getKey().toString(), Color.web(entry.getValue().toString())));
			if(!memoryRepo.containsKey("SUCCESS")) {
				memoryRepo.put("SUCCESS",Color.DARKGREEN);
			}
			if(!memoryRepo.containsKey("INFO")) {
				memoryRepo.put("INFO",Color.DARKBLUE);
			}
			if(!memoryRepo.containsKey("ERROR")) {
				memoryRepo.put("ERROR",Color.RED);
			}
			if(!memoryRepo.containsKey("WARNING")) {
				memoryRepo.put("WARNING",Color.YELLOW);
			}
			LOG.info("properties loaded");
			System.err.println("-------------------colors loaded");
		}catch(IOException ioe){
			LOG.error("could not load properties "+getPropertyFileName());
		}
		
	}
	
	private void save() {
		final Properties p = new Properties();
		memoryRepo.entrySet().stream().forEach(entry -> p.setProperty(entry.getKey().toString(), entry.getValue().toString()));		
		try(final OutputStream os = new FileOutputStream(new File(getClass().getResource("/"+getPropertyFileName()).toURI()))){
			p.store(os, null);
		} catch (IOException | URISyntaxException e) {
			LOG.error("could not save properties in file ["+getPropertyFileName()+"]",e);
		}
	}
	
	public Color get(final String threat) {
		final Color c = memoryRepo.get(threat);
		return c==null? memoryRepo.get("INFO"):c;
	}
	
	public void put(final String threat, final Color color) {
		memoryRepo.put(threat,color);
		save();
	}
	
}
