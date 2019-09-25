var Pdv = Pdv || {};

Pdv.Relatorios = (function() {
	
	Relatorios = function() {
		this.btnGerar = $('#btnGerar');
	}
	
	Relatorios.prototype.start = function() {
		this.btnGerar.on('click', gerarRelatorio.bind(this));
	}

	gerarRelatorio = function(response) {
		var dataInicio = $('#dataInicio').val();
		var dataFim = $('#dataFim').val();
		
		var url = $('#div-form').data('url');	
		
		var resposta = $.ajax({
			url: url,
			method: 'GET',
			data: {
				dataInicio: dataInicio + ' 00:00:00',
				dataFim: dataFim + ' 23:59:59'
			}
		});
		
		resposta.done(function(resposta){
			location.href = '/relatorios';
			window.open(this.url, '_blank', null, null);
		});
	}
	
	return Relatorios;
	
}());

$(function() {
	var relatorios = new Pdv.Relatorios();
	relatorios.start();
});