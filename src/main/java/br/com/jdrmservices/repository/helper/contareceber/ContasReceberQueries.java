package br.com.jdrmservices.repository.helper.contareceber;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.ContaReceber;
import br.com.jdrmservices.repository.filter.ContaReceberFilter;

public interface ContasReceberQueries {
	public Page<ContaReceber> filtrar(ContaReceberFilter filtro, Pageable pageable);
}