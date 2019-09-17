package br.com.jdrmservices.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.jdrmservices.util.Constants;

@Entity
@Table(name = "conta_pagar_lancamento")
public class ContaPagarLancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	private ContaPagar contaPagar;
	
	@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Column(name = "data_pagamento", columnDefinition = "DATE")
	private LocalDate dataPagamento = LocalDate.now();
	
	@Column(name = "total_pago")
	private BigDecimal totalPago = BigDecimal.ZERO;

	public boolean isNovo() {
		return this.codigo == null;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public ContaPagar getContaPagar() {
		return contaPagar;
	}

	public void setContaPagar(ContaPagar contaPagar) {
		this.contaPagar = contaPagar;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaPagarLancamento other = (ContaPagarLancamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
