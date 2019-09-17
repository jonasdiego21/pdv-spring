package br.com.jdrmservices.repository.helper.usuario;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.jdrmservices.model.Usuario;
import br.com.jdrmservices.repository.filter.UsuarioFilter;

public class UsuariosImpl implements UsuariosQueries {

	@Autowired
	private EntityManager manager;
	
	@Override
	public List<String> permissoes(Usuario usuario) {
		
		String jpql = "select distinct p.nome from Usuario u inner join u.grupo g inner join g.permissoes p where u = :usuario";
		
		List<String> permissoes = manager.createQuery(jpql, String.class)
					.setParameter("usuario", usuario)
					.getResultList();
		
		return permissoes;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
		
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		criteria.addOrder(Order.asc("nome"));
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(UsuarioFilter filtro) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
}
