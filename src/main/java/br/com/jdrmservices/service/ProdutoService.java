package br.com.jdrmservices.service;

import static br.com.jdrmservices.util.Constants.INFORMACOES_JA_CADASTRADAS;
import static br.com.jdrmservices.util.Constants.INFORMACOES_NAO_CADASTRADO;

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
		Optional<Produto> optional = produtos.findByCodigoBarrasIgnoreCase(produto.getCodigoBarras());
		
		if(produto.isNovo() && optional.isPresent()) {
			throw new GlobalException(INFORMACOES_JA_CADASTRADAS);
		}
		
		produtos.saveAndFlush(produto);
	}
	
	@Transactional
	public void adicionarEntrada(Produto produto) {
		Optional<Produto> optional = produtos.findByCodigoBarrasIgnoreCase(produto.getCodigoBarras());
		
		if(!optional.isPresent()) {
			throw new GlobalException(INFORMACOES_NAO_CADASTRADO);
		}
		
		produto.setCodigo(optional.get().getCodigo());
		produto.setCodigoBarras(optional.get().getCodigoBarras());
		produto.setNome(optional.get().getNome());
		produto.setUnidade(optional.get().getUnidade());
		produto.setDescricao(optional.get().getDescricao());
		produto.setQuantidade(optional.get().getQuantidade().add(produto.getQuantidade()));
		produto.setPrecoCompra(optional.get().getPrecoCompra());
		produto.setPrecoVenda(optional.get().getPrecoVenda());
		produto.setCategoria(optional.get().getCategoria());
		produto.setFornecedor(optional.get().getFornecedor());
		
		produtos.saveAndFlush(produto);
	}	

	@Transactional
	public void excluir(Produto produto) {
		produtos.delete(produto);
	}
}
