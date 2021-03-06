package com.pokemon.pokemones.core.scopes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pokemon.pokemones.core.component.controller.AbstractController;

@Component
public class ComponentScope implements Scope {

	private final Map<String, AbstractController> beans;
	private final Map<String, Runnable> destructionCallbacks;

	public ComponentScope() {
		super();
		this.beans=new ConcurrentHashMap<String, AbstractController>();
		this.destructionCallbacks=new ConcurrentHashMap<String, Runnable>();
	}
	
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if(!beans.containsKey(name)) {
			beans.put(name, (AbstractController)objectFactory.getObject());
		}
		return beans.get(name);
	}
	
	public Object remove(String name) {
//		System.out.println("remove "+name);
		if(destructionCallbacks.containsKey(name)) {
			destructionCallbacks.remove(name).run();
		}
		return beans.remove(name);
	}
	
	public void clean() {
		for(String k: beans.keySet()) {
			if(!beans.get(k).isActivo())
				remove(k);
		}
	}
	
	public void removeAll() {
		for(String k: beans.keySet()) {
			remove(k);
		}
	}
		
	public void registerDestructionCallback(String name, Runnable callback) {
		this.destructionCallbacks.put(name, callback);

	}

	public Object resolveContextualObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getConversationId() {
		// TODO Auto-generated method stub
		return null;
	}

}
