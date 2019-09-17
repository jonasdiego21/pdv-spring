package br.com.jdrmservices.service.event.venda;

import br.com.jdrmservices.model.Venda;

public class VendaEvent {

	private Venda venda;

	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}
}
