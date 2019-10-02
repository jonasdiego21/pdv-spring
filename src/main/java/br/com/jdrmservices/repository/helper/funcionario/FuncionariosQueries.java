package br.com.jdrmservices.repository.helper.funcionario;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jdrmservices.model.Funcionario;
import br.com.jdrmservices.repository.filter.FuncionarioFilter;

public interface FuncionariosQueries {
	public Page<Funcionario> filtrar(FuncionarioFilter filtro, Pageable pageable);
	public List<Funcionario> comissaoFuncionario();
}