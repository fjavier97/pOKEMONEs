package com.pokemon.pokemones.core.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import com.pokemon.pokemones.core.services.SecurityMethodService;

public class MyMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    private Object filterObject;
    private Object returnObject;
    private Object target;
    private String methodName;
	private String className;
	
	private final SecurityMethodService s;

    public MyMethodSecurityExpressionRoot(Authentication a, SecurityMethodService s) {
        super(a);
		this.s = s;
		System.out.println("root creado");
    }

    // allow the method to be set    
    public void setClassName(final String className) {
        this.className = className;
    }

    // optionally expose the method to be accessed in expressions    
    public String getClassName() {
        return className;
    }

    // allow the method to be set    
    public void setMethod(final String methodName) {
        this.methodName = methodName;
    }

    // optionally expose the method to be accessed in expressions    
    public String getMethod() {
        return methodName;
    }

    // create a method that will perform the check with 
    // the method name transparently for you    
	public boolean canUse() {
//		List<GrantedAuthority> permisions = s.getPermissionsRequiredFor(getClassName(),getMethod());
//		for(GrantedAuthority auth: getAuthentication().getAuthorities()){
//			if(permisions.contains(auth)){
//				return true;
//			}
//		}
//		return false;
		System.out.println("---- holaaaaaa ----");
		System.out.println(getMethod());
		System.out.println(getClassName());
		return false;
    }

    // implement the interface and provide setters

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    public Object getFilterObject() {
        return filterObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setThis(Object target) {
        this.target = target;
    }

    public Object getThis() {
        return target;
    }
}