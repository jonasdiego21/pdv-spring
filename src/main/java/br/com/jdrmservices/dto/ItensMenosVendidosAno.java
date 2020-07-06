package br.com.jdrmservices.dto;

public class ItensMenosVendidosAno {
	
	private String nome;
	private Integer total;
	private String ano;
	
	public ItensMenosVendidosAno(String nome, Integer total, String ano) {
		this.nome = nome;
		this.total = total;
		this.ano = ano;
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

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}
