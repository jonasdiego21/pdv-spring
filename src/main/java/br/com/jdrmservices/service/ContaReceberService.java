package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.repository.ContasReceber;

@Service
public class ContaReceberService {

	@Autowired
	private ContasReceber contasReceber;
	
	@Transactional
	public void cadastrar(ContaReceber contaReceber) {
		Optional<ContaReceber> optional = contasReceber.findByClienteNomeIgnoreCase(contaReceber.getCliente().getNome());
		
		if(contaReceber.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		contasReceber.saveAndFlush(contaReceber);
	}
	
	@Transactional
	public void excluir(ContaReceber contaReceber) {
		contasReceber.delete(contaReceber);
	}	
}