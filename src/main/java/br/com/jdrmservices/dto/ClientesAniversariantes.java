package br.com.jdrmservices.dto;

public class ClientesAniversariantes {

	private Long codigo;
	private String nome;
	private String dataNascimento;

	public ClientesAniversariantes(Long codigo, String nome, String dataNascimento) {
		this.codigo = codigo;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
}
