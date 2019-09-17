package br.com.jdrmservices.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.model.ItemVenda;
import br.com.jdrmservices.model.Usuario;
import br.com.jdrmservices.model.enumeration.FormaPagamento;
import br.com.jdrmservices.model.enumeration.StatusVenda;

public class PdvDTO {

	private String uuid;
	private Usuario usuario;
	private Cliente cliente;
	private LocalDate dataCriacao;
	private BigDecimal valorVenda;
	private BigDecimal valorDesconto;
	private FormaPagamento formaPagamento;
	private StatusVenda status;
	private List<ItemVenda> itens;
	private BigDecimal valorPago;
	private BigDecimal troco;
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public BigDecimal getValorVenda() {
		return valorVenda;
	}
	
	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public StatusVenda getStatus() {
		return status;
	}
	
	public void setStatus(StatusVenda status) {
		this.status = status;
	}
	
	public List<ItemVenda> getItens() {
		return itens;
	}
	
	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}
	
	public BigDecimal getValorPago() {
		return valorPago;
	}
	
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}
	
	public BigDecimal getTroco() {
		return troco;
	}
	
	public void setTroco(BigDecimal troco) {
		this.troco = troco;
	}
}
