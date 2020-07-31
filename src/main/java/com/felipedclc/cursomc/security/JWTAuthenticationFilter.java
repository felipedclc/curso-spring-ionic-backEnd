package com.felipedclc.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipedclc.cursomc.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter { 
	// FILTRO QUE INTERCEPTA A REQUISIÇAO DE LOGIN - /login (PADRAO)
	
	private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;
	
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req, // METODO QUE TENTA AUTENTICAR (CODIGO PADRAO DO SpringSecurity)
                                                HttpServletResponse res) throws AuthenticationException {	
		
		try { 
			CredenciaisDTO creds = new ObjectMapper()
	                .readValue(req.getInputStream(), CredenciaisDTO.class);

	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

	        Authentication auth = authenticationManager.authenticate(authToken);
	        return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req, // PROCEDIMENTOS APÓS OCORRER A AUTENTICAÇÃO
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsername(); //PEGANDO O USERNAME E RETORNANDO UM USUARIO DO SPRING SECURITY
        String token = jwtUtil.generateToken(username); // GERA O TOKEN A PARTIR DO USUARIO(EMAIL)
        res.addHeader("Authorization", "Bearer " + token); // ACRESCENTANDO O TOKEN NA RESPOSTA DA REQUISIÇÃO
        res.addHeader("access-control-expose-headers", "Authorization"); // COMANDO PARA LIBERAR A LEITURA DO HEADER(CABEÇALHO)
	}
}