package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Funcionario;
import br.com.jdrmservices.repository.helper.funcionario.FuncionariosQueries;

@Repository
public interface Funcionarios extends JpaRepository<Funcionario, Long>, FuncionariosQueries {
	public Optional<Funcionario> findByNomeIgnoreCase(String nome);
	public List<Funcionario> findAllByOrderByNomeAsc();
}