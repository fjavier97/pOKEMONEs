package com.pokemon.pokemones.core.scopes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ComponentScope implements Scope {

	private final Map<String,Object> beans;
	private final Map<String, Runnable> destructionCallbacks;

	public ComponentScope() {
		super();
		this.beans=new ConcurrentHashMap<>();
		this.destructionCallbacks=new ConcurrentHashMap<>();
	}
	
	public @Override Object get(String name, ObjectFactory<?> objectFactory) {
		if(!beans.containsKey(name)) {
			beans.put(name, objectFactory.getObject());
		}
		return beans.get(name);
	}
	
	public @Override Object remove(String name) {
//		System.out.println("remove "+name);
		if(destructionCallbacks.containsKey(name)) {
			destructionCallbacks.remove(name).run();
		}
		return beans.remove(name);
	}
	
	public void removeAll() {
		for(String k: beans.keySet()) {
			remove(k);
		}
	}
		
	public @Override void registerDestructionCallback(String name, Runnable callback) {
		this.destructionCallbacks.put(name, callback);

	}

	public @Override Object resolveContextualObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public @Override String getConversationId() {
		// TODO Auto-generated method stub
		return null;
	}

}
