package com.altus.model;

public enum Situacao {
	ATIVO("ATIVO"),
	AFASTADO("AFASTADO"),
	DEMITIDO("DEMITIDO"),
	FERIAS("FERIAS");
	
	private String descricao;
	
	Situacao(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
