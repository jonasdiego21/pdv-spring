package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.repository.helper.produto.ProdutosQueries;

@Repository
public interface Produtos extends JpaRepository<Produto, Long>, ProdutosQueries {
	public Optional<Produto> findByNomeIgnoreCase(String nome);
	public List<Produto> findAllByOrderByNomeAsc();
	public Produto findByCodigo(Long codigoProduto);
	public Produto findByCodigoBarras(String codigoBarras);
}
