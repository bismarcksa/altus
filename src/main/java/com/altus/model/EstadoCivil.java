package com.altus.model;

public enum EstadoCivil {
	SOLTEIRO("SOLTEIRO(A)"),
	CASADO("CASADO(A)"),
	DIVORCIADO("DIVORCIADO(A)"),
	VIUVO("VIÚVO(A)");
	
	private String descricao;
	
	EstadoCivil(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
