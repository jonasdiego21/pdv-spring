package br.com.jdrmservices.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.repository.ContasReceber;

@Service
public class ContaReceberService {

	@Autowired
	private ContasReceber contasReceber;
	
	@Transactional
	public void cadastrar(ContaReceber contaReceber) {
		Optional<ContaReceber> contaReceberExistente = 
				contasReceber.findByClienteNomeIgnoreCaseAndStatus(contaReceber.getCliente().getNome(), Status.DEVENDO);
		
		if(contaReceberExistente.isPresent() && contaReceberExistente.get().getStatus().equals(Status.DEVENDO)) {
			contaReceber.setTotalVenda(contaReceberExistente.get().getTotalVenda().add(contaReceber.getTotalVenda()));
			contaReceber.setTotalRecebido(contaReceberExistente.get().getTotalRecebido());
			contaReceber.setRestante(contaReceberExistente.get().getRestante());
			contaReceber.setCodigo(contaReceberExistente.get().getCodigo());
			
			contasReceber.saveAndFlush(contaReceber);
		}
		
		contasReceber.saveAndFlush(contaReceber);
	}
	
	@Transactional
	public void excluir(ContaReceber contaReceber) {
		contasReceber.delete(contaReceber);
	}	
}