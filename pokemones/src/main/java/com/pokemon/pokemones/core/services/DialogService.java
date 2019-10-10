package com.pokemon.pokemones.core.services;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.ComponentLoader;
import com.pokemon.pokemones.core.ComponentManager;
import com.pokemon.pokemones.core.DialogAction;

@Service
public class DialogService{
	
	private final ComponentLoader loader;
	private final ApplicationContext ctx;
	private final ComponentManager manager;
	
	public @Autowired DialogService(final ComponentLoader loader, final ComponentManager manager, final ApplicationContext ctx){
		this.loader=loader;
		this.ctx = ctx;
		this.manager = manager;
	}
	
	public <E> void prompt(final String name){
		DialogAction<E> actiondialog = (DialogAction<E>)ctx.getBean(DialogAction.class, loader, manager, name);
		/*este filtro hace que si devuelve un null, el handler no se ejecute*/
		final Predicate<E> filter = r -> r!=null;
		actiondialog.getDialog().showAndWait().filter(filter).ifPresent(actiondialog.getController());
	}
}
