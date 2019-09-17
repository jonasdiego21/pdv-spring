package br.com.jdrmservices.dto;

public class TotalVendasMes {

	private String mes;
	private Integer total;

	public TotalVendasMes(String mes, Integer total) {
		this.mes = mes;
		this.total = total;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}