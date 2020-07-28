package com.felipedclc.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.felipedclc.cursomc.security.JWTAuthenticationFilter;
import com.felipedclc.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private Environment env;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	private static final String[] PUBLIC_MATCHERS = { // DEFININDO QUAIS OS CAMINHOS ESTÃO LIBERADOS
			"/h2-console/**",
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = { // DEFININDO QUAIS OS CAMINHOS ESTÃO LIBERADOS(apenas para o get)
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) { // SE O PROFILE ESTIVER TEST 
            http.headers().frameOptions().disable(); // COMANDO QUE LIBERA O ACESSO AO H2
        }
		
		http.cors().and().csrf().disable(); // CHAMANDO O CORS E DESATIVANDO O CSRF(ARMAZENA SEÇÃO)
		http.authorizeRequests() 
			.antMatchers(PUBLIC_MATCHERS).permitAll() // PERMITE TODOS OS CAMINHOS DO VETOR 
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // PERMITE APENAS ACESSAR DADOS
			.anyRequest().authenticated(); // PEDE AUTORIZAÇÃO PARA QUEM NÃO FOR DO VETOR
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil)); // REGISTRANDO O FILTRO DE AUTENTICAÇÃO
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // BACK-END NÃO PDOE CRIAR SESSÃO DE USUÁRIO
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // SOBRESCREVENDO O METODO CONFIGURE (mecanismo de autenticação)
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder()); // IDENTIFICA QUE É O USER DETAILSERVICEE O PASSWORDENCODER
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() { 
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;  // CONCEDENDO ACESSO AOS END POINTS POR MULTIPLAS FONTES(/**) COM AS CONFIGURAÇÕES BÁSICAS 
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() { // ENCODANDO A SENHA
		return new BCryptPasswordEncoder();
	}
}
