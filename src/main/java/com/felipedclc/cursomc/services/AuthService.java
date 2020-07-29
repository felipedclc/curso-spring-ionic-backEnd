package com.felipedclc.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Random rand = new Random(); // CLASSE DO JAVA QUE GERA VALORES ALEATÓRIOS
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email); // ENCONTRANDO O USUARIO
		if(cliente==null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword(); // GERANDO NOVA SENHA 
		cliente.setSenha(bcrypt.encode(newPass)); // SETANDO A SENHA COM BCRYPT
		
		clienteRepository.save(cliente); // SALVANDO O NOVO CLIENTE NO BANCO
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() { // GERANDO UMA NOVA SENHA
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3); //0,1,2
		if(opt==0) { // GERA UM DIGITO 
			return (char) (rand.nextInt(10) + 48); // 48 É O CODIGO UNICODE DO NUMERO 0 (de 0 a 9)
		}
		else if(opt==1) { // GERA LETRA MAIUSCULA 
			return (char) (rand.nextInt(26) + 65); // 48 É O CODIGO UNICODE DA LETRA A (de A a Z)
		}
		else { // GERA LETRA MINUSCULA 
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
