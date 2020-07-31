package com.felipedclc.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.felipedclc.cursomc.dto.EmailDTO;
import com.felipedclc.cursomc.security.JWTUtil;
import com.felipedclc.cursomc.security.UserSS;
import com.felipedclc.cursomc.services.AuthService;
import com.felipedclc.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth") // END POINT PROTEGIDO POR AUTENTICAÇÃO 
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST) // FILTRO DE AUTORIZAÇÃO 
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) { 
		UserSS user = UserService.authenticated(); // PEGANDO O USUARIO LOGADO
		String token = jwtUtil.generateToken(user.getUsername()); // GERANDO NOVO TOKEN PARA O USUARIO
		response.addHeader("Authorization", "Bearer " + token); // ADD O TOKEN NA RESPOSTA DA REQUISIÇÃO 
		response.addHeader("access-control-expose-headers", "Authorization"); // ADD A EXPOSIÇÃO DO CABEÇALHO 
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST) // FILTRO DE AUTORIZAÇÃO 
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) { 
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
}
