var Pdv = Pdv || {};

Pdv.FinalizarVenda = (function() {
	
	function FinalizarVenda(venda) {
		this.venda = venda;
		this.formPdv = $('#formFinalizarPdv');
		this.cliente = $('#clienteNome');
		this.status = $('#status');
		this.aviso = $('#aviso');
		
		this.formaPagamento = $('#formaPagamento');
		
		this.inputTotalVenda = $('#totalVenda');
		this.inputDesconto = $('#desconto');
		this.inputTotalPago = $('#totalPago');
		this.inputTroco = $('#troco');
		
		this.valorTotalVenda = 0.00;
		this.valorTotalDesconto = 0.00;
		this.valorTotalPago = 0.00;
		
		this.descontoAplicado = false;
		
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
				
				this.inputDesconto.focus();
				this.inputDesconto.select();
			}
		}.bind(this));
		
		this.inputDesconto.on('keypress', function(e) {			
			if(e.which == 13) {
				e.preventDefault();
							
				this.valorTotalVenda = this.inputTotalVenda.text().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();
				this.valorTotalDesconto = this.inputDesconto.val().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();

				$('#valorTotal').val(this.inputTotalVenda.text().replace('R$ ', ''));
				
				let total = 0.00;
				
				if(this.descontoAplicado === false) {
					total = (this.valorTotalVenda - this.valorTotalDesconto);
					this.descontoAplicado = true;	
				}	
				
				this.inputTotalVenda.text(converterValueStringToDouble(total));

				this.inputDesconto.val(this.inputDesconto.val() != '' ? 'R$ ' + this.inputDesconto.val() : 'R$ 0,00');

				this.inputTotalPago.focus();
				this.inputTotalPago.select();
			}
		}.bind(this));
		
		this.inputTotalPago.on('keypress', function(e) {
			if(e.which == 13) {
				e.preventDefault();
				
				this.valorTotalVenda = this.inputTotalVenda.text().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();
				this.valorTotalPago = this.inputTotalPago.val().replace('R$ ', '').replace('.', '').replace('.', '').replace(',', '.').trim();

				if(this.formaPagamento.val() == 'DINHEIRO' || 
						this.formaPagamento.val() == 'CREDITO' || this.formaPagamento.val() == 'DEBITO') {
					
					if(parseFloat(this.valorTotalPago) < parseFloat(this.valorTotalVenda)) {
						swal({
							  title: "Valor Insuficiente!",
							  text: "O VALOR PAGO NÃO PODE SER MENOR QUE O TOTAL A PAGAR!",
							  icon: "error",
							  button: "Fechar",
						}).then((value) => {			
							this.inputTotalPago.focus();
							this.inputTotalPago.select();
							this.inputTotalPago.val('R$ 0,00');
						});
												
						this.inputTotalPago.val(this.inputTotalPago.val() != '' ? 'R$ ' + this.inputTotalPago.val() : 'R$ 0,00');
					} else {
						var troco = (this.valorTotalPago - this.valorTotalVenda);
						var total = this.valorTotalVenda;
						
						this.inputTroco.val(converterValueStringToDouble(troco));					
						
						this.inputTotalPago.val(this.inputTotalPago.val() != '' ? 'R$ ' + this.inputTotalPago.val() : 'R$ 0,00');
						
						this.inputTotalVenda.text(this.inputTotalVenda.text());
						
						this.inputTroco.focus();
						this.inputTroco.select();
					}				
				} else {
					this.inputTotalPago.val(this.inputTotalPago.val() != '' ? 'R$ ' + this.inputTotalPago.val() : 'R$ 0,00');
					
					this.inputTroco.focus();
					this.inputTroco.select();
				}
			}	
		}.bind(this));
		
		this.inputTroco.on('keypress', function(e) {
			if(e.which == 13) {
				this.formaPagamento.focus();
				this.formaPagamento.select();
				
				$('#valorDesconto').val(this.inputDesconto.val().replace('R$ ', ''));
				$('#valorPago').val(this.inputTotalPago.val().replace('R$ ', ''));
				
				this.descontoAplicado = false;
				
				this.formPdv.submit();
			}
		}.bind(this));
		
		this.inputDesconto.on('focusout', function() {
			if(this.inputDesconto.val() <= 0) {
				$('#valorDesconto').val('R$ 0,00');
			}
		}.bind(this));
		
		this.inputTotalPago.on('focusout', function() {
			if(this.inputTotalPago.val() <= 0) {
				$('#valorPago').val('R$ 0,00');
			}
		}.bind(this));
	}
	
	function converterValueStringToDouble(stringValue) {
		return stringValue.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }).replace('R$', 'R$ ');
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