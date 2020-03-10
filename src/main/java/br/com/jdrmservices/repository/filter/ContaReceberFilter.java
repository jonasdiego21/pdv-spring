package br.com.jdrmservices.repository.filter;

import br.com.jdrmservices.model.Cliente;
import br.com.jdrmservices.model.enumeration.Status;

public class ContaReceberFilter {

	private Cliente cliente;
	private Status status;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
