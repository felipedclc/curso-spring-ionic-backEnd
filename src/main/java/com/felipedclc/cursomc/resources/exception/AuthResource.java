package com.felipedclc.cursomc.resources.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.felipedclc.cursomc.security.JWTUtil;
import com.felipedclc.cursomc.security.UserSS;
import com.felipedclc.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth") // END POINT PROTEGIDO POR AUTENTICAÇÃO 
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST) // FILTRO DE AUTORIZAÇÃO 
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) { 
		UserSS user = UserService.authenticated(); // PEGANDO O USUARIO LOGADO
		String token = jwtUtil.generateToken(user.getUsername()); // GERANDO NOVO TOKEN PARA O USUARIO
		response.addHeader("Authorization", "Bearer " + token); // ADD O TOKEN NA RESPOSTA DA REQUISIÇÃO 
		return ResponseEntity.noContent().build();
	}
}
