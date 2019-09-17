package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.repository.ContasPagar;

@Service
public class ContaPagarService {

	@Autowired
	private ContasPagar contasPagar;
	
	@Transactional
	public void cadastrar(ContaPagar contaPagar) {
		Optional<ContaPagar> optional = contasPagar.findByFornecedorNomeIgnoreCase(contaPagar.getFornecedor().getNome());
		
		if(contaPagar.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		contasPagar.saveAndFlush(contaPagar);
	}
	
	@Transactional
	public void excluir(ContaPagar contaPagar) {
		contasPagar.delete(contaPagar);
	}	
}