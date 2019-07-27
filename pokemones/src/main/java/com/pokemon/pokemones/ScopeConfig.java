package com.pokemon.pokemones;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ScopeConfig
{

	@Bean
	public static CustomScopeConfigurer customScopeConfigurer(final ComponentScope scope)
	{
		final CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.addScope("ComponentScope", scope);
		return configurer;
	}
}
