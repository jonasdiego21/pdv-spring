package br.com.jdrmservices.repository.helper.produto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.dto.ProdutoDTO;
import br.com.jdrmservices.dto.ProdutosBaixoEstoque;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.repository.filter.ProdutoFilter;

public interface ProdutosQueries {
	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable);
	public List<ProdutoDTO> porCodigoOuCodigoBarras(String codigoOuCodigoBarras);
	public List<ProdutosBaixoEstoque> findByProdutosQuantidadeMenorQueCinco();
}