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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;

import br.com.jdrmservices.model.enumeration.Status;
@Entity
@Table(name = "conta_pagar")
public class ContaPagar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@NotNull(message = "O fornecedor é obrigatório")
	private Fornecedor fornecedor;
	
	//@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Column(name = "data_compra")//columnDefinition = "DATE"
	private LocalDate dataCompra = LocalDate.now();
	
	//@DateTimeFormat(pattern = Constants.FORMAT_DATE)
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento = LocalDate.now().plusDays(30);
	
	@Column(name = "total_compra")
	private BigDecimal totalCompra = BigDecimal.ZERO;
	
	@Column(name = "total_pago")
	private BigDecimal totalPago = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.DEVENDO;
	
	@Transient
	private BigDecimal restante = BigDecimal.ZERO;
	
	public boolean isNovo() {
		return this.codigo == null;
	}
	
	public BigDecimal getSaldoDevedor() {
		return Optional.ofNullable(this.totalCompra.subtract(totalPago))
				.orElse(BigDecimal.ZERO);
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public LocalDate getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(LocalDate dataCompra) {
		this.dataCompra = dataCompra;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(BigDecimal totalCompra) {
		this.totalCompra = totalCompra;
	}
	
	public BigDecimal getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(BigDecimal totalPago) {
		this.totalPago = totalPago;
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
		ContaPagar other = (ContaPagar) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
