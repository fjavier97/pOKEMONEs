package com.pokemon.pokemones.core.services;

import org.springframework.stereotype.Service;

import javafx.scene.paint.Color;

@Service
public class AspectPropertyService extends AbstractPropertiesService{
		
	protected @Override String getPropertyFileName(){
		return "aspect.properties";
	}
	
	//poner las claves para acceder a las propiedades. Para poder acceder a ellas anotarlas para poder acceder por reflexion
	/*@PropertyKey */
	private static final String KEY_DEFAULTBACKGROUNDCOLOR = "background.color.default";
	private static final String KEY_ANIMATIONENABLED = "animation.enabled";
	private static final String KEY_ANIMATIONLENGTH = "animation.length";
	
	public void setDefaultBackgroundColor(final String color){
		super.setProperty(KEY_DEFAULTBACKGROUNDCOLOR,color);
	}
	
	public Color getDefaultBackgroundColor(){
		return Color.web((String)super.getProperty(KEY_DEFAULTBACKGROUNDCOLOR,"#808080FF"));
	}
	
	public void setAnimationEnabled(final String color){
		super.setProperty(KEY_ANIMATIONENABLED,color);
	}
	
	public boolean getAnimationEnabled(){
		return ("true".equals((String)super.getProperty(KEY_ANIMATIONENABLED,"false")));
	}
	
	public void setAnimationLenght(final String color){
		super.setProperty(KEY_ANIMATIONLENGTH,color);
	}
	
	public int getAnimationLenght(){
		try {
			return Integer.parseInt((String)super.getProperty(KEY_ANIMATIONLENGTH,"1000"));
		}catch (Exception e) {
			return 1000;
		}
	}
	
	
}
