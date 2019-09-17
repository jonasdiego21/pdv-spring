package br.com.jdrmservices.repository.filter;

import java.time.LocalDate;

public class ContaPagarLancamentoFilter {

	private LocalDate dataPagamento;

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}	
}