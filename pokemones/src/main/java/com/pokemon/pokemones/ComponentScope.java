package com.pokemon.pokemones;

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

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		if(!beans.containsKey(name)) {
			beans.put(name, objectFactory.getObject());
		}
		return beans.get(name);
	}

	@Override
	public Object remove(String name) {
		if(destructionCallbacks.containsKey(name)) {
			destructionCallbacks.remove(name).run();
		}
		return beans.remove(name);
	}
	
	private @EventListener void onContextClosed(ContextClosedEvent evt) {
		for(String key: beans.keySet()) {
			remove(key);
		}
	}
	
	private @EventListener void onContextClosed(ComponenteChangeCommitEvent evt) {
		remove(evt.getComponente().toString());
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		this.destructionCallbacks.put(name, callback);

	}

	@Override
	public Object resolveContextualObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConversationId() {
		// TODO Auto-generated method stub
		return null;
	}

}
