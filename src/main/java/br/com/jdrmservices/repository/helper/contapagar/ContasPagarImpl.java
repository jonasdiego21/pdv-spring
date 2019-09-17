package br.com.jdrmservices.repository.helper.contapagar;

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

import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.repository.filter.ContaPagarFilter;

public class ContasPagarImpl implements ContasPagarQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<ContaPagar> filtrar(ContaPagarFilter filtro, Pageable pageable) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ContaPagar.class);
		
		criteria.addOrder(Order.asc("fornecedor"));
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ContaPagarFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ContaPagar.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ContaPagarFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getFornecedor())) {
				criteria.add(Restrictions.ilike("fornecedor", filtro.getFornecedor(), MatchMode.ANYWHERE));
			}
		}
	}
}