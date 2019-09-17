package br.com.jdrmservices.repository.helper.empresa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Empresa;
import br.com.jdrmservices.repository.filter.EmpresaFilter;

public interface EmpresasQueries {
	public Page<Empresa> filtrar(EmpresaFilter filtro, Pageable pageable);
}