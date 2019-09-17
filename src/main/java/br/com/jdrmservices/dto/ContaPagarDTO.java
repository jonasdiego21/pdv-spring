package br.com.jdrmservices.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ContaPagarDTO {

	private Long codigoContaPagar;
	private BigDecimal totalCompra;
	private BigDecimal totalPago;
	
	public Long getCodigoContaPagar() {
		return codigoContaPagar;
	}
	
	public void setCodigoContaPagar(Long codigoContaPagar) {
		this.codigoContaPagar = codigoContaPagar;
	}
	
	public BigDecimal getTotalCompra() {
		return totalCompra;
	}
	
	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}
	
	public BigDecimal getTotalPago() {
		return totalPago;
	}
	
	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
	}
}
