package com.pokemon.pokemones.core.job;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Retention(RUNTIME)
@Target(TYPE)
@Service
@Scope("prototype")
public @interface JobPerformable {

}
