package br.com.jdrmservices.dto;

public class TotalVendasDia {

	private String dia;
	private Integer total;
	
	public TotalVendasDia(String dia, Integer total) {
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
