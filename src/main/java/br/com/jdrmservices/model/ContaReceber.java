package br.com.jdrmservices.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.jdrmservices.model.enumeration.Status;

@Entity
@Table(name = "conta_receber")
public class ContaReceber implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@NotNull(message = "O cliente é obrigatório")
	private Cliente cliente;
	
	//@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Column(name = "data_venda")
	private LocalDate dataVenda = LocalDate.now();
	
	//@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento = LocalDate.now().plusDays(30);
	
	@Column(name = "total_venda")
	private BigDecimal totalVenda = BigDecimal.ZERO;
	
	@Column(name = "total_recebido")
	private BigDecimal totalRecebido = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.DEVENDO;
	
	@Transient
	private BigDecimal restante = BigDecimal.ZERO;
	
	public boolean isNovo() {
		return this.codigo == null;
	}
	
	public BigDecimal getSaldoDevedor() {
		return Optional.ofNullable(this.totalVenda.subtract(totalRecebido))
				.orElse(BigDecimal.ZERO);
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(BigDecimal totalVenda) {
		this.totalVenda = totalVenda;
	}
	
	public BigDecimal getTotalRecebido() {
		return totalRecebido;
	}

	public void setTotalRecebido(BigDecimal totalRecebido) {
		this.totalRecebido = totalRecebido;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public BigDecimal getRestante() {
		return this.restante != BigDecimal.ZERO ? this.restante : this.getSaldoDevedor();
	}

	public void setRestante(BigDecimal restante) {
		this.restante = restante;
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
		ContaReceber other = (ContaReceber) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
