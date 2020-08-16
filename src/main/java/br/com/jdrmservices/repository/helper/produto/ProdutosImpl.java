package br.com.jdrmservices.repository.helper.produto;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.jdrmservices.dto.ProdutoDTO;
import br.com.jdrmservices.dto.ProdutosBaixoEstoque;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.repository.filter.ProdutoFilter;

public class ProdutosImpl implements ProdutosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<ProdutoDTO> porCodigoOuCodigoBarras(String codigoOuCodigoBarras) {
		String jpql = "select new br.com.jdrmservices.dto.ProdutoDTO(codigo, codigoBarras, nome, precoVenda, quantidade, unidade) "
				+ "from Produto where codigoBarras = :codigoOuCodigoBarras or nome = :codigoOuCodigoBarras";
		
		List<ProdutoDTO> produtoFiltrado = manager.createQuery(jpql, ProdutoDTO.class)
				.setParameter("codigoOuCodigoBarras", codigoOuCodigoBarras)
				.getResultList();
		
		return produtoFiltrado;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);
		
		criteria.addOrder(Order.asc("nome"));
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ProdutoFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ProdutoFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if(!StringUtils.isEmpty(filtro.getCodigoBarras())) {
				criteria.add(Restrictions.ilike("codigoBarras", filtro.getCodigoBarras(), MatchMode.EXACT));
			}
		}
	}

	@Override
	public List<ProdutosBaixoEstoque> findByProdutosQuantidadeMenorQueCinco() {
		List<ProdutosBaixoEstoque> produtos = manager
				.createQuery("select new br.com.jdrmservices.dto.ProdutosBaixoEstoque(codigo, codigoBarras, nome, quantidade) from Produto where quantidade <= :quantidadeMinima", ProdutosBaixoEstoque.class)
				.setParameter("quantidadeMinima", new BigDecimal("5.000"))
				.getResultList();
		
		return produtos;
	}
}
