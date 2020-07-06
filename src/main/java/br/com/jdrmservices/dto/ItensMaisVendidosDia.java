package br.com.jdrmservices.dto;

public class ItensMaisVendidosDia {

	private String nome;
	private Integer total;
	private String dia;
	
	public ItensMaisVendidosDia(String nome, Integer total, String dia) {
		this.nome = nome;
		this.total = total;
		this.dia = dia;
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

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
}
