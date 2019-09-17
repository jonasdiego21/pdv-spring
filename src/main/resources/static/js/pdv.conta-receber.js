var Pdv = Pdv || {};

Pdv.ContaReceber = (function() {
	
	function ContaReceber() {
		this.contaReceberBtn = $('#contaReceberBtn');
		this.codigoContaReceber = $('#contaReceberBtn').data('codigo-receber');
	}
	
	ContaReceber.prototype.start = function() {
		this.contaReceberBtn.on('click', contaReceberLancamento.bind(this));
	}
	
	function contaReceberLancamento(data) {
		$.ajax({
			url: '/contasreceber/lancamentos/' + this.codigoContaReceber,
			method: 'GET',
			success: requisicaoSuccess.bind(this),
			error: requisicaoError.bind(this)
		});
	}
	
	function requisicaoSuccess(response) {
		console.log('Ok, realizado com sucesso!', response);
		window.location.href = '/contasreceberlancamento/novo';
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