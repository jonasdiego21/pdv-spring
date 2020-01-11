package br.com.jdrmservices.repository.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.jdrmservices.model.enumeration.StatusVenda;

public class VendaFilter {

	private StatusVenda status;
	private Long codigoCliente;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;

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

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}
}