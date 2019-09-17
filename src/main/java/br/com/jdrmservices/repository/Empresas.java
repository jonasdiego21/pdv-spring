package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Empresa;
import br.com.jdrmservices.repository.helper.empresa.EmpresasQueries;

@Repository
public interface Empresas extends JpaRepository<Empresa, Long>, EmpresasQueries {
	public Optional<Empresa> findByNomeIgnoreCase(String nome);
	public List<Empresa> findAllByOrderByNomeAsc();
}