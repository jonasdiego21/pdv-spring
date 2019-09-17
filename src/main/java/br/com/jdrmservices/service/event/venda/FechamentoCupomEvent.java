package br.com.jdrmservices.service.event.venda;

import br.com.jdrmservices.model.Venda;

public class FechamentoCupomEvent {

	private Venda venda;

	public FechamentoCupomEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}
}
