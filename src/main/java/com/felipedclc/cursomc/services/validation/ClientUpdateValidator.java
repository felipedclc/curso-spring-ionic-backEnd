package com.felipedclc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.dto.ClienteDTO;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.resources.exception.FieldMessage;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request; // POSSUI UMA FUNÇÃO QUE PERMITE OBTER O PARAMATRO DA URI (IDENTIFICAR O ID)

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClientUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) { // @Valid (true, false)

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE); // PEGA VARIAVEIS DE URI QUE ESTAO NA REQUISIÇÃO
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && aux.getId() != uriId) { // SE NÃO FOR NULO O OBJETO(EMAIL) FOI ENCONTRADO
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) { // PERCORRE A LISTA DE ERROS E INSERE OS ERROS CORRESPONDENTES
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty(); // TRUE
	}
}