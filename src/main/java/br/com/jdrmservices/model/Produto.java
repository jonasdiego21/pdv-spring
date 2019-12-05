package br.com.jdrmservices.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import br.com.jdrmservices.model.enumeration.UnidadeMedida;

@Entity
@Table(name = "produto")
@DynamicUpdate
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Nome é obrigatório")
	@Size(max = 24, message = "O nome deve conter no máximo 24 caracteres e no mínimo 5")
	private String nome;

	//@NotBlank(message = "Descricao é obrigatória")
	private String descricao;

	@Column(name = "preco_compra")
	@Digits(integer = 10, fraction = 2)
	@NotNull(message = "O preço de compra é obrigatorio")
	private BigDecimal precoCompra = BigDecimal.ZERO;

	@Column(name = "preco_venda")
	@Digits(integer = 10, fraction = 2)
	@NotNull(message = "O preço de venda é obrigatorio")
	private BigDecimal precoVenda = BigDecimal.ZERO;

	@Digits(integer = 10, fraction = 3)
	@NotNull(message = "A quantidade é obrigatoria")
	private BigDecimal quantidade = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	//@NotNull(message = "A unidade de medida é obrigatoria")
	private UnidadeMedida unidade = UnidadeMedida.UN;

	@NotBlank(message = "CodigoBarras é obrigatório")
	private String codigoBarras;
	
	//private String foto;
	//private String contentType;
	
	@ManyToOne
	//@NotNull(message = "A categoria é obrigatoria")
	private Categoria categoria;
	
	@ManyToOne
	//@NotNull(message = "O fornecedor é obrigatório")
	private Fornecedor fornecedor;

	public boolean isNovo() {
		return codigo == null;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
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

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (codigo == null) {
		if (other.codigo != null)
			return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}