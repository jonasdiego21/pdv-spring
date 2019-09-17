package br.com.jdrmservices.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.jdrmservices.model.ItemVenda;
import br.com.jdrmservices.model.Produto;

class TabelaItensVenda {

	private String uuid;
	private List<ItemVenda> itens = new ArrayList<ItemVenda>();

	public TabelaItensVenda(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public BigDecimal getTotalVenda(Produto produto, BigDecimal quantidade) {
		return quantidade.multiply(produto.getPrecoVenda());
	}

	public void adicionarItem(Produto produto, BigDecimal quantidade) {
		Optional<ItemVenda> itemVendaOptional = BuscarItemPorProduto(produto);
		
		ItemVenda itemVenda = null;
		
		if(itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade().add(quantidade));
		} else {			
			itemVenda = new ItemVenda();
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(produto.getPrecoVenda());

			itens.add(itemVenda);
		}
	}

	private Optional<ItemVenda> BuscarItemPorProduto(Produto produto) {
		Optional<ItemVenda> itemVendaOptional = itens.stream()
			.filter(i -> i.getProduto().equals(produto))
			.findAny();
		return itemVendaOptional;
	}
	
	public void alterarQuantidadeItens(Produto produto, BigDecimal quantidade) {
		ItemVenda itemVenda = BuscarItemPorProduto(produto).get();
		itemVenda.setQuantidade(quantidade);
	}
	
	public void excluirItem(Produto produto) {
		int indice = IntStream.range(0, itens.size())
				.filter(i -> itens.get(i).getProduto().equals(produto))
				.findAny().getAsInt();
		
		itens.remove(indice);
	}
	
	public int getTotal() {
		return itens.size();
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		TabelaItensVenda other = (TabelaItensVenda) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
