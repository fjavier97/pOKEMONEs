package com.pokemon.pokemones.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

public abstract class Loader {
	
	protected final ApplicationContext ctx;
		
	protected final String fx_prefix;
	protected final String fx_suffix;
	
	protected final String fx_css_prefix;
	protected final String fx_css_suffix;	
	
	public @Autowired Loader(ApplicationContext ctx,
			@Value("${fx.view.prefix}")String fx_prefix,
			@Value("${fx.view.suffix}")String fx_suffix,
			@Value("${fx.css.prefix}")String fx_css_prefix, 
			@Value("${fx.css.suffix}")String fx_css_suffix)	{
		super();

		this.ctx=ctx;
		
		this.fx_prefix=fx_prefix;
		this.fx_suffix=fx_suffix;
		this.fx_css_prefix=fx_css_prefix;
		this.fx_css_suffix=fx_css_suffix;
	}
	
}
