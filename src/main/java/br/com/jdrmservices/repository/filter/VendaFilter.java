package br.com.jdrmservices.repository.filter;

import java.time.LocalDate;

import br.com.jdrmservices.model.enumeration.StatusVenda;

public class VendaFilter {

	private StatusVenda status;
	private Long codigoCliente;
	private LocalDate dataInicio;
	private LocalDate dataFim;

	public StatusVenda getStatus() {
		return status;
	}

	public void setStatus(StatusVenda status) {
		this.status = status;
	}

	public Long getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
}