package br.com.jdrmservices.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.repository.ContasPagar;

@Service
public class ContaPagarService {

	@Autowired
	private ContasPagar contasPagar;
	
	@Transactional
	public void cadastrar(ContaPagar contaPagar) {
		Optional<ContaPagar> contaPagarExistente = 
				contasPagar.findByFornecedorNomeIgnoreCaseAndStatus(contaPagar.getFornecedor().getNome(), Status.DEVENDO);
		
		if(contaPagarExistente.isPresent() && contaPagarExistente.get().getStatus().equals(Status.DEVENDO)) {
			contaPagar.setTotalCompra(contaPagarExistente.get().getTotalCompra().add(contaPagar.getTotalCompra()));
			contaPagar.setTotalPago(contaPagarExistente.get().getTotalPago());
			contaPagar.setRestante(contaPagarExistente.get().getRestante());
			contaPagar.setCodigo(contaPagarExistente.get().getCodigo());
			
			contasPagar.saveAndFlush(contaPagar);
		}
		
		contasPagar.saveAndFlush(contaPagar);
	}
	
	@Transactional
	public void excluir(ContaPagar contaPagar) {
		contasPagar.delete(contaPagar);
	}	
}