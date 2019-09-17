package br.com.jdrmservices.repository.helper.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Fornecedor;
import br.com.jdrmservices.repository.filter.FornecedorFilter;

public interface FornecedoresQueries {
	public Page<Fornecedor> filtrar(FornecedorFilter filtro, Pageable pageable);
}