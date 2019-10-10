package com.pokemon.pokemones.core.locals;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/*
 * TODO
 * intentar unir @FXML con esta para no tener que poner 2 anotaciones
 */


//import javafx.fxml.FXML;

@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface Lacalized {	
	//FXML fxml() default @FXML;
}
