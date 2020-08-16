package br.com.jdrmservices.dto;

import java.math.BigDecimal;

public class ProdutosBaixoEstoque {

	private Long codigo;
	private String codigoBarras;	
	private String nome;
	private BigDecimal quantidade;

	public ProdutosBaixoEstoque(Long codigo, String codigoBarras, String nome, BigDecimal quantidade) {
		this.codigo = codigo;
		this.codigoBarras = codigoBarras;
		this.nome = nome;
		this.quantidade = quantidade;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
}
