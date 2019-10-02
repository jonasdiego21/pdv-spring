package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Emprestimo;
import br.com.jdrmservices.repository.helper.emprestimo.EmprestimosQueries;

@Repository
public interface Emprestimos extends JpaRepository<Emprestimo, Long>, EmprestimosQueries {
	public Optional<Emprestimo> findByNomeIgnoreCase(String nome);
	public List<Emprestimo> findAllByOrderByNomeAsc();
}