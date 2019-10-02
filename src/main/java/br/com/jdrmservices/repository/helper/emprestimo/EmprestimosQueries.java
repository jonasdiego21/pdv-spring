package br.com.jdrmservices.repository.helper.emprestimo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Emprestimo;
import br.com.jdrmservices.repository.filter.EmprestimoFilter;

public interface EmprestimosQueries {
	public Page<Emprestimo> filtrar(EmprestimoFilter filtro, Pageable pageable);
}