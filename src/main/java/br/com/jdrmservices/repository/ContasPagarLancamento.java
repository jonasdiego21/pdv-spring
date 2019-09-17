package br.com.jdrmservices.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.ContaPagarLancamento;
import br.com.jdrmservices.repository.helper.contapagar.ContasPagarLancamentoQueries;

@Repository
public interface ContasPagarLancamento extends JpaRepository<ContaPagarLancamento, Long>, ContasPagarLancamentoQueries {
	public Optional<ContaPagarLancamento> findByDataPagamento(LocalDate dataPagamento);
	public List<ContaPagarLancamento> findAllByOrderByDataPagamentoAsc();
}