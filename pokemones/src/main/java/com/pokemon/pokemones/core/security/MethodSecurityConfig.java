package com.pokemon.pokemones.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.pokemon.pokemones.core.services.SecurityMethodService;
import com.pokemon.pokemones.core.services.SecurityUserService;

import org.springframework.security.crypto.password.PasswordEncoder; 

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
		
	@Autowired
	public MethodSecurityConfig(final SecurityUserService userDetailsService,final SecurityMethodService securedMethodsService) {
		
		this.pwdEncoder = new PWDEncoder();
		this.authProvider = new CustomAuthenticationManager(userDetailsService, pwdEncoder);
		
		this.securedMethodsService=securedMethodsService;
		this.userDetailsService = userDetailsService;
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
	
	@Override 
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception{		
		auth.authenticationProvider(authProvider).userDetailsService(userDetailsService).passwordEncoder(pwdEncoder);
		/* TODO esto ponerlo en una clase con un listener de context refreshedevent */
//		if(userDetailsService.findAll(null, Pageable.unpaged()).isEmpty()) {
//			UserDPO admin = new UserDPO();
//			admin.setUsername("admin");
//			admin.setPassword("nimda");
//			RoleDPO rol = new RoleDPO("ROLE_ADMIN");
//			admin.getAuthorities().add(rol);
//			userDetailsService.save(admin);
//			LOG.info("no users in the system, initializing admin user [admin] with password [nimda] with role [ROLE_ADMIN]");
//		}
	}
		
}
