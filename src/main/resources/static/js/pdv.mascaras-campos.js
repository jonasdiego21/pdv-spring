/* mascaras.js */
// Refatorar este javascript para remover duplicidade
var Pdv = Pdv || {};

Pdv.Mascaras = (function() {
	
	function Mascaras() {
		this.moeda = $(".moeda-formato");
		this.limiteCompra = $("#limiteCompra");
		this.dataNascimento = $("#dataNascimento");
		this.dataCompra = $("#dataCompra");
		this.dataVenda = $("#dataVenda");
		this.dataVencimento = $("#dataVencimento");
		this.dataInicio = $("#dataInicioPesquisa");
		this.dataFim = $("#dataFimPesquisa");
		this.cpf = $("#cpf");
		this.cnpj = $("#cnpj");
		this.telefone = $("#telefone");
		this.fixo = $("#fixo");
		this.comissao = $("#comissao");
		this.quantidade = $('#quantidade');
	}
	
	Mascaras.prototype.start = function() {
		this.limiteCompra.maskMoney({
			prefix:'R$ ', 
			allowNegative: true, 
			thousands:'.', 
			decimal:',', 
			affixesStay: false
		});
		
		this.moeda.maskMoney({
			prefix:'R$ ', 
			allowNegative: true, 
			thousands:'.', 
			decimal:',', 
			affixesStay: false
		});

		this.dataNascimento.mask("00/00/0000", {placeholder: "__/__/____"});
		this.dataCompra.mask("00/00/0000", {placeholder: "__/__/____"});
		this.dataVenda.mask("00/00/0000", {placeholder: "__/__/____"});
		this.dataVencimento.mask("00/00/0000", {placeholder: "__/__/____"});
		this.dataInicio.mask("00/00/0000", {placeholder: "__/__/____"});
		this.dataFim.mask("00/00/0000", {placeholder: "__/__/____"});
		this.cpf.mask("000.000.000-00", {placeholder: "___.___.___-__"});
		this.cnpj.mask("00.000.000/0000-00", {placeholder: "__.___.___/____-__"});
		this.telefone.mask("(00) 00000-0000", {placeholder: "(99) 99999-9999"});
		this.fixo.mask("(00) 0000-0000", {placeholder: "(99) 9999-9999"});
		
		this.quantidade.maskMoney({
			prefix: '', 
			precision: 3,
			allowNegative: true, 
			thousands:'.', 
			decimal:',', 
			affixesStay: false
		});
		
		this.comissao.maskMoney({
			prefix: '', 
			allowNegative: true, 
			thousands:'.', 
			decimal:',', 
			affixesStay: false
		});
	}
	
	return Mascaras;
	
}());

$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	$('.datepicker').datepicker({
		language: "pt-BR"
	});
    
	var mascaras = new Pdv.Mascaras();
	mascaras.start();
});