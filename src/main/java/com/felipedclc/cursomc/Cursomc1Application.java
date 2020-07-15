package com.felipedclc.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.felipedclc.cursomc.domain.Categoria;
import com.felipedclc.cursomc.domain.Cidade;
import com.felipedclc.cursomc.domain.Cliente;
import com.felipedclc.cursomc.domain.Endereco;
import com.felipedclc.cursomc.domain.Estado;
import com.felipedclc.cursomc.domain.Produto;
import com.felipedclc.cursomc.domain.enums.TipoCliente;
import com.felipedclc.cursomc.repositories.CategoriaRepository;
import com.felipedclc.cursomc.repositories.CidadeRepository;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.repositories.EnderecoRepository;
import com.felipedclc.cursomc.repositories.EstadoRepository;
import com.felipedclc.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class Cursomc1Application implements CommandLineRunner { // PERMITE IMPLEMENTAR UM METODO AUXILIAR QUANDO A APLICAÇÃO INICIAR 

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(Cursomc1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));	
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2)); // SALVANDO NO BANCO DE DADOS
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberaba", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Belo Horizonte", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c3));
		est2.getCidades().addAll(Arrays.asList(c2));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "10655789423", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("987455125", "987451239"));
		
		Endereco e1 = new Endereco(null, "rua Flores", "300", "apto 303", "Jardim", "30455870", cli1, c1);
		Endereco e2 = new Endereco(null, "av Matos", "105", "apto 105", "Centro", "30945400", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}
