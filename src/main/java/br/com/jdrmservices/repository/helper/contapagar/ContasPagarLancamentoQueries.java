package br.com.jdrmservices.repository.helper.contapagar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.ContaPagarLancamento;
import br.com.jdrmservices.repository.filter.ContaPagarLancamentoFilter;

public interface ContasPagarLancamentoQueries {
	public Page<ContaPagarLancamento> filtrar(ContaPagarLancamentoFilter filtro, Pageable pageable);
}
