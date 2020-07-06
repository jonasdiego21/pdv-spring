package br.com.jdrmservices.dto;

public class ItensMaisVendidosMes {

	private String nome;
	private Integer total;
	private String mes;
	
	public ItensMaisVendidosMes(String nome, Integer total, String mes) {
		this.nome = nome;
		this.total = total;
		this.mes = mes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}
}
