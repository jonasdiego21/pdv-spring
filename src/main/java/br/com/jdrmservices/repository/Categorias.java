package br.com.jdrmservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.Categoria;
import br.com.jdrmservices.repository.helper.categoria.CategoriasQueries;

@Repository
public interface Categorias extends JpaRepository<Categoria, Long>, CategoriasQueries {
	public Optional<Categoria> findByNomeIgnoreCase(String nome);
	public List<Categoria> findAllByOrderByNomeAsc();
}