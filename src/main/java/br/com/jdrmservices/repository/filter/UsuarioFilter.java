package br.com.jdrmservices.repository.filter;

public class UsuarioFilter {

	private String nome;

	public UsuarioFilter(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}