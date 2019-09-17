package br.com.jdrmservices.model.enumeration;

public enum StatusVenda {

	ORCAMENTO("ORÇAMENTO"),
	EMITIDA("EMITIDA"),
	CREDIARIO("CREDIÁRIO"),
	CANCELADA("CANCELADA");
	
	private String descricao;
	
	private StatusVenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}	
}
