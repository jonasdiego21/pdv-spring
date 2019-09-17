var Pdv = Pdv || {};

Pdv.ContaPagar = (function() {
	
	function ContaPagar() {
		this.contaPagarBtn = $('#contaPagarBtn');
		this.codigoContaPagar = $('#contaPagarBtn').data('codigo-pagar');
	}
	
	ContaPagar.prototype.start = function() {
		this.contaPagarBtn.on('click', contaPagarLancamento.bind(this));
	}
	
	function contaPagarLancamento(data) {
		$.ajax({
			url: '/contaspagar/lancamentos/' + this.codigoContaPagar,
			method: 'GET',
			success: requisicaoSuccess.bind(this),
			error: requisicaoError.bind(this)
		});
	}
	
	function requisicaoSuccess(response) {
		console.log('Ok, realizado com sucesso!', response);
		window.location.href = '/contaspagarlancamento/novo';
	}
	
	function requisicaoError(error) {
		console.log(error);
	}
	
	return ContaPagar;
	
}());

$(function() {
	var contaPagar = new Pdv.ContaPagar();
	contaPagar.start();
});