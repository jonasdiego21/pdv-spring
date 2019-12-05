var Pdv = Pdv || {};

Pdv.ContaPagar = (function() {
	
	function ContaPagar() {
		this.contaPagarBtn = $('a.lancamento-btn');
		//this.codigoContaPagar = $('#contaPagarBtn').data('codigo-pagar');
	}
	
	ContaPagar.prototype.start = function() {
		this.contaPagarBtn.on('click', contaPagarLancamento.bind(this));
	}
	
	function contaPagarLancamento(e) {
		e.preventDefault();

		$.ajax({
			url: '/contaspagar/lancamentos/' + $(e.currentTarget).data("codigo-pagar"),
			method: 'GET',
			success: requisicaoSuccess.bind(this),
			error: requisicaoError.bind(this)
		});
	}
	
	function requisicaoSuccess(response) {
		$('body').html(response);
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