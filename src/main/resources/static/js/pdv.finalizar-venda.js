var Pdv = Pdv || {};

Pdv.FinalizarVenda = (function() {
	
	function FinalizarVenda() {
		this.formPdv = $('#formFinalizarPdv');
		this.cliente = $('#clienteNome');
		this.status = $('#status');
		this.formaPagamento = $('#formaPagamento');
		this.totalVenda = $('#totalVenda');
		this.desconto = $('#desconto');
		this.totalPago = $('#totalPago');
		this.troco = $('#troco');
		this.aviso = $('#aviso');
		
		this.formaPagamento.focus();
		this.formaPagamento.select();
	}
	
	FinalizarVenda.prototype.start = function() {
		this.formaPagamento.on('keypress', function(e) {
			if(e.which == 13) {
				this.totalPago.focus();
				this.totalPago.select();
				
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
					default:
						this.formaPagamento.val('DINHEIRO');
						break;
				}
			}
		}.bind(this));
		
		this.totalPago.on('keypress', function(e) {
			var totalVenda = this.totalVenda.text();
			var totalPago = this.totalPago.val();
			
			var venda = parseFloat(totalVenda.replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.'));
			var pago = parseFloat(totalPago.replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.'));
			
			if(e.which == 13) {
				if(this.formaPagamento.val() == 'DINHEIRO') {
					if(pago < venda) {
						alert('O VALOR PAGO NÃO PODE SER MENOR QUE O TOTAL A PAGAR');
						this.totalPago.focus();
						this.totalPago.select();
					}
				}

				this.totalPago.val('R$ ' + this.totalPago.val());
				
				this.desconto.focus();
				this.desconto.select();
			}		
		}.bind(this));
		
		this.desconto.on('keypress', function(e) {
			if(e.which == 13) {
				var totalVenda = this.totalVenda.text();
				var desconto = this.desconto.val();
				var totalPago = this.totalPago.val();
				
				var troco = totalPago.replace('R$ ', '').replace('.', '').replace(',', '.') - (totalVenda.replace('R$ ', '').replace('.', '').replace(',', '.') - desconto.replace('R$ ', '').replace('.', '').replace(',', '.'));
				
				$('#valorPago').val(totalPago.replace('R$ ', ''));
				$('#valorTotal').val(totalVenda.replace('R$ ', ''));
				$('#valorDesconto').val(desconto.replace('R$ ', ''));
				
				this.troco.val(troco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }));
				
				this.desconto.val('R$ ' + this.desconto.val());
				
				this.troco.focus();
				this.troco.select();
			}
		}.bind(this));
		
		this.troco.on('keypress', function(e) {
			if(e.which == 13) {
				this.formaPagamento.focus();
				this.formaPagamento.select();
				
				this.formPdv.submit();
			}	
		}.bind(this));
	}
	
	return FinalizarVenda;
	
}());

$(function() {
	let finalizarVenda = new Pdv.FinalizarVenda();
	finalizarVenda.start();
});