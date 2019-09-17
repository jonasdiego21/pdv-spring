package br.com.jdrmservices.repository.helper.contareceber;

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

import br.com.jdrmservices.model.ContaReceberLancamento;
import br.com.jdrmservices.repository.filter.ContaReceberLancamentoFilter;

public class ContasReceberLancamentoImpl implements ContasReceberLancamentoQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<ContaReceberLancamento> filtrar(ContaReceberLancamentoFilter filtro, Pageable pageable) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ContaReceberLancamento.class);
		
		criteria.addOrder(Order.asc("dataRecebimento"));
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ContaReceberLancamentoFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(ContaReceberLancamento.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ContaReceberLancamentoFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getDataRecebimento())) {
				criteria.add(Restrictions.eqOrIsNull("dataRecebimento", filtro.getDataRecebimento()));
			}
		}
	}
}
