package br.com.jdrmservices.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jdrmservices.model.ContaReceberLancamento;
import br.com.jdrmservices.repository.helper.contareceber.ContasReceberLancamentoQueries;

@Repository
public interface ContasReceberLancamento extends JpaRepository<ContaReceberLancamento, Long>, ContasReceberLancamentoQueries {
	public Optional<ContaReceberLancamento> findByDataRecebimento(LocalDate dataRecebimento);
	public List<ContaReceberLancamento> findAllByOrderByDataRecebimentoAsc();
}