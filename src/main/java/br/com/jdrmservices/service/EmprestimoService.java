package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Emprestimo;
import br.com.jdrmservices.repository.Emprestimos;

@Service
public class EmprestimoService {

	@Autowired
	private Emprestimos emprestimos;
	
	@Transactional
	public void cadastrar(Emprestimo emprestimo) {
		Optional<Emprestimo> optional = emprestimos.findByNomeIgnoreCase(emprestimo.getNome());
		
		if(emprestimo.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		emprestimos.saveAndFlush(emprestimo);
	}
	
	@Transactional
	public void excluir(Emprestimo emprestimo) {
		emprestimos.delete(emprestimo);
	}	
}