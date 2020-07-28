package com.felipedclc.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component // PODE SER INJETADA EM OUTRAS CLASSES COMO COMPONENTE
public class JWTUtil {

	@Value("${jwt.secret}") // IMPORTANDO DO APPLICATION.PROPERTIES
	private String secret; // SECRET POSSUI O VALOR DA VARIAVEL jwt.secret
	
	@Value("${jwt.expiration}") 
	private Long expiration;
	
	public String generateToken(String username) { // GERANDO O TOKEN
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // COMO ASSINAR O TOKEN
				.compact();
	}
}
