package com.pokemon.pokemones.core.services;

import org.springframework.stereotype.Service;

@Service
public class DataVisualizationPropertyService extends AbstractPropertiesService{
		
	protected @Override String getPropertyFileName(){
		return "dataVisualization.properties";
	}
	
	//poner las claves para acceder a las propiedades. Para poder acceder a ellas anotarlas para poder acceder por reflexion
	/*@PropertyKey */
	private static final String KEY_ELEMENTSPERPAGE = "elemets.page";
	
	public void setElementsPerPage(final String color){
		super.setProperty(KEY_ELEMENTSPERPAGE,color);
	}
	
	public int getElementsPerPage(){
		try {
			return Integer.parseInt((String)super.getProperty(KEY_ELEMENTSPERPAGE,"7"));
		}catch (Exception e) {
			return 7;
		}
	}
	
	
}

