package br.com.jdrmservices.dto;

public class TotalVendasDiaGeral {

	private String dia;
	private Integer total;
	
	public TotalVendasDiaGeral(String dia, Integer total) {
		this.dia = dia;
		this.total = total;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
