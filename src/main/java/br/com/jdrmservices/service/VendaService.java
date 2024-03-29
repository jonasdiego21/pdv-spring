package br.com.jdrmservices.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.ModelAndView;

//import br.com.jdrmservices.bematech.BematechPrinter;
//import br.com.jdrmservices.epson.EpsonPrint;
import br.com.jdrmservices.impressora.GenericPrinter;
import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.model.ItemVenda;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.FormaPagamento;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.model.enumeration.StatusVenda;
import br.com.jdrmservices.repository.ContasReceber;
import br.com.jdrmservices.repository.ItensVendas;
import br.com.jdrmservices.repository.Vendas;
import br.com.jdrmservices.service.event.venda.FechamentoCupomEvent;
import br.com.jdrmservices.service.event.venda.VendaEvent;

@Service
public class VendaService {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ItensVendas itensVendas;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ContasReceber contasReceber;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private GenericPrinter genericPrinter;

	//@Autowired
	//private EpsonPrint epsonPrint;
	
	//@Autowired
	//private BematechPrinter bematechPrinter;
	
	@Transactional
	public void cadastrar(Venda venda) {	
		if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
			venda.setStatus(StatusVenda.CREDIARIO);
			
			Optional<ContaReceber> contaReceberExistente = 
					contasReceber.findByClienteNomeIgnoreCaseAndStatus(venda.getCliente().getNome(), Status.DEVENDO);
			
			if(contaReceberExistente.isPresent()) {
				contaReceberExistente.get().setTotalVenda(venda.getValorTotal().add(contaReceberExistente.get().getTotalVenda()));
				
				contasReceber.saveAndFlush(contaReceberExistente.get());
			} else {
				ContaReceber contaReceber = new ContaReceber();
				
				contaReceber.setCliente(venda.getCliente());
				contaReceber.setTotalVenda(venda.getValorTotal());
				//contaReceber.setDataVenda(venda.getDataCriacao());
				//contaReceber.setDataVencimento(venda.getDataCriacao().plusDays(30));
				contaReceber.setDataVenda(venda.getDataCriacao().toLocalDate());
				contaReceber.setDataVencimento(venda.getDataCriacao().plusDays(30).toLocalDate());
				contaReceber.setStatus(Status.DEVENDO);
				
				contasReceber.saveAndFlush(contaReceber);
			}
		}
		
		if(venda.getFormaPagamento().equals(FormaPagamento.NENHUMA)) {
			venda.setStatus(StatusVenda.CANCELADA);
		}
		
		if(venda.getValorTotal().doubleValue() == 0 || venda.getValorTotal() == null) {
			venda.setStatus(StatusVenda.CANCELADA);
		}

		if(!venda.getStatus().equals(StatusVenda.ORCAMENTO) && !venda.getStatus().equals(StatusVenda.CANCELADA)) {			
			publisher.publishEvent(new VendaEvent(venda));
			publisher.publishEvent(new FechamentoCupomEvent(venda));
		}
		
		venda.setValorTotal(venda.getValorTotal().subtract(venda.getValorDesconto()));
		
		vendas.saveAndFlush(venda);
	}
	
	@Transactional
	public void excluir(Venda venda) {
		vendas.delete(venda);
	}
	
	@Transactional
	public void devolverItem(ItemVenda itemVenda, Venda venda) {
		Optional<Venda> vendaExistente = vendas.findById(venda.getCodigo());
		
		produtoService.adicionarEntrada(itemVenda.getProduto(), itemVenda.getQuantidade());
		itensVendas.delete(itemVenda);
		
		Optional<BigDecimal> valorTotalVenda = vendaExistente.get().getItens()
				.stream().map(i -> i.getValorTotal()).findAny();
		
		vendaExistente.get().setValorTotal(valorTotalVenda.orElse(BigDecimal.ZERO));
		vendas.saveAndFlush(vendaExistente.get());
	}
		
	@Transactional
	public void cancelar(Venda venda) {
		Optional<Venda> vendaExistente = vendas.findById(venda.getCodigo());
		
		for(ItemVenda it : vendaExistente.get().getItens()) {
			produtoService.adicionarEntrada(it.getProduto(), it.getQuantidade());
		}
		
		vendaExistente.get().setStatus(StatusVenda.CANCELADA);			
		vendas.saveAndFlush(vendaExistente.get());
	}
	
	@Transactional
	public void orcamento(Venda venda) {
		Optional<Venda> vendaExistente = vendas.findById(venda.getCodigo());
		vendaExistente.get().setStatus(StatusVenda.ORCAMENTO);	
		
		vendas.saveAndFlush(vendaExistente.get());
	}
	
	@Transactional
	public void emitir(Venda venda) {
		Optional<Venda> vendaExistente = vendas.findById(venda.getCodigo());		
		vendaExistente.get().setStatus(StatusVenda.EMITIDA);	
		
		vendas.saveAndFlush(vendaExistente.get());
	}

	public void imprimirCabecalhoCupom(Venda venda) {
		if(venda.getStatus().equals(StatusVenda.EMITIDA) || venda.getStatus().equals(StatusVenda.CREDIARIO)) {
			//if(epsonPrint.conectar()) {
				//epsonPrint.imprimirCabacalho();
			//}
			
			//if(bematechPrinter != null) {
				//bematechPrinter.imprimirCabacalho();
			//}
			
			genericPrinter.imprimirCabacalho();
		}
	}

	public void imprimirItemCupom(Venda venda, String uuid, Produto produto, BigDecimal quantidade) {
		if(venda.getStatus().equals(StatusVenda.EMITIDA) || venda.getStatus().equals(StatusVenda.CREDIARIO)) {			
			//epsonPrint.imprimirItem(uuid, produto, quantidade);
			//bematechPrinter.imprimirItem(uuid, produto, quantidade);
			genericPrinter.imprimirItem(uuid, produto, quantidade);
		}
	}
}