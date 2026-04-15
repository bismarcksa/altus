package com.altus.model;

public enum Tipo {
	MATRIZ("MATRIZ"),
	FILIAL("FILIAL");
	
	private String descricao;
	
	Tipo(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
