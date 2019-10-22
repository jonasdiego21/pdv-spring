var Pdv = Pdv || {};

Pdv.BuscarProduto = (function() {
	
	function BuscarProduto() {
		this.uuid = $('#uuid').val();
		this.campoCodigoProduto = $('#codigoProduto');
		this.campoNomeProduto = $('#nomeProduto');
		this.campoQuantidade = $('#quantidade');
		this.campoQuantidadeOculta = $('#inputQuantidade');
		this.campoValorUnitario = $('#valorUnitario');
		this.campoValorTotal = $('#valorTotal');
		this.aviso = $('#aviso');
		this.vendaStatus = false;
		this.itemDados = null;
		this.formPdv = $('#formPdv');
		this.campoCodigoCliente = $('#cliente');
		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter); 	

		this.campoCodigoProduto.focus();
	}
	
	BuscarProduto.prototype.start = function() {
		this.campoCodigoProduto.on('keypress', buscarProduto.bind(this));
		this.campoQuantidade.on('keypress', quantidadeEnter.bind(this));
		this.campoValorUnitario.on('keypress', valorUnitarioEnter.bind(this));
		this.campoQuantidade.on('change', sairCampoQuantidade.bind(this));
		
		$(document).on('keypress', function(evento) {			
		    if(evento.which == 27) {
		    	evento.preventDefault();
		        location.href = '/';
		    }
		});
	}
	
	/*========== INICIAR VENDA ==========*/
	function iniciarNovaVenda() {
		var retorno = $.ajax({
			url: 'iniciar',
			method: 'GET',
			success: novaVendaIniciadaSuccess.bind(this),
			error: novaVendaIniciadaError.bind(this)
		});
	}
	
	function novaVendaIniciadaSuccess(response) {
		this.vendaStatus = true;
		
		this.campoNomeProduto.text('INFORME O CÓDIGO DO PRODUTO...');
		this.aviso.text('VENDA INICIADA');
		
		this.campoCodigoProduto.val('');
		this.campoCodigoProduto.focus();
	}
	
	function novaVendaIniciadaError(error) {
		this.vendaStatus = false;
		alert(error);
	}
	
	/*========== ADICIONAR CLIENTE ==========*/
	function adicionarCliente() {
		$('#modalPesquisaRapidaCliente').modal('show');

		this.campoCodigoProduto.val('');
		this.campoCodigoProduto.focus();
	}

	function buscarProduto(evento) {
		if(evento.which == 13 || evento.which == 9) {				
			evento.preventDefault();

			if(this.campoCodigoProduto.val() == ',1') {
				iniciarNovaVenda.call(this);
			} else if(this.campoCodigoProduto.val() == ',2' && this.vendaStatus) {
				adicionarCliente.call(this);
			} else if(this.campoCodigoProduto.val() == ',3' && this.vendaStatus) {
				this.formPdv.submit();
			} else {	
				/*========== ADICIONAR PRODUTO ==========*/
				if(this.campoCodigoProduto.val() != '' && this.vendaStatus) {	
					$.ajax({
						url: this.campoCodigoProduto.attr('data-url'),
						method: 'GET',
						data: {
							'codigoOuCodigoBarras': this.campoCodigoProduto.val(),
							'uuid': this.uuid,
						}, 
						success: retornoBuscarProdutoSuccess.bind(this),
						error: retornoBuscarProdutoError.bind(this)
					});			
				}
			}
		}
	}
	
	function retornoBuscarProdutoSuccess(dados) {	
		var data = dados[0];	
		
		this.itemDados = data;

		if(data != undefined) {
			trazerNomeProdutoValorUnitario.call(this, data);				
		} else {
			alert('PRODUTO NÃO ENCONTRADO!');
		}		
	}
	
	function retornoBuscarProdutoError(erro) {
		alert('Erro ao buscar o produto.');
	}
	
	function trazerNomeProdutoValorUnitario(data) {		
		this.campoNomeProduto.text(data.nome);
		this.campoValorUnitario.val(data.precoVenda.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }));	
		
		liparCampoCodigoFocoQuantidade.call(this);
	}
	
	function liparCampoCodigoFocoQuantidade() {
		this.campoCodigoProduto.val('');
		this.campoQuantidade.focus();
	}

	function quantidadeEnter(evento) {			
		if(evento.which == 13 || evento.which == 9) { 
			evento.preventDefault();
			
			var campoQuantidade = parseFloat(this.campoQuantidade.val());
			var quantidade = parseFloat(this.itemDados.quantidade);
			
			console.log(campoQuantidade, quantidade);
			
			if(campoQuantidade > quantidade) {				
				alert('QUANTIDADE INSUFICIENTE!');
			} else {
				this.campoQuantidadeOculta.val(this.campoQuantidade.val());
				this.campoValorUnitario.focus();
			}			
		}
	}
	
	function sairCampoQuantidade(evento) {
		evento.preventDefault();
		
		var valorUnitario = this.campoValorUnitario.val().replace('R$', '').replace('.', '').replace('.', '').replace('.', '').replace(',', '.');
		var quantidade = this.campoQuantidadeOculta.val().replace('.', '').replace('.', '').replace('.', '').replace(',', '.');
		var total = parseFloat(quantidade) * parseFloat(valorUnitario);		
		
		this.campoValorTotal.val(total.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }));
	}
	
	function valorUnitarioEnter(evento, data) {					
		if(evento.which == 13 || evento.which == 9) {		
			evento.preventDefault();

			if(this.campoQuantidade.val() != null && this.campoQuantidade.val() != undefined && this.campoQuantidade.val() != '') {				
				this.emitter.trigger('item-selecionado', this.itemDados);
				
				zerarValoresDosCampos.call(this);
			}	
		}
	}
	
	function zerarValoresDosCampos() {
		this.campoQuantidade.val('0,000');
		this.campoValorUnitario.val('R$ 0,00');
		this.campoValorTotal.val('R$ 0,00');
		
		this.campoNomeProduto.text('INFORME O CÓDIGO DO PRODUTO...');
		this.aviso.text('VENDA INICIADA');
		
		this.campoCodigoProduto.focus();
	}
	
	function limparCampoCodigoFocusQantidade() {
		this.campoCodigoProduto.val('');
		this.campoQuantidade.focus();
	}
	
	return BuscarProduto;
	
}());