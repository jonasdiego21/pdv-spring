var Pdv = Pdv || {};

Pdv.Notifications = (function() {

	function Notifications() {
		//this.quantidadeProdutos = $('#quantidadeProdutosBaixoEstoque');
		//this.quantidadeClientes = $('#quantidadeClientesAniversariantes');
	}
	
	Notifications.prototype.start = function() {
		buscarProdutosBaixoEstoque();
		buscarClientesAniversariantes();
	}
	
	function buscarProdutosBaixoEstoque() {
		$.ajax({
			url: '/notifications/produtosBaixoEstoque',
			method: 'GET',
			success: function(produtos) {
				$('#quantidadeProdutosBaixoEstoque').text(produtos.length);
				
				if (produtos.length == 0) {
					$('#quantidadeProdutosBaixoEstoque').css('display', 'none');
				}
				
				produtos.map(item => {
					$('#itensProdutosBaixoEstoque').append(`
						<a class=\"dropdown-item d-flex align-items-center\" href=\"/produtos/${item.codigo}\">
						  <div class=\"mr-3\">
							<div class=\"icon-circle bg-secondary\">
							  <i class=\"fas fa-dolly text-white\"></i>
							</div>
						  </div>
						  <div>
							<div class=\"small text-gray-500\">${item.codigoBarras} - ${item.nome}</div>
							<span class=\"font-weight-bold\">Você tem ${item.quantidade} no estoque</span>
						  </div>
						</a>
	                `);
				});
			},
			error: function(error) {
				console.log(error);
			}
		});
	}
	
	function buscarClientesAniversariantes() {
		$.ajax({
			url: '/notifications/clientesAniversariantes',
			method: 'GET',
			success: function(clientes) {
				if (clientes.indexOf('CLIENTE AVULSO') == -1) {
					$('#quantidadeClientesAniversariantes').hide();
					$('#quantidadeClientesAniversariantes').text(clientes.length -1);	
				} else {
					$('#quantidadeClientesAniversariantes').show();
					$('#quantidadeClientesAniversariantes').text(clientes.length);
				}
				
				if (clientes.length == 0) {
					$('#quantidadeClientesAniversariantes').css('display', 'none');
				}
				
				clientes.map(item => {
					if (item.nome != 'CLIENTE AVULSO') {
						$('#itensClientesAniversariantes').append(`
							<a class=\"dropdown-item d-flex align-items-center\" href=\"/clientes/${item.codigo}\">
							  <div class=\"font-weight-bold\">
								<div class=\"text-truncate\">Parabéns ${item.nome}</div>
								<div class=\"small text-gray-500\">Nascido(a) em ${item.dataNascimento}</div>
							  </div>
							</a>
						`);
					}	
				})				
			},
			error: function(error) {
				console.log(error);
			}
		});
	}
	
	return Notifications;
}());

$(function() {
	let notifications = new Pdv.Notifications();
	notifications.start();
});