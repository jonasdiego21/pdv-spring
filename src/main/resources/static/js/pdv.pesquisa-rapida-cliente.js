var Pdv = Pdv || {};

Pdv.PesquisaRapidaCliente = (function() {
	
	function PesquisaRapidaCliente() {
		this.modalPesquisaCliente = $('#modalPesquisaRapidaCliente');
		this.btnPesquisaCliente = $('.js-btn-pesquisa-cliente');
		this.nomeCliente = $('#nomeCliente');
		this.messageErro = $('#errorMessage');
		this.tabelaPesquisaClienteContainer = $('#containerTabelaClientes');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
	}
	
	PesquisaRapidaCliente.prototype.start = function() {
		this.btnPesquisaCliente.on('click', pesquisaRapidaClicada.bind(this));
	}
	
	function pesquisaRapidaClicada(event) {
		event.preventDefault();
		
		$.ajax({
			url: '/clientes',
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeCliente.val()
			},
			success: pesquisaClienteSuccess.bind(this),
			error: pesquisaClienteError.bind(this)
		});
	}
	
	function pesquisaClienteSuccess(resultado) {
		var html = this.template(resultado);
		
		this.tabelaPesquisaClienteContainer.html(html);
		this.messageErro.addClass('invisible');
		
		var tabelaClientePesquisa = new Pdv.TabelaClientePesquisa(this.modalPesquisaCliente);
		tabelaClientePesquisa.start();
	}
	
	function pesquisaClienteError(error) {
		this.messageErro.removeClass('invisible');
	}
	
	return PesquisaRapidaCliente;	
	
}());

Pdv.TabelaClientePesquisa = (function() {
	
	function TabelaClientePesquisa(modal) {
		this.modalCliente = modal;
		this.cliente = $('#clientePesquisaRapida');
		this.inputCodigoCliente = $('#cliente');
		this.inputNomeCliente = $('#aviso');
		this.campoCodigoProduto = $('#codigoProduto');
	}
	
	TabelaClientePesquisa.prototype.start = function() {
		this.cliente.on('click', clienteSelecionado.bind(this));
		this.cliente.focus();
	}
	
	function clienteSelecionado(evento) {
		var clienteSelecionado = $(evento.currentTarget);
		
		this.inputCodigoCliente.val(clienteSelecionado.data('codigo'));
		this.inputNomeCliente.text(clienteSelecionado.data('nome'));
		
		this.modalCliente.modal('hide');
		
		this.campoCodigoProduto.val('');
		this.campoCodigoProduto.focus();
	}
	
	return TabelaClientePesquisa;
	
}());

$(function() {
	let pesquisaCliente = new Pdv.PesquisaRapidaCliente();
	pesquisaCliente.start();
});