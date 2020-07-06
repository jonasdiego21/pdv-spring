var Pdv = Pdv || {};

Pdv.Graficos = (function() {
	
	function Graficos() {
		//this.line = $('#line');
		//this.bar = $('#bar');
		//this.pizza = $('#pie');
		
		//this.ctxItensMaisVendidosDia = $('#itensMaisVendidosDia')[0].getContext('2d');
		this.ctxItensMaisVendidosMes = $('#itensMaisVendidosMes')[0].getContext('2d');
		this.ctxItensMaisVendidosAno = $('#itensMaisVendidosAno')[0].getContext('2d');
		
		//this.ctxItensMenosVendidosDia = $('#itensMenosVendidosDia')[0].getContext('2d');
		this.ctxItensMenosVendidosMes = $('#itensMenosVendidosMes')[0].getContext('2d');
		this.ctxItensMenosVendidosAno = $('#itensMenosVendidosAno')[0].getContext('2d');
		
		this.ctxTotalVendasDia = $('#totalVendasDia')[0].getContext('2d');
		this.ctxTotalVendasMes = $('#totalVendasMes')[0].getContext('2d');
		this.ctxTotalVendasAno = $('#totalVendasAno')[0].getContext('2d');
		
		this.ctxTotalVendasDiaCrediario = $('#totalVendasDiaCrediario')[0].getContext('2d');
		this.ctxTotalVendasMesCrediario = $('#totalVendasMesCrediario')[0].getContext('2d');
		this.ctxTotalVendasAnoCrediario = $('#totalVendasAnoCrediario')[0].getContext('2d');
		
		this.ctxTotalVendasDiaGeral = $('#totalVendasDiaGeral')[0].getContext('2d');
		this.ctxTotalVendasMesGeral = $('#totalVendasMesGeral')[0].getContext('2d');
		this.ctxTotalVendasAnoGeral = $('#totalVendasAnoGeral')[0].getContext('2d');
		
		//this.tipoGrafico = 'line';
	}
	
	Graficos.prototype.start = function() {
		//this.line.on('click', alterarTipoGrafico.bind(this));
		//this.bar.on('click', alterarTipoGrafico.bind(this));
		//this.pizza.on('click', alterarTipoGrafico.bind(this));
		
		buscarDadosDosGraficos.call(this);
	}
	
	function gerarCores(bar) {
		var cor = [];
		
		for(i = 0; i < bar.length; i++) {
			cor.unshift('rgba(' + Math.floor(Math.random() * 255) + ',' 
					+ Math.floor(Math.random() * 255) + ',' + Math.floor(Math.random() * 255) + ', 0.5)');
		}
		
		return cor;
	}
	
	function gerarCoresText(bar) {
		var cor = [];
		
		for(i = 0; i < bar.length; i++) {
			cor.unshift('rgba(' + Math.floor(Math.random() * 255) + ',' 
					+ Math.floor(Math.random() * 255) + ',' + Math.floor(Math.random() * 255) + ', 1.0)');
		}
		
		return cor;
	}
	
	function alterarTipoGrafico(e) {
		this.tipoGrafico = e.currentTarget.id;
		buscarDadosDosGraficos.call(this);
	}
	
	function buscarDadosDosGraficos() {
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
		
		$.ajax({
			url: 'vendas/totalVendasDiaCrediario',
			method: 'GET',
			success: rederizaGraficoTotalVendasDiaCrediario.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasMesCrediario',
			method: 'GET',
			success: rederizaGraficoTotalVendasMesCrediario.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasAnoCrediario',
			method: 'GET',
			success: rederizaGraficoTotalVendasAnoCrediario.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasDiaGeral',
			method: 'GET',
			success: rederizaGraficoTotalVendasDiaGeral.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasMesGeral',
			method: 'GET',
			success: rederizaGraficoTotalVendasMesGeral.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/totalVendasAnoGeral',
			method: 'GET',
			success: rederizaGraficoTotalVendasAnoGeral.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		// mais vendidos
		$.ajax({
			url: 'vendas/itensMaisVendidosAno',
			method: 'GET',
			success: rederizaGraficoItensMaisVendidosAno.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/itensMaisVendidosMes',
			method: 'GET',
			success: rederizaGraficoItensMaisVendidosMes.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		// menos vendidos
		$.ajax({
			url: 'vendas/itensMenosVendidosAno',
			method: 'GET',
			success: rederizaGraficoItensMenosVendidosAno.bind(this),
			error: rederizarGraficoError.bind(this)
		});
		
		$.ajax({
			url: 'vendas/itensMenosVendidosMes',
			method: 'GET',
			success: rederizaGraficoItensMenosVendidosMes.bind(this),
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
					backgroundColor: gerarCores(dia),
					pointBorderColor: gerarCoresText(dia),
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
			type: 'bar',
			data: {
				labels: mes,
				datasets: [{
					label: 'Total de vendas por mês',
					backgroundColor: gerarCores(mes),
					pointBorderColor: gerarCoresText(mes),
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
			type: 'pie',
			data: {
				labels: ano,
				datasets: [{
					label: 'Total de vendas por ano',
					backgroundColor: gerarCores(ano),
					pointBorderColor: gerarCoresText(ano),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}

	function rederizaGraficoTotalVendasDiaCrediario(totalVendasDiaCrediario) {
		var dia = [];
		var total = [];
		
		totalVendasDiaCrediario.forEach(function(data) {
			dia.unshift(data.dia);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasDiaCrediario = new Chart(this.ctxTotalVendasDiaCrediario, {
			type: 'line',
			data: {
				labels: dia,
				datasets: [{
					label: 'Total de vendas por dia',
					backgroundColor: gerarCores(dia),
					pointBorderColor: gerarCoresText(dia),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasMesCrediario(totalVendasMesCrediario) {
		var mes = [];
		var total = [];
		
		totalVendasMesCrediario.forEach(function(data) {
			mes.unshift(data.mes);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasMesCrediario = new Chart(this.ctxTotalVendasMesCrediario, {
			type: 'bar',
			data: {
				labels: mes,
				datasets: [{
					label: 'Total de vendas por mês',
					backgroundColor: gerarCores(mes),
					pointBorderColor: gerarCoresText(mes),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasAnoCrediario(totalVendasAnoCrediario) {
		var ano = [];
		var total = [];
		
		totalVendasAnoCrediario.forEach(function(data) {
			ano.unshift(data.ano);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasAnoCrediario = new Chart(this.ctxTotalVendasAnoCrediario, {
			type: 'pie',
			data: {
				labels: ano,
				datasets: [{
					label: 'Total de vendas por ano',
					backgroundColor: gerarCores(ano),
					pointBorderColor: gerarCoresText(ano),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasDiaGeral(totalVendasDiaGeral) {
		var dia = [];
		var total = [];
		
		totalVendasDiaGeral.forEach(function(data) {
			dia.unshift(data.dia);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasDiaGeral = new Chart(this.ctxTotalVendasDiaGeral, {
			type: 'line',
			data: {
				labels: dia,
				datasets: [{
					label: 'Total de vendas por dia',
					backgroundColor: gerarCores(dia),
					pointBorderColor: gerarCoresText(dia),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasMesGeral(totalVendasMesGeral) {
		var mes = [];
		var total = [];
		
		totalVendasMesGeral.forEach(function(data) {
			mes.unshift(data.mes);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasMesGeral = new Chart(this.ctxTotalVendasMesGeral, {
			type: 'bar',
			data: {
				labels: mes,
				datasets: [{
					label: 'Total de vendas por mês',
					backgroundColor: gerarCores(mes),
					pointBorderColor: gerarCoresText(mes),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoTotalVendasAnoGeral(totalVendasAnoGeral) {
		var ano = [];
		var total = [];
		
		totalVendasAnoGeral.forEach(function(data) {
			ano.unshift(data.ano);
			total.unshift(data.total);
		});
		
		var graficoTotalVendasAnoGeral = new Chart(this.ctxTotalVendasAnoGeral, {
			type: 'pie',
			data: {
				labels: ano,
				datasets: [{
					label: 'Total de vendas por ano',
					backgroundColor: gerarCores(ano),
					pointBorderColor: gerarCoresText(ano),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}

	// Mais vendidos
	function rederizaGraficoItensMaisVendidosAno(itensMaisVendidosAno) {
		var ano = [];
		var nome = [];
		var total = [];
		
		itensMaisVendidosAno.forEach(function(data) {
			ano.unshift(data.ano);
			nome.unshift(data.nome);
			total.unshift(data.total);
		});
		
		var graficoItensMaisVendidosAno = new Chart(this.ctxItensMaisVendidosAno, {
			type: 'pie',
			data: {
				labels: nome,
				datasets: [{
					label: 'Itens mais vendidos no ano',
					backgroundColor: gerarCores(nome),
					pointBorderColor: gerarCoresText(nome),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoItensMaisVendidosMes(itensMaisVendidosMes) {
		var mes = [];
		var nome = [];
		var total = [];
		
		itensMaisVendidosMes.forEach(function(data) {
			mes.unshift(data.mes);
			nome.unshift(data.nome);
			total.unshift(data.total);
		});
		
		var graficoItensMaisVendidosMes = new Chart(this.ctxItensMaisVendidosMes, {
			type: 'pie',
			data: {
				labels: nome,
				datasets: [{
					label: 'Itens mais vendidos no mês',
					backgroundColor: gerarCores(nome),
					pointBorderColor: gerarCoresText(nome),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	// Menos vendidos
	function rederizaGraficoItensMenosVendidosAno(itensMenosVendidosAno) {
		var ano = [];
		var nome = [];
		var total = [];
		
		itensMenosVendidosAno.forEach(function(data) {
			ano.unshift(data.ano);
			nome.unshift(data.nome);
			total.unshift(data.total);
		});
		
		var graficoItensMenosVendidosAno = new Chart(this.ctxItensMenosVendidosAno, {
			type: 'pie',
			data: {
				labels: nome,
				datasets: [{
					label: 'Itens menos vendidos no ano',
					backgroundColor: gerarCores(nome),
					pointBorderColor: gerarCoresText(nome),
					pointBackgroundColor: '#fff',
					data: total
				}]
			}
		});
	}
	
	function rederizaGraficoItensMenosVendidosMes(itensMenosVendidosMes) {
		var mes = [];
		var nome = [];
		var total = [];
		
		itensMenosVendidosMes.forEach(function(data) {
			mes.unshift(data.mes);
			nome.unshift(data.nome);
			total.unshift(data.total);
		});
		
		var graficoItensMenosVendidosMes = new Chart(this.ctxItensMenosVendidosMes, {
			type: 'pie',
			data: {
				labels: nome,
				datasets: [{
					label: 'Itens menos vendidos no mês',
					backgroundColor: gerarCores(nome),
					pointBorderColor: gerarCoresText(nome),
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