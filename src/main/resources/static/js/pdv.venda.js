Pdv.Venda = (function() {
	
	function Venda(tabelaItens, buscarProduto) {
		this.tabelaItens = tabelaItens;
		this.totalVenda = $('#totalVenda');
	}
	
	Venda.prototype.start = function() {
		this.tabelaItens.on('tabela-itens-atualizada', tabelaItensAtualizada.bind(this));
	}
	
	function tabelaItensAtualizada(evento, data) {	
		var valor = data.valorTotal == null ? 'R$ 0,00' : data.valorTotal;
		
		this.totalVenda.text(valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }).replace('R$', 'R$ '));
	}
	
	return Venda;
}());