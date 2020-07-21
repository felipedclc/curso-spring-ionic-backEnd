package com.felipedclc.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static String decodeParam(String s) { // METODO PARA DECODIFICAR O STRING(NOME BUSCADO)
		try {
			return URLDecoder.decode(s, "UTF-8"); // UTF-8 DECODER URL PADRAO 
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		} 
	}

	public static List<Integer> decodeIntList(String s){  // TRANSFORMA O STRING NA HORA DE BUSCAR EM INT 
		String[] vet = s.split(","); // CRIANDO UM VETOR QUE RECORTA O STRING BASEADO NA VÍRGULA
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i])); // CONVERTENDO CADA ITEM DO VETOR PARA INT 
		}
		return list;
		// EXPRESSÃO LAMBDA EQUIVALENTE:  
		// return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	} 
}
