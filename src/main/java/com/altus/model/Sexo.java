package com.altus.model;

public enum Sexo {
	MASCULINO("MASCULINO"),
	FEMININO("FEMININO"),
	OUTROS("OUTROS");
	
	private String descricao;
	
	Sexo(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
