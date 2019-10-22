Pdv.Venda = (function() {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.totalVenda = $('#totalVenda');
	}
	
	Venda.prototype.start = function() {
		this.tabelaItens.on('tabela-itens-atualizada', tabelaItensAtualizada.bind(this));
	}
	
	function tabelaItensAtualizada(evento, valorTotalVenda, valorTotalItem) {
		var valor = valorTotalVenda == null ? '0,00' : valorTotalVenda;
		
		this.totalVenda.text('R$ ' + valor);
	}
	
	return Venda;
	
}());
/*
$(function() {
	var buscarProduto = new Pdv.BuscarProduto();
	buscarProduto.start();
	
	var tabelaItens = new Pdv.TabelaItens(buscarProduto);
	tabelaItens.start();
	
	var venda = new Pdv.Venda(tabelaItens);
	venda.start();
});
*/