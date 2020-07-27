package com.felipedclc.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipedclc.cursomc.domain.ItemPedido;
import com.felipedclc.cursomc.domain.PagamentoComBoleto;
import com.felipedclc.cursomc.domain.Pedido;
import com.felipedclc.cursomc.domain.enums.EstadoPagamento;
import com.felipedclc.cursomc.repositories.ItemPedidoRepository;
import com.felipedclc.cursomc.repositories.PagamentoRepository;
import com.felipedclc.cursomc.repositories.PedidoRepository;
import com.felipedclc.cursomc.services.exceptions.ObjectNotFoundException;

@Service // CLASSE QUE BUSCA OS DADOS DO REPOSITÓRIO E PASSA PARA O CONTROLADOR REST 
public class PedidoService {

	@Autowired // INJEÇÃO DE DEPENDÊNCIA AUTOMATICA
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		 Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null); // GARANTINDO QUE É UM NOVO PEDIDO
		obj.setDate(new Date()); // SETANDO A DATA PARA O MOMENTO ATUAL
		obj.setCliente(clienteService.find(obj.getCliente().getId())); // SETANDO O CLIENTE COM O ID BUSCADO NO BD
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);// DEFININDO O ESTADO DO PAGAMENTO (pedido acabou de ser inserido)
		obj.getPagamento().setPedido(obj); // O PAGAMENTO DEVE CONHECER O PEDIDO
		if(obj.getPagamento() instanceof PagamentoComBoleto) { // "SE O PAGAMENTO FOR INSTANCIA DE PAGAMENTO COM BOLETO"
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getDate());
		}
		obj = repo.save(obj); // SALVANDO O PEDIDO NO BANCO
		pagamentoRepository.save(obj.getPagamento()); // SALVANDO O PAGAMENTO NO BANCO
		for(ItemPedido ip : obj.getItens()) { // PERCORRENDO TODOS OS ITENS DE PEDIDO ASSOCIADOS AO OBJ
			ip.setDesconto(0.0); // SEM DESCONTO
			// QUANTIDADE JÁ ESTÁ ASSOCIADA AO ITEM PEDIDO
			ip.setProduto(produtoService.find(ip.getProduto().getId())); // SETANDO O PRODUTO COM O ID BUSCADO NO BD
			ip.setPreco(ip.getProduto().getPrice()); // PEGANDO O PREÇO DO PRODUTO
			ip.setPedido(obj); // ASSOCIANDO O ITEM DE PEDIDO COM O NOVO PEDIDO
		}
		itemPedidoRepository.saveAll(obj.getItens()); // SALVANDO OS ITENS(LISTA) NO REPOSITÓRIO
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
}
