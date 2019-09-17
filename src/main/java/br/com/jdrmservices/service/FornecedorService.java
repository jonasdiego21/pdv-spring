package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Fornecedor;
import br.com.jdrmservices.repository.Fornecedores;

@Service
public class FornecedorService {

	@Autowired
	private Fornecedores fornecedores;
	
	@Transactional
	public void cadastrar(Fornecedor fornecedor) {
		Optional<Fornecedor> optional = fornecedores.findByNomeIgnoreCase(fornecedor.getNome());
		
		if(fornecedor.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		fornecedores.saveAndFlush(fornecedor);
	}
	
	@Transactional
	public void excluir(Fornecedor fornecedor) {
		fornecedores.delete(fornecedor);
	}	
}
