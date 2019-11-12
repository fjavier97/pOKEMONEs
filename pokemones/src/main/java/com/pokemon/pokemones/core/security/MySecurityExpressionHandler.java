package com.pokemon.pokemones.core.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pokemon.pokemones.core.services.SecurityMethodService;

public class MySecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler{
	
	private final SecurityMethodService service;
	
	public MySecurityExpressionHandler(final SecurityMethodService service){
		super();
		this.service = service;
		System.out.println("handler creado");
	}
	
    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation){
    	System.out.println("devulevo el root");
    	MyMethodSecurityExpressionRoot root = new MyMethodSecurityExpressionRoot(authentication, service);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(new AuthenticationTrustResolverImpl());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setMethod(invocation.getMethod().getName());
		root.setClassName(invocation.getMethod().getDeclaringClass().getAnnotation(Service.class).value());
        return root;
    }
}