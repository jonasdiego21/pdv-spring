Pdv.TabelaItens = (function() {
	
	function TabelaItens(buscarProduto) {
		this.buscarProduto = buscarProduto;
		this.uuid = $('#uuid');
		this.btnExcluir = $('#btn-excluir');
		this.btnCancelar = $('#btn-cancelar');
		this.campoQuantidadeOculta = $('#inputQuantidade');
		this.tabelaItens = $('#tabela-itens');
		this.itemSelecionadoTabelaItens = null;
		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter); 
	}
	
	TabelaItens.prototype.start = function() {
		this.buscarProduto.on('item-selecionado', itemSelecionado.bind(this));
	}

	function itemSelecionado(evento, item) {				
		var retorno = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				'codigoOuCodigoBarras': item.codigoBarras,
				'quantidade': this.campoQuantidadeOculta.val(),
				'uuid': this.uuid.val()
			},
			success: tabelaItensRederizadaSuccess.bind(this)
		});	
		
		
		retorno.done(irParaUltimoItemAdicionadoCupom.bind(this));
	}	
	
	function tabelaItensRederizadaSuccess(html) {
		this.tabelaItens.html(html);
		
		this.itemSelecionadoTabelaItens = $('input[type=radio]');
		
		this.itemSelecionadoTabelaItens.on('click', mostrarButtonExcluirItem.bind(this));
		this.itemSelecionadoTabelaItens.on('click', mostrarButtonCancelar.bind(this));
		
		var valorTotal = $('#item-tabela').data('valor-total');
		
		this.emitter.trigger('tabela-itens-atualizada', valorTotal);
	}

	// refatorar
	function mostrarButtonExcluirItem(evento) {			
		this.btnCancelar.attr('class', 'text-right d-none');
		this.btnExcluir.attr('class', 'text-right d-block');
		
		this.btnExcluir.on('click', btnExcluirItemClicado.bind(this));
	}
	
	function btnExcluirItemClicado(evento) {
		this.btnExcluir.attr('class', 'text-right d-none');
		this.btnCancelar.attr('class', 'text-right d-none');
		
		var retorno = $.ajax({
			url: 'item/' + this.uuid.val() + '/' + this.itemSelecionadoTabelaItens.val(),
			method: 'DELETE',
			success: itemExcluidoSuccess.bind(this),
			error: itemExcluidoError.bind(this)
		});
		
		retorno.done(irParaUltimoItemAdicionadoCupom.bind(this));
	}
	
	function itemExcluidoSuccess(html) {
		this.tabelaItens.html(html);
		
		this.itemSelecionadoTabelaItens = $('input[type=radio]');
		
		this.itemSelecionadoTabelaItens.on('click', mostrarButtonExcluirItem.bind(this));
		this.itemSelecionadoTabelaItens.on('click', mostrarButtonCancelar.bind(this));
		
		var valorTotal = $('#item-tabela').data('valor-total');
		
		this.emitter.trigger('tabela-itens-atualizada', valorTotal);
	}
	
	function itemExcluidoError(error) {
		console.log(error);
	}
	
	// refatorar
	function mostrarButtonCancelar(evento) {			
		this.btnCancelar.attr('class', 'text-right d-block');
		
		this.btnCancelar.on('click', btnCancelarClicado.bind(this));
	}
	
	function btnCancelarClicado(evento) {
		this.itemSelecionadoTabelaItens = $('input[type=radio]');
		this.itemSelecionadoTabelaItens.attr("checked", false);
		this.btnCancelar.attr('class', 'text-right d-none');
		this.btnExcluir.attr('class', 'text-right d-none');
	}
	
	function irParaUltimoItemAdicionadoCupom() {
		this.tabelaItens.animate({
			scrollTop: this.tabelaItens.height()
		}, 100);
	}
	
	return TabelaItens;
	
}());