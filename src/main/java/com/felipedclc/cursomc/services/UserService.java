package com.felipedclc.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.felipedclc.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() { 
		try {
			// OBTENDO UM USUARIO LOGADO NA FORMA SPRING SECURITY
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} 
		catch (Exception e){
			return null;
		}
	}
}
