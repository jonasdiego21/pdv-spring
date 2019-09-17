package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.repository.helper.contareceber.ContasReceberQueries;

@Repository
public interface ContasReceber extends JpaRepository<ContaReceber, Long>, ContasReceberQueries {
	public Optional<ContaReceber> findByClienteNomeIgnoreCase(String nome);
	public List<ContaReceber> findAllByOrderByClienteAsc();
}