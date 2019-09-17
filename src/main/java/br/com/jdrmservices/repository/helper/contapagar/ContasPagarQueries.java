package br.com.jdrmservices.repository.helper.contapagar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.ContaPagar;
import br.com.jdrmservices.repository.filter.ContaPagarFilter;

public interface ContasPagarQueries {
	public Page<ContaPagar> filtrar(ContaPagarFilter filtro, Pageable pageable);
}