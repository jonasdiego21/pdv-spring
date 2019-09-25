package br.com.jdrmservices.repository.helper.venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.jdrmservices.dto.TotalVendasAno;
import br.com.jdrmservices.dto.TotalVendasDia;
import br.com.jdrmservices.dto.TotalVendasMes;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.StatusVenda;
import br.com.jdrmservices.repository.filter.VendaFilter;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasDia> totalVendasDia() {
		List<TotalVendasDia> totalDia = manager.createNamedQuery("Vendas.totalVendasDia").getResultList();	
		return totalDia;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasMes> totalVendasMes() {
		List<TotalVendasMes> totalMes = manager.createNamedQuery("Vendas.totalVendasMes").getResultList();	
		return totalMes;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasAno> totalVendasAno() {
		List<TotalVendasAno> totalAno = manager.createNamedQuery("Vendas.totalVendasAno").getResultList();	
		return totalAno;
	}
	
	@Override
	public BigDecimal valorTotalDia() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where day(dataCriacao) = :dia  and status = :status", BigDecimal.class)
				.setParameter("dia", LocalDate.now().getDayOfMonth())
				.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalMes() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano  and status = :status", BigDecimal.class)
				.setParameter("ano", Year.now().getValue())
				.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status = :status", BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue())
				.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		Page<Venda> page = new PageImpl<>(criteria.list(), pageable, total(filtro));
		
		return page;
	}

	private Long total(VendaFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(VendaFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("cliente.codigo", filtro.getCodigo()));
			}
		}
	}
}