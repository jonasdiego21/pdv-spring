package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.repository.helper.contapagar.ContasPagarQueries;

@Repository
public interface ContasPagar extends JpaRepository<ContaPagar, Long>, ContasPagarQueries {
	public Optional<ContaPagar> findByFornecedorNomeIgnoreCase(String fornecedor);
	public List<ContaPagar> findAllByOrderByFornecedorAsc();
}