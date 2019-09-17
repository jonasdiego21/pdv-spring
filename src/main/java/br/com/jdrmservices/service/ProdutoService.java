package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jdrmservices.exception.GlobalException;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.repository.Produtos;

@Service
public class ProdutoService {

	@Autowired
	private Produtos produtos;
	
	@Transactional
	public void cadastrar(Produto produto) {
		Optional<Produto> optional = produtos.findByNomeIgnoreCase(produto.getNome());
		
		if(produto.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		produtos.saveAndFlush(produto);
	}
	
	@Transactional
	public void excluir(Produto produto) {
		produtos.delete(produto);
	}	
}
