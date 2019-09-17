package br.com.jdrmservices.repository.filter;

import java.time.LocalDate;

public class ContaReceberLancamentoFilter {

	private LocalDate dataRecebimento;

	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(LocalDate dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}	
}