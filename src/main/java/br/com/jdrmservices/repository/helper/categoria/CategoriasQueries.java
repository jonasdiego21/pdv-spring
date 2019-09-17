package br.com.jdrmservices.repository.helper.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Categoria;
import br.com.jdrmservices.repository.filter.CategoriaFilter;

public interface CategoriasQueries {
	public Page<Categoria> filtrar(CategoriaFilter filtro, Pageable pageable);
}