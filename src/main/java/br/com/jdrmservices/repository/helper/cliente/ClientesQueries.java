package br.com.jdrmservices.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.repository.filter.ClienteFilter;

public interface ClientesQueries {
	public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable);
}