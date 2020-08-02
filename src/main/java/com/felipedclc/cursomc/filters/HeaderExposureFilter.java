package com.felipedclc.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component 
public class HeaderExposureFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse) response; // UPCASTING
		res.addHeader("access-control-expose-headers", "location"); // EXPPÕE O HEADER LOCATION NA RESPOSTA(http:localhost8080..)
		chain.doFilter(request, response); // ENCAMINHANDO A REQUISIÇÃO
	}
}
