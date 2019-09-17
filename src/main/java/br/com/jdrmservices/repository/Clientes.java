package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.repository.helper.cliente.ClientesQueries;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {
	public Optional<Cliente> findByNomeIgnoreCase(String nome);
	public List<Cliente> findAllByOrderByNomeAsc();
	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
}