package br.com.jdrmservices.repository.helper.contapagar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.jdrmservices.model.ContaPagarLancamento;
import br.com.jdrmservices.repository.filter.ContaPagarLancamentoFilter;

public class ContasPagarLancamentoImpl implements ContasPagarLancamentoQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<ContaPagarLancamento> filtrar(ContaPagarLancamentoFilter filtro, Pageable pageable) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ContaPagarLancamento.class);
		
		criteria.addOrder(Order.asc("dataPagamento"));
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ContaPagarLancamentoFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ContaPagarLancamento.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ContaPagarLancamentoFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getDataPagamento())) {
				criteria.add(Restrictions.eqOrIsNull("dataPagamento", filtro.getDataPagamento()));
			}
		}
	}
}