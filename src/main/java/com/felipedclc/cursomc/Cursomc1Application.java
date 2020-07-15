package com.felipedclc.cursomc;

import java.text.SimpleDateFormat;
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
import com.felipedclc.cursomc.domain.ItemPedido;
import com.felipedclc.cursomc.domain.Pagamento;
import com.felipedclc.cursomc.domain.PagamentoComBoleto;
import com.felipedclc.cursomc.domain.PagamentoComCartao;
import com.felipedclc.cursomc.domain.Pedido;
import com.felipedclc.cursomc.domain.Produto;
import com.felipedclc.cursomc.domain.enums.EstadoPagamento;
import com.felipedclc.cursomc.domain.enums.TipoCliente;
import com.felipedclc.cursomc.repositories.CategoriaRepository;
import com.felipedclc.cursomc.repositories.CidadeRepository;
import com.felipedclc.cursomc.repositories.ClienteRepository;
import com.felipedclc.cursomc.repositories.EnderecoRepository;
import com.felipedclc.cursomc.repositories.EstadoRepository;
import com.felipedclc.cursomc.repositories.ItemPedidoRepository;
import com.felipedclc.cursomc.repositories.PagamentoRepository;
import com.felipedclc.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("26/06/2019 17:36"), e1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2019 10:42"), e2, cli1);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.APROVADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2019 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
