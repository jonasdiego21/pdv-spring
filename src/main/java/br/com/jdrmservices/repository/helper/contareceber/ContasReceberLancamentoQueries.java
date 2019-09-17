package br.com.jdrmservices.repository.helper.contareceber;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.ContaReceberLancamento;
import br.com.jdrmservices.repository.filter.ContaReceberLancamentoFilter;

public interface ContasReceberLancamentoQueries {
	public Page<ContaReceberLancamento> filtrar(ContaReceberLancamentoFilter filtro, Pageable pageable);
}
