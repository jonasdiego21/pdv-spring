package br.com.jdrmservices.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.model.ContaPagarLancamento;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.repository.ContasPagar;
import br.com.jdrmservices.repository.ContasPagarLancamento;

@Service
public class ContaPagarLancamentoService {

	@Autowired
	private ContasPagarLancamento contasPagarLancamento;
	
	@Autowired
	private ContasPagar contasPagar;
	
	@Transactional
	public void cadastrar(ContaPagarLancamento contaPagarLancamento) {				
		contasPagarLancamento.saveAndFlush(contaPagarLancamento);
		
		Optional<ContaPagar> contaPagar = contasPagar.findById(contaPagarLancamento.getContaPagar().getCodigo());
		contaPagar.get().setTotalPago(contaPagarLancamento.getTotalPago().add(contaPagar.get().getTotalPago()));
		
		if(contaPagar.get().getRestante().doubleValue() <= 0 && contaPagar.get().getStatus().equals(Status.DEVENDO)) {
			contaPagar.get().setStatus(Status.PAGO);
			//contaPagar.get().setTotalCompra(new BigDecimal("0.000"));
			//contaPagar.get().setTotalPago(new BigDecimal("0.000"));
		}
		
		if(contaPagar.get().getStatus().equals(Status.DEVENDO)) {
			alterarValorPago(contaPagar);
		}
	}
	
	@Transactional
	public void alterarValorPago(Optional<ContaPagar> contaPagar) {
		contasPagar.saveAndFlush(contaPagar.get());		
	}
	
	@Transactional
	public void excluir(ContaPagarLancamento contaPagarLancamento) {
		contasPagarLancamento.delete(contaPagarLancamento);
	}	
}
