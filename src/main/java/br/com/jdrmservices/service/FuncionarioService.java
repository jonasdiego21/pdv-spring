package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Funcionario;
import br.com.jdrmservices.repository.Funcionarios;

@Service
public class FuncionarioService {

	@Autowired
	private Funcionarios funcionarios;
	
	@Transactional
	public void cadastrar(Funcionario funcionario) {
		Optional<Funcionario> optional = funcionarios.findByNomeIgnoreCase(funcionario.getNome());
		
		if(funcionario.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		funcionarios.saveAndFlush(funcionario);
	}
	
	@Transactional
	public void excluir(Funcionario funcionario) {
		funcionarios.delete(funcionario);
	}	
}