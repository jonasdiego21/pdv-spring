package br.com.jdrmservices.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ContaReceberDTO {

	private Long codigoContaReceber;
	private BigDecimal totalVenda;
	private BigDecimal totalRecebido;
	
	public Long getCodigoContaReceber() {
		return codigoContaReceber;
	}
	
	public void setCodigoContaReceber(Long codigoContaReceber) {
		this.codigoContaReceber = codigoContaReceber;
	}
	
	public BigDecimal getTotalVenda() {
		return totalVenda;
	}
	
	public void setTotalVenda(BigDecimal totalVenda) {
		this.totalVenda = totalVenda;
	}
	
	public BigDecimal getTotalRecebido() {
		return totalRecebido;
	}
	
	public void setTotalRecebido(BigDecimal totalRecebido) {
		this.totalRecebido = totalRecebido;
	}
}
