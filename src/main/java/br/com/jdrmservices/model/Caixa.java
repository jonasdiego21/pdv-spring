package br.com.jdrmservices.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "caixa")
public class Caixa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long codigo;
	
	private LocalDateTime dataHoraAbertura = LocalDateTime.now();
	private LocalDateTime dataHoraFechamento = LocalDateTime.now();
	
	private BigDecimal valorAbertura;
	private BigDecimal valorFechamento;
	
	private String observacao;
	
	public boolean isNovo() {
		return this.codigo == null;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataHoraAbertura() {
		return dataHoraAbertura;
	}

	public void setDataHoraAbertura(LocalDateTime dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}

	public LocalDateTime getDataHoraFechamento() {
		return dataHoraFechamento;
	}

	public void setDataHoraFechamento(LocalDateTime dataHoraFechamento) {
		this.dataHoraFechamento = dataHoraFechamento;
	}

	public BigDecimal getValorAbertura() {
		return valorAbertura;
	}

	public void setValorAbertura(BigDecimal valorAbertura) {
		this.valorAbertura = valorAbertura;
	}

	public BigDecimal getValorFechamento() {
		return valorFechamento;
	}

	public void setValorFechamento(BigDecimal valorFechamento) {
		this.valorFechamento = valorFechamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		Caixa other = (Caixa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
