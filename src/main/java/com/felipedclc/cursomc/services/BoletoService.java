package com.felipedclc.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService { // REALIDADE É FAZER A CHAMADA DE UM WEB SERVICE QUE IRÁ GERAR O BOLETO

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance(); // INSTANCIANDO O CALENDARIO CAL
		cal.setTime(instanteDoPedido); // SETANDO A DATA PARA O instanteDoPedido
		cal.add(Calendar.DAY_OF_MONTH, 7); // ADD 7 DIAS PARA O VENCIMENTO
		pagto.setDataVencimento(cal.getTime());
	}
}
