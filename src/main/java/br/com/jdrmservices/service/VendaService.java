package br.com.jdrmservices.service;

import java.math.BigDecimal;
import java.util.Optional;
//import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

//import br.com.jdrmservices.epson.EpsonPrint;
import br.com.jdrmservices.exception.GlobalException;
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
//import br.com.jdrmservices.repository.Produtos;
import br.com.jdrmservices.repository.Vendas;
import br.com.jdrmservices.service.event.venda.FechamentoCupomEvent;
import br.com.jdrmservices.service.event.venda.VendaEvent;

@Service
public class VendaService {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ItensVendas itensVendas;
	
	//@Autowired
	//private Produtos produtos;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ContasReceber contasReceber;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//@Autowired
	//private EpsonPrint epsonPrint;
	
	@Autowired
	private GenericPrinter genericPrinter;
	
	@Transactional
	public void cadastrar(Venda venda) {	
		if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
			venda.setStatus(StatusVenda.CREDIARIO);
			
			Optional<ContaReceber> contaReceberOptional = contasReceber.findByClienteNomeIgnoreCase(venda.getCliente().getNome());
			
			if(contaReceberOptional.isPresent()) {
				contaReceberOptional.get().setCodigo(contaReceberOptional.get().getCodigo());
				contaReceberOptional.get().setTotalVenda(venda.getValorTotal().add(contaReceberOptional.get().getTotalVenda()));

				if(venda.getCliente().getLimiteCompra().doubleValue() >= venda.getValorTotal()
						.add(contaReceberOptional.get().getRestante()).doubleValue()) {					
					
					publisher.publishEvent(new VendaEvent(venda));
					publisher.publishEvent(new FechamentoCupomEvent(venda));
					
					throw new GlobalException("LIMITE DE CREDITO ATINGIDO!");
				}				

				contasReceber.saveAndFlush(contaReceberOptional.get());
			} else {
				ContaReceber contaReceber = new ContaReceber();
				
				contaReceber.setCliente(venda.getCliente());
				contaReceber.setTotalVenda(venda.getValorTotal());
				contaReceber.setDataVenda(venda.getDataCriacao());
				contaReceber.setDataVencimento(venda.getDataCriacao().plusDays(30));
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
		
		// desconto
		venda.setValorTotal(venda.getValorTotal().subtract(venda.getValorDesconto()));
		
		vendas.saveAndFlush(venda);
	}
	
	@Transactional
	public void excluir(Venda venda) {
		vendas.delete(venda);
	}
	
	@Transactional
	public void devolverItem(ItemVenda itemVenda) {
		Optional<Venda> vendaExistente = vendas.findById(itemVenda.getVenda().getCodigo());
		
		System.out.println("Item venda: " + itemVenda.getProduto().getNome());
		
		for(ItemVenda it : vendaExistente.get().getItens()) {						
			produtoService.adicionarEntrada(it.getProduto());
		}
		
		itensVendas.delete(itemVenda);
	}
		
	@Transactional
	public void cancelar(Venda venda) {
		Optional<Venda> vendaExistente = vendas.findById(venda.getCodigo());
		
		for(ItemVenda it : vendaExistente.get().getItens()) {						
			produtoService.adicionarEntrada(it.getProduto());
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
			
			genericPrinter.imprimirCabacalho();
		}
	}

	public void imprimirItemCupom(Venda venda, String uuid, Produto produto, BigDecimal quantidade) {
		if(venda.getStatus().equals(StatusVenda.EMITIDA) || venda.getStatus().equals(StatusVenda.CREDIARIO)) {			
			//epsonPrint.imprimirItem(uuid, produto, quantidade);
			genericPrinter.imprimirItem(uuid, produto, quantidade);
		}
	}	
}