package com.felipedclc.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.security.UserSS;

@Service
public class UserDatailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // RECEBE O USER(email-usuario) E RETORNA
																							// USERDETAILS(usuario Spring Security)
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email); // EXCEÇÃO DO SPRING SECURITY
		}

		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}
}
