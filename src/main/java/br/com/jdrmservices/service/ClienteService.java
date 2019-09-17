package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.repository.Clientes;

@Service
public class ClienteService {

	@Autowired
	private Clientes clientes;
	
	@Transactional
	public void cadastrar(Cliente cliente) {
		Optional<Cliente> optional = clientes.findByNomeIgnoreCase(cliente.getNome());
		
		if(cliente.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		clientes.saveAndFlush(cliente);
	}
	
	@Transactional
	public void excluir(Cliente cliente) {
		clientes.delete(cliente);
	}	
}