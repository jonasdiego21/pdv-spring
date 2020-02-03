var Imeph = Imeph || {};

Imeph.ItensSelecionados = (function() {
	
	function ItensSelecionados() {
		this.buttonGerarExcel = $('#buttonGerarExcel');
	}
	
	ItensSelecionados.prototype.start = function() {
		this.buttonGerarExcel.on('click', gerarExcel.bind(this));
	}
	
	async function gerarExcel() {		
		setTimeout(function() {				
			$('.table').table2excel({
				exclude: '.noExl',
				name: 'RELATÓRIO DE VENDA - PDV',
				filename: 'pdv-vendas'
			});
		}, 50);		
	}
	
	return ItensSelecionados;
	
}());

$(function() {
	let itensSelecionados = new Imeph.ItensSelecionados();
	itensSelecionados.start();
});