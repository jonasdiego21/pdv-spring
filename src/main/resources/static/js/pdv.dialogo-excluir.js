/* imeph.dialogo-excluir.js */
var Pdv = Pdv || {};

Pdv.DialogoExcluir = (function() {
	
	function DialogoExcluir() {
		this.btnExcluir = $('.js-btn-excluir');		
	}
	
	DialogoExcluir.prototype.iniciar = function() {
		this.btnExcluir.on('click', onExcluir.bind(this));
	}
	
	function onExcluir(event) {
		event.preventDefault();
		
		this.url = $(event.currentTarget.attributes[2]).val();
		
		console.log(this.url);
		
		swal({
			  title: "Você tem certeza?",
			  text: "As informações não poderão ser recuperadas!",
			  icon: "warning",
			  buttons: ['Cancelar', 'Sim, exclua agora!'],
			  dangerMode: true,
		}).then((willDelete) => {
			if (willDelete) {
				onExcluidoSucesso(this.url);
			}
		});
	}
	
	function onExcluidoSucesso(url) {
		$.ajax({
			url: url,
			method: 'DELETE',
			success: function() {
				swal("Pronto! informações excluídas com sucesso!", {
					icon: "success",
				});
				
				time();
			}, error: function(erro) {
				swal("Oops! As informações não podem ser excluídas! ", {
					icon: "warning",
				});
			}
		});
	}
	
	function time() {		
		setTimeout(function() {
			window.location = window.location.href;
		}, 1000);		
	}
	
	return DialogoExcluir;

}());

$(function() {
	var dialogo = new Pdv.DialogoExcluir();
	dialogo.iniciar();
});