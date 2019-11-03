package com.pokemon.pokemones.entrypoint;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.pokemon.pokemones.core.services.CustomAuthenticationManager;
import com.pokemon.pokemones.core.services.SecurityUserService;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; 


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
	
	
//	private @Autowired DataSource dataSource;
//
//	protected @Override void configure(final AuthenticationManagerBuilder auth) throws Exception{
//		auth.jdbcAuthentication().dataSource(dataSource)//.withDefaultSchema()///*.passwordEncoder(...)*/;
//		.usersByUsernameQuery("select username,password,enabled from users where username = ? ")
//		.authoritiesByUsernameQuery(" select username,authority from authorities where username = ? ")
//			.withUser("admin")
//			.password("nimda")
//			.roles("ADMIN");
//	}
	
	/* esto es para tener como bean el authmanager */
//	public @Bean @Override AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManager();
//	}
	
	class PwdEncoder implements PasswordEncoder{

		@Override
		public String encode(CharSequence rawPassword) {
			// TODO Auto-generated method stub
			return rawPassword.toString();
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			// TODO Auto-generated method stub
			return rawPassword.equals(encodedPassword);
		}
		
	}
	
	private @Autowired CustomAuthenticationManager authProvider;
	private @Autowired SecurityUserService userDetailsService;
	
	protected @Override void configure(final AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authProvider).userDetailsService(userDetailsService).passwordEncoder(new PwdEncoder());
	}
}
