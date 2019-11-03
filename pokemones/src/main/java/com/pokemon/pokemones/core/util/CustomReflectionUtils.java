package com.pokemon.pokemones.core.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomReflectionUtils {

	public static final List<Field> getAllFields(final Class<?> clazz){
		final List<Field> fields = new LinkedList<Field>();
		if(!clazz.equals(Object.class)) {
			fields.addAll(Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
			fields.addAll(getAllFields(clazz.getSuperclass()));
		}
		return fields;
	}
	
}
