package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Fornecedor;
import br.com.jdrmservices.repository.helper.fornecedor.FornecedoresQueries;

@Repository
public interface Fornecedores extends JpaRepository<Fornecedor, Long>, FornecedoresQueries {
	public Optional<Fornecedor> findByNomeIgnoreCase(String nome);
	public List<Fornecedor> findAllByOrderByNomeAsc();
}