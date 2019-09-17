package br.com.jdrmservices.model.enumeration;

public enum Status {
	PAGO("PAGO"),
	DEVENDO("DEVENDO");
	
	private String descricao;
	
	Status(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
