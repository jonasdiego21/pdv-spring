package br.com.jdrmservices.dto;

public class TotalVendasAnoGeral {

	private String ano;
	private Integer total;
	
	public TotalVendasAnoGeral(String ano, Integer total) {
		this.ano = ano;
		this.total = total;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
