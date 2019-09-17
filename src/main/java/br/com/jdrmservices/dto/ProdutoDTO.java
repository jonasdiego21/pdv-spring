package br.com.jdrmservices.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;

import br.com.jdrmservices.model.enumeration.UnidadeMedida;

public class ProdutoDTO {
	
	private Long codigo;
	private String codigoBarras;
	private String nome;
	
	@Digits(integer = 10, fraction = 2)
	private BigDecimal precoVenda;
	
	@Digits(integer = 10, fraction = 3)
	private BigDecimal quantidade;
	
	private UnidadeMedida unidade;
	//private String foto
	
	public ProdutoDTO(Long codigo, String codigoBarras, String nome, BigDecimal precoVenda, BigDecimal quantidade, UnidadeMedida unidade) {
		this.codigo = codigo;
		this.codigoBarras = codigoBarras;
		this.nome = nome;
		this.precoVenda = precoVenda;
		this.quantidade = quantidade;
		this.unidade = unidade;
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
	
	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}
	
	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public UnidadeMedida getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeMedida unidade) {
		this.unidade = unidade;
	}
}
