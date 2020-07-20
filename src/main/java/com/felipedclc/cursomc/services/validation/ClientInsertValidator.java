package com.felipedclc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.domain.enums.TipoCliente;
import com.felipedclc.cursomc.dto.ClienteNewDTO;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.resources.exception.FieldMessage;
import com.felipedclc.cursomc.services.validation.utils.BR;

public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClientInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) { // @Valid (true, false)
		
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && ! BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido")); // ADD A MENSAGEM DE ERRO
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && ! BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNsPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null) { // SE NÃO FOR NULO O OBJETO(EMAIL) FOI ENCONTRADO 
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) { // PERCORRE A LISTA DE ERROS E INSERE OS ERROS CORRESPONDENTES
			context.disableDefaultConstraintViolation(); 
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
		}
		return list.isEmpty(); //TRUE
	}
}