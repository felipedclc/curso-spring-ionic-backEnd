package com.felipedclc.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token); // CLAIMS ARMAZENA AS REIVINDICAÇOES DO TOKEN (USER, EXPIRATION..) 
		if(claims != null ) {
			String username = claims.getSubject(); // FUNÇÃO DO CLAIMS PARA RETORNAR O USUÁRIO
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if(username != null && expirationDate != null && now.before(expirationDate)) { // SE O INSTANTE ATUAL É ANTERIOR A DATA DE EXPIRAÇÃO
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) { // PEGA O USUARIO A PARTIR DO TOKEN 
		Claims claims = getClaims(token); 
		if(claims != null ) {
			return claims.getSubject(); 
		}
		return null; // RETORNA VAZIO SE NÃO ENCONTRAR
	}

	private Claims getClaims(String token) {
		//CLAIMS ARMAZENA AS REINVIDICAÇÕES DO TOKEN (USER, EXPIRATION..)
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}
