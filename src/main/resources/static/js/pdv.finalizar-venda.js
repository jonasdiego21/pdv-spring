var Pdv = Pdv || {};

Pdv.FinalizarVenda = (function() {
	
	function FinalizarVenda(venda) {
		this.venda = venda;
		this.formPdv = $('#formFinalizarPdv');
		this.cliente = $('#clienteNome');
		this.status = $('#status');
		this.aviso = $('#aviso');
		
		this.formaPagamento = $('#formaPagamento');
		
		this.totalVenda = $('#totalVenda');
		this.desconto = $('#desconto');
		this.totalPago = $('#totalPago');
		this.troco = $('#troco');
		
		this.valorTotalVenda = 0.00;
		this.valorTotalDesconto = 0.00;
		this.valorTotalPago = 0.00;
		
		this.formaPagamento.focus();
		this.formaPagamento.select();
	}
	
	FinalizarVenda.prototype.start = function() {
		this.formaPagamento.on('keypress', function(e) {
			if(e.which == 13) {
				switch(this.formaPagamento.val()) {
					case '1':
						this.formaPagamento.val('DINHEIRO');
						break;
					case '2':
						this.formaPagamento.val('CREDIARIO');
						break;
					case '3':
						this.formaPagamento.val('CREDITO');
						break;
					case '4':
						this.formaPagamento.val('DEBITO');
						break;
					case '5':
						this.formaPagamento.val('NENHUMA');
						break;
					case '6':
						this.formaPagamento.val('ORCAMENTO');
						break;
					default:
						this.formaPagamento.val('DINHEIRO');
						break;
				}
				
				this.desconto.focus();
				this.desconto.select();
			}
		}.bind(this));
		
		this.desconto.on('keypress', function(e) {			
			if(e.which == 13) {
				e.preventDefault();
				
				this.valorTotalVenda = this.totalVenda.text().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();
				this.valorTotalDesconto = this.desconto.val().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();

				$('#valorTotal').val(this.totalVenda.text().replace('R$ ', '').trim());

				var total = this.valorTotalVenda - this.valorTotalDesconto;

				this.totalVenda.text(total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }).replace('R$', 'R$ '));//1

				this.desconto.val(this.desconto.val() != '' ? 'R$ ' + this.desconto.val() : 'R$ 0,00');	

				this.totalPago.focus();
				this.totalPago.select();
			}
		}.bind(this));
		
		this.totalPago.on('keypress', function(e) {
			if(e.which == 13) {
				e.preventDefault();
				
				this.valorTotalVenda = this.totalVenda.text().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();
				this.valorTotalPago = this.totalPago.val().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();

				if(this.formaPagamento.val() == 'DINHEIRO' || 
						this.formaPagamento.val() == 'CREDITO' || this.formaPagamento.val() == 'DEBITO') {

					if(parseFloat(this.valorTotalPago) < parseFloat(this.valorTotalVenda)) {
						console.log('#ERRO', 'totalVenda', this.valorTotalVenda, 'totalPago', this.valorTotalPago, 'desconto', this.valorTotalDesconto);
						
						swal({
							  title: "Valor Insuficiente!",
							  text: "O VALOR PAGO NÃO PODE SER MENOR QUE O TOTAL A PAGAR!",
							  icon: "error",
							  button: "Fechar",
						}).then((value) => {			
							this.totalPago.focus();
							this.totalPago.select();
							this.totalPago.val('R$ 0,00');
						});
												
						this.totalPago.val(this.totalPago.val() != '' ? 'R$ ' + this.totalPago.val() : 'R$ 0,00');
					} else {
						var troco = this.valorTotalPago - (this.valorTotalVenda - this.valorTotalDesconto);
						var total = this.valorTotalVenda - this.valorTotalDesconto;

						this.troco.val(troco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }).replace('R$', 'R$ '));
						this.totalVenda.text(total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }).replace('R$', 'R$ '));//2	

						this.totalPago.val(this.totalPago.val() != '' ? 'R$ ' + this.totalPago.val() : 'R$ 0,00');
						
						this.troco.focus();
						this.troco.select();
					}
				} else {
					this.totalPago.val(this.totalPago.val() != '' ? 'R$ ' + this.totalPago.val() : 'R$ 0,00');
					
					this.troco.focus();
					this.troco.select();
				}
			}	
		}.bind(this));
		
		this.troco.on('keypress', function(e) {
			if(e.which == 13) {
				this.formaPagamento.focus();
				this.formaPagamento.select();
				
				$('#valorDesconto').val(this.desconto.val().replace('R$ ', '').trim());
				$('#valorPago').val(this.totalPago.val().replace('R$ ', '').trim());

				console.log('#3', 'totalVenda', this.valorTotalVenda, 'totalPago', this.valorTotalPago, 'desconto', this.valorTotalDesconto);
				
				console.log('#1', $('#valorTotal').val());
				console.log('#1', $('#valorPago').val());
				console.log('#1', $('#valorDesconto').val());

				this.formPdv.submit();
			}
		}.bind(this));
	}
	
	return FinalizarVenda;
	
}());

$(function() {
	var buscarProduto = new Pdv.BuscarProduto();
	buscarProduto.start();
	
	var tabelaItens = new Pdv.TabelaItens(buscarProduto);
	tabelaItens.start();
	
	var venda = new Pdv.Venda(tabelaItens);
	venda.start();

	var finalizarVenda = new Pdv.FinalizarVenda(venda);
	finalizarVenda.start();
});