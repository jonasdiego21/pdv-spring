package br.com.jdrmservices.session;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.jdrmservices.model.ItemVenda;
import br.com.jdrmservices.model.Produto;

@SessionScope
@Component
public class TabelasItensSession {

	private Set<TabelaItensVenda> tabelas = new HashSet<>();
	
	public BigDecimal getValorTotal(String uuid) {
		return buscarTabelaPorUuid(uuid).getValorTotal();
	}
	
	public void adicionarItem(String uuid, Produto produto, BigDecimal quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		
		tabela.adicionarItem(produto, quantidade);
		
		tabelas.add(tabela);
	}
	
	public void alterarQuantidadeItens(String uuid, Produto produto, BigDecimal quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.alterarQuantidadeItens(produto, quantidade);
	}
	
	public void excluirItem(String uuid, Produto produto) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		
		tabela.excluirItem(produto);
		
		tabelas.add(tabela);
	}
	
	public List<ItemVenda> getItens(String uuid) {
		return buscarTabelaPorUuid(uuid).getItens();		
	}
	
	private TabelaItensVenda buscarTabelaPorUuid(String uuid) {
		TabelaItensVenda tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny().orElse(new TabelaItensVenda(uuid));
		
		return tabela;
	}

	public BigDecimal getTotalVenda(String uuid, Produto produto, BigDecimal quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		
		BigDecimal total = tabela.getTotalVenda(produto, quantidade);
		
		return total;
	}
}
