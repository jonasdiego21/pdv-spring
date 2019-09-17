package br.com.jdrmservices.model.enumeration;

public enum FormaPagamento {

	DINHEIRO("DINHEIRO"),
	CREDIARIO("CREDIÁRIO"),
	DEBITO("DÉBITO"),
	CREDITO("CRÉDITO"),
	CHEQUE("CHEQUE"), 
	NENHUMA("CANCELADA");
	
	private String descricao;
	
	FormaPagamento(String descricao) {
		this.descricao = descricao;
	}
	
	public String getFormaPagamento() {
		return this.descricao;
	}
}
