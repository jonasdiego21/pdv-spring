package br.com.jdrmservices.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.model.ContaReceberLancamento;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.repository.ContasReceber;
import br.com.jdrmservices.repository.ContasReceberLancamento;

@Service
public class ContaReceberLancamentoService {

	@Autowired
	private ContasReceberLancamento contasReceberLancamento;
	
	@Autowired
	private ContasReceber contasReceber;
	
	@Transactional
	public void cadastrar(ContaReceberLancamento contaReceberLancamento) {	
		contasReceberLancamento.saveAndFlush(contaReceberLancamento);
		
		Optional<ContaReceber> contaReceber = contasReceber.findById(contaReceberLancamento.getContaReceber().getCodigo());
		contaReceber.get().setTotalRecebido(contaReceberLancamento.getTotalRecebido().add(contaReceber.get().getTotalRecebido()));
		
		if(contaReceber.get().getRestante().doubleValue() <= 0) {
			contaReceber.get().setStatus(Status.PAGO);
			contaReceber.get().setTotalVenda(new BigDecimal("0.000"));
			contaReceber.get().setTotalRecebido(new BigDecimal("0.000"));
			contaReceber.get().setRestante(contaReceber.get().getCliente().getLimiteCompra());
		}
		
		if(contaReceber.get().getStatus().equals(Status.DEVENDO)) {
			alterarValorReceber(contaReceber);
		}
	}
	
	@Transactional
	public void alterarValorReceber(Optional<ContaReceber> contaReceber) {
		contasReceber.saveAndFlush(contaReceber.get());		
	}
	
	@Transactional
	public void excluir(ContaReceberLancamento contaReceberLancamento) {
		contasReceberLancamento.delete(contaReceberLancamento);
	}	
}