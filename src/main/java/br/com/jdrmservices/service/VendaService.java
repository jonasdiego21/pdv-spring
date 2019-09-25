package br.com.jdrmservices.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.FormaPagamento;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.model.enumeration.StatusVenda;
import br.com.jdrmservices.repository.ContasReceber;
import br.com.jdrmservices.repository.Vendas;
import br.com.jdrmservices.service.event.venda.FechamentoCupomEvent;
import br.com.jdrmservices.service.event.venda.VendaEvent;

@Service
public class VendaService {

	@Autowired
	private Vendas vendas;
	
	@Autowired
	private ContasReceber contasReceber;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void cadastrar(Venda venda) {	
		if(venda.getFormaPagamento().equals(FormaPagamento.CREDIARIO)) {
			venda.setStatus(StatusVenda.CREDIARIO);
			Optional<ContaReceber> contaReceberOptional = contasReceber.findByClienteNomeIgnoreCase(venda.getCliente().getNome());
			
			if(contaReceberOptional.isPresent()) {
				contaReceberOptional.get().setCodigo(contaReceberOptional.get().getCodigo());
				contaReceberOptional.get().setTotalVenda(venda.getValorTotal().add(contaReceberOptional.get().getTotalVenda()));

				if(venda.getCliente().getLimiteCompra().doubleValue() >= venda.getValorTotal().add(contaReceberOptional.get().getRestante()).doubleValue()) {					
					System.out.println("O CLIENTE ATINGIU O LIMITE DE CRÉDITO!");
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
		
		vendas.saveAndFlush(venda);
	}
	
	@Transactional
	public void cancelaVendaEmitida(Venda venda) {
		venda.setStatus(StatusVenda.CANCELADA);
		vendas.saveAndFlush(venda);
	}
	
	@Transactional
	public void excluir(Venda venda) {
		vendas.delete(venda);
	}	
}