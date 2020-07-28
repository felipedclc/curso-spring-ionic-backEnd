package com.felipedclc.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{ 
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService; // BUSCA USUARIO POR EMAIL 

	//EXTRAI O CODIGO TOKEN E BUSCA O USUARIO NO BANCO
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDatailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDatailsService;
	}
  
	@Override
    protected void doFilterInternal(HttpServletRequest request,  //INTERCEPTA A REQUISIÇÃO E CONFERE SE O USUARIO ESTA AUTORIZADO 
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

		String header = request.getHeader("Authorization"); // PEGANDO O VALOR DO CABEÇALHO(STRING)
		
		if (header != null && header.startsWith("Bearer ")) {
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7)); // PEGA O STRING TIRANDO 7 PRIMEIRAS LETRAS
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		chain.doFilter(request, response); // CONTINUA A FAZER OS TESTES SE AS CONDIÇOES DE REQUISIÇÕES FOREM ATENDIDAS
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.tokenValido(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username); // BUSCA NO BANCO DE DADOS O USUARIO
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
	
}
