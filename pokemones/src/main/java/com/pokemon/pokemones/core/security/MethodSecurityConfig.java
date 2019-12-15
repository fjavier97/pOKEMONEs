package com.pokemon.pokemones.core.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.pokemon.pokemones.core.item.dto.AuthorityDPO;
import com.pokemon.pokemones.core.item.dto.UserDPO;
import com.pokemon.pokemones.core.services.SecurityMethodService;
import com.pokemon.pokemones.core.services.SecurityUserService;
import com.pokemon.pokemones.core.services.UserService;

import org.springframework.security.crypto.password.PasswordEncoder; 

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
		
	private final static Logger LOG = LoggerFactory.getLogger(MethodSecurityConfig.class);
	
	@Autowired
	public MethodSecurityConfig(
			final SecurityUserService userDetailsService,
			final UserService userService,
			final SecurityMethodService securedMethodsService) {
		
		this.pwdEncoder = new PWDEncoder();
		this.authProvider = new CustomAuthenticationManager(userDetailsService, pwdEncoder);
		
		this.securedMethodsService=securedMethodsService;
		this.userDetailsService = userDetailsService;
		this.userService=userService;
	}
	
	private final PasswordEncoder pwdEncoder;
	
	@Bean
	public PasswordEncoder pwdEncoder() {
		return pwdEncoder;
	}	
	
	private final CustomAuthenticationManager authProvider;
	
	@Bean 
	public CustomAuthenticationManager authProvider() {
		return authProvider;
	}
	
	private final SecurityMethodService securedMethodsService;
	
	@Override 
	protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new MySecurityExpressionHandler(securedMethodsService);
    }
	
	private final SecurityUserService userDetailsService;
	private final UserService userService;
	
	@Override 
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception{		
		auth.authenticationProvider(authProvider).userDetailsService(userDetailsService).passwordEncoder(pwdEncoder);
		/* TODO esto ponerlo en una clase con un listener de context refreshedevent */
		if(userService.findAll(null, Pageable.unpaged()).isEmpty()) {
			UserDPO admin = new UserDPO();
			admin.setUsername("admin");
			admin.setPassword("nimda");
			AuthorityDPO rol = new AuthorityDPO("ROLE_ADMIN");
			admin.getAuthorities().add(rol);
			userService.save(admin);
			LOG.info("no users in the system, initializing admin user [admin] with password [nimda] with role [ROLE_ADMIN]");
		}
	}
		
}
