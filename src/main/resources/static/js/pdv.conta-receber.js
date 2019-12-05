var Pdv = Pdv || {};

Pdv.ContaReceber = (function() {
	
	function ContaReceber() {
		this.contaReceberBtn = $('a.contaReceberBtn');
		//this.codigoContaReceber = $('#contaReceberBtn').data('codigo-receber');
	}
	
	ContaReceber.prototype.start = function() {
		this.contaReceberBtn.on('click', contaReceberLancamento.bind(this));
	}
	
	function contaReceberLancamento(e) {
		e.preventDefault();
		
		$.ajax({
			url: '/contasreceber/lancamentos/' + $(e.currentTarget).data("codigo-receber"),
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
	
	return ContaReceber;
	
}());

$(function() {
	var contaReceber = new Pdv.ContaReceber();
	contaReceber.start();
});