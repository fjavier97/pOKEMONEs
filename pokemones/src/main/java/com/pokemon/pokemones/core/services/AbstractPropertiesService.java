package com.pokemon.pokemones.core.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPropertiesService {	

	private final Properties properties;
	
	private final Logger LOG;
	
	protected final Properties getProperties(){
		return properties;
	}
	
	public final Object setProperty(final String k, final String v){
		final Object res = properties.setProperty(k,v);
		try(final OutputStream os = new FileOutputStream(new File(getClass().getResource("/"+getPropertyFileName()).toURI()))){
			this.properties.store(os,"");
		} catch (IOException | URISyntaxException e) {
			LOG.error("could not save property ["+k+"] in file ["+getPropertyFileName()+"]",e);
		}
		return res;
	}
	
	public final Object getProperty(final String k, final String def){
		return properties.getProperty(k,def);
	}
	
	public AbstractPropertiesService(){
		this.LOG = LoggerFactory.getLogger(getClass());
		this.properties = new Properties();
		this.reloadProperties(true);	
	}
	
	/* este metodo se llama al inicio, pero se puede volver a llamar para recargar las propiedades 
	 * (si se cambia el fichero en tiempo de ejecución, aunque es mejor usar el interfaz) */
	public void reloadProperties(final boolean createIfNotExists){
		final URL loc = getClass().getResource("/"+getPropertyFileName());
		if(loc==null){
			LOG.info("property file "+getPropertyFileName()+" does not exist");
			if(createIfNotExists){
				try {
					new File(new File(getClass().getResource("/").toURI()),File.separator+getPropertyFileName()).createNewFile();
					LOG.info("file created");
				} catch (IOException | URISyntaxException e) {
					LOG.error("could not create property file ["+getPropertyFileName()+"]",e);
				}
			}else{
				return;
			}
		}
		try(final InputStream is = getClass().getResourceAsStream("/"+getPropertyFileName())){
			this.properties.load(is);
			LOG.info("properties loaded");
		}catch(IOException ioe){
			LOG.error("could not load properties "+getPropertyFileName());
		}
	}
	
	/* esto se implementa por la clase concreta para obtener la direccion del fichero de persistencia de las propiedades */
	protected abstract String getPropertyFileName();
	
}
