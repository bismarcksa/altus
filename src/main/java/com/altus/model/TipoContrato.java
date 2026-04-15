package com.altus.model;

public enum TipoContrato {
	CLT("CLT"),
	PJ("Pessoa Jurídica"),
	ESTAGIO("Estágio");
	
	private String descricao;
	
	TipoContrato(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
