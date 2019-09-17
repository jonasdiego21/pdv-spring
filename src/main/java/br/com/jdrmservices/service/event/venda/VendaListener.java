package br.com.jdrmservices.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.jdrmservices.model.ItemVenda;
import br.com.jdrmservices.model.Produto;
import br.com.jdrmservices.repository.Produtos;

@Component
public class VendaListener {
	
	@Autowired
	private Produtos produtos;

	@EventListener
	public void vendaEmitida(VendaEvent vendaEvent) {
		for(ItemVenda item : vendaEvent.getVenda().getItens()) {
			Produto produto = produtos.findByCodigo(item.getProduto().getCodigo());
			produto.setQuantidade(produto.getQuantidade().subtract(item.getQuantidade()));
			produtos.save(produto);
		}
	}
}
