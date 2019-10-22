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
import br.com.jdrmservices.dto.TotalVendasAnoCrediario;
import br.com.jdrmservices.dto.TotalVendasAnoGeral;
import br.com.jdrmservices.dto.TotalVendasDia;
import br.com.jdrmservices.dto.TotalVendasDiaCrediario;
import br.com.jdrmservices.dto.TotalVendasDiaGeral;
import br.com.jdrmservices.dto.TotalVendasMes;
import br.com.jdrmservices.dto.TotalVendasMesCrediario;
import br.com.jdrmservices.dto.TotalVendasMesGeral;
import br.com.jdrmservices.model.Venda;
import br.com.jdrmservices.model.enumeration.Status;
import br.com.jdrmservices.model.enumeration.StatusVenda;
import br.com.jdrmservices.repository.filter.VendaFilter;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;
	

	@Override
	public BigDecimal valorTotalCaixa() {		
		Optional<BigDecimal> optionalVendas = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where day(dataCriacao) = :dia  and status = :status", BigDecimal.class)
				.setParameter("dia", LocalDate.now().getDayOfMonth())
				.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		
		Optional<BigDecimal> optionalContaReceber = Optional.ofNullable(manager
				.createQuery("select sum(totalRecebido) from ContaReceberLancamento where day(dataRecebimento) = :dia", BigDecimal.class)
				.setParameter("dia", LocalDate.now().getDayOfMonth())
				.getSingleResult());
		
		return optionalVendas.orElse(BigDecimal.ZERO).add(optionalContaReceber.orElse(BigDecimal.ZERO));
	}

	@Override
	public BigDecimal valorTotalDespesas() {
		Optional<BigDecimal> optionalContaPagar = Optional.ofNullable(manager
				.createQuery("select sum(totalCompra) from ContaPagar where day(dataVencimento) = :dia and status = :status", BigDecimal.class)
				.setParameter("dia", LocalDate.now().getDayOfMonth())
				.setParameter("status", Status.DEVENDO)
				.getSingleResult());
		
		return optionalContaPagar.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalSaldo() {
		return valorTotalCaixa().subtract(valorTotalDespesas());
	}
	
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
	@SuppressWarnings("unchecked")
	public List<TotalVendasDiaCrediario> totalVendasDiaCrediario() {
		List<TotalVendasDiaCrediario> totalDiaCrediario = manager.createNamedQuery("Vendas.totalVendasDiaCrediario").getResultList();	
		return totalDiaCrediario;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasMesCrediario> totalVendasMesCrediario() {
		List<TotalVendasMesCrediario> totalMesCrediario = manager.createNamedQuery("Vendas.totalVendasMesCrediario").getResultList();	
		return totalMesCrediario;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasAnoCrediario> totalVendasAnoCrediario() {
		List<TotalVendasAnoCrediario> totalAnoCrediario = manager.createNamedQuery("Vendas.totalVendasAnoCrediario").getResultList();	
		return totalAnoCrediario;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasDiaGeral> totalVendasDiaGeral() {
		List<TotalVendasDiaGeral> totalDiaGeral = manager.createNamedQuery("Vendas.totalVendasDiaGeral").getResultList();	
		return totalDiaGeral;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasMesGeral> totalVendasMesGeral() {
		List<TotalVendasMesGeral> totalMesGeral = manager.createNamedQuery("Vendas.totalVendasMesGeral").getResultList();	
		return totalMesGeral;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TotalVendasAnoGeral> totalVendasAnoGeral() {
		List<TotalVendasAnoGeral> totalAnoGeral = manager.createNamedQuery("Vendas.totalVendasAnoGeral").getResultList();	
		return totalAnoGeral;
	}
	
	@Override
	public BigDecimal valorTotalDiaCrediario() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where day(dataCriacao) = :dia  and status = :status", BigDecimal.class)
				.setParameter("dia", LocalDate.now().getDayOfMonth())
				.setParameter("status", StatusVenda.CREDIARIO)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalMesCrediario() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status = :status", BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue())
				.setParameter("status", StatusVenda.CREDIARIO)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalAnoCrediario() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano  and status = :status", BigDecimal.class)
				.setParameter("ano", Year.now().getValue())
				.setParameter("status", StatusVenda.CREDIARIO)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTotalDiaGeral() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where day(dataCriacao) = :dia and status != :status", BigDecimal.class)
				.setParameter("dia", LocalDate.now().getDayOfMonth())
				.setParameter("status", StatusVenda.CANCELADA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalMesGeral() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status != :status", BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue())
				.setParameter("status", StatusVenda.CANCELADA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalAnoGeral() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano and status != :status", BigDecimal.class)
				.setParameter("ano", Year.now().getValue())
				.setParameter("status", StatusVenda.CANCELADA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
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
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status = :status", BigDecimal.class)
				.setParameter("mes", MonthDay.now().getMonthValue())
				.setParameter("status", StatusVenda.EMITIDA)
				.getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalAno() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano  and status = :status", BigDecimal.class)
				.setParameter("ano", Year.now().getValue())
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
			if(!StringUtils.isEmpty(filtro.getStatus())) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
		}
		
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getCodigoCliente())) {
				criteria.add(Restrictions.eq("cliente.codigo", filtro.getCodigoCliente()));
			}
		}
		
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getDataInicio())) {
				criteria.add(Restrictions.ge("dataCriacao", filtro.getDataInicio()));
			}
		}
		
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getDataFim())) {
				criteria.add(Restrictions.le("dataCriacao", filtro.getDataFim()));
			}
		}
	}
}