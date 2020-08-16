package br.com.jdrmservices.repository.helper.cliente;

import java.time.LocalDate;
import java.util.Date;
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

import com.ibm.icu.text.DecimalFormat;

import br.com.jdrmservices.dto.ClientesAniversariantes;
import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.repository.filter.ClienteFilter;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		
		criteria.addOrder(Order.asc("nome"));
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ClienteFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ClienteFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	@Override
	public List<ClientesAniversariantes> findByClientesAniversariantes() {
		DecimalFormat decimalFormat = new DecimalFormat("00");
		List<ClientesAniversariantes> clientesAniversariantes = manager
				.createQuery("select new br.com.jdrmservices.dto.ClientesAniversariantes(codigo, nome, dataNascimento) "
							+ "from Cliente where dataNascimento like :dataNascimento", ClientesAniversariantes.class)
				.setParameter("dataNascimento", decimalFormat.format(LocalDate.now().getDayOfMonth()) + "/" 
							+ decimalFormat.format(LocalDate.now().getMonthValue()) + "%")
				.getResultList();
		
		return clientesAniversariantes;
	}
}