var Pdv = Pdv || {};

Pdv.Relatorios = (function() {
	
	Relatorios = function() {
		this.btnGerar = $('#btnGerar');
		this.btnGerarComissaoFuncionario = $('#btnGerarComissaoFuncionario');
	}
	
	Relatorios.prototype.start = function() {
		this.btnGerar.on('click', gerarRelatorio.bind(this));
		this.btnGerarComissaoFuncionario.on('click', gerarRelatorioComissaoFuncionario.bind(this));
	}

	gerarRelatorio = function(response) {
		var dataInicio = $('#dataInicio').val();
		var dataFim = $('#dataFim').val();
		
		var url = $('#div-form').data('url');	
		
		var resposta = $.ajax({
			url: url,
			method: 'GET',
			data: {
				dataInicio: dataInicio,
				dataFim: dataFim
			}
		});
		
		resposta.done(function(resposta){
			location.href = '/relatorios';
			window.open(this.url, '_blank', null, null);
		});
	}
	
	function gerarRelatorioComissaoFuncionario(response) {
		var nomeFuncionario = $('#nomeFuncionario').val();
		var dataInicioFuncionario = $('#dataInicioFuncionario').val();
		var dataFimFuncionario = $('#dataFimFuncionario').val();
		
		var url = $('#btnGerarComissaoFuncionario').data('url');	
		
		var resposta = $.ajax({
			url: url,
			method: 'GET',
			data: {
				nomeFuncionario: nomeFuncionario,
				dataInicioFuncionario: dataInicioFuncionario,
				dataFimFuncionario: dataFimFuncionario
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