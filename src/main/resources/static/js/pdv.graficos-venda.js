var Pdv = Pdv || {};

Pdv.Graficos = (function() {
	
	function Graficos() {
		this.ctxTotalVendasDia = $('#totalVendasDia')[0].getContext('2d');
		this.ctxTotalVendasMes = $('#totalVendasMes')[0].getContext('2d');
		this.ctxTotalVendasAno = $('#totalVendasAno')[0].getContext('2d');
	}
	
	Graficos.prototype.start = function() {
		$.ajax({
			url: 'vendas/totalVendasDia',
			method: 'GET',
			success: rederizaGraficoTotalVendasDia.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasMes',
			method: 'GET',
			success: rederizaGraficoTotalVendasMes.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasAno',
			method: 'GET',
			success: rederizaGraficoTotalVendasAno.bind(this),
			error: rederizarGraficoError.bind(this)
		});
	}
	
	function rederizaGraficoTotalVendasDia(totalVendasDia) {
		var dia = [];
		var total = [];
		
		totalVendasDia.forEach(function(data) {
			dia.unshift(data.dia);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasDia = new Chart(this.ctxTotalVendasDia, {
			type: 'line',
			data: {
				labels: dia,
				datasets: [{
					label: 'Total de vendas por dia',
					backgroundColor: 'rgba(26, 179, 148, 0.5)',
					pointBorderColor: 'rgba(26, 179, 148, 1)',
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasMes(totalVendasMes) {
		var mes = [];
		var total = [];
		
		totalVendasMes.forEach(function(data) {
			mes.unshift(data.mes);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasMes = new Chart(this.ctxTotalVendasMes, {
			type: 'line',
			data: {
				labels: mes,
				datasets: [{
					label: 'Total de vendas por mês',
					backgroundColor: 'rgba(26, 179, 148, 0.5)',
					pointBorderColor: 'rgba(26, 179, 148, 1)',
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasAno(totalVendasAno) {
		var ano = [];
		var total = [];
		
		totalVendasAno.forEach(function(data) {
			ano.unshift(data.ano);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasAno = new Chart(this.ctxTotalVendasAno, {
			type: 'line',
			data: {
				labels: ano,
				datasets: [{
					label: 'Total de vendas por ano',
					backgroundColor: 'rgba(26, 179, 148, 0.5)',
					pointBorderColor: 'rgba(26, 179, 148, 1)',
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}

	function rederizarGraficoError(error) {
		console.log(error);
	}
	
	return Graficos;
	
}());

$(function() {
	let graficos = new Pdv.Graficos();
	graficos.start();
});