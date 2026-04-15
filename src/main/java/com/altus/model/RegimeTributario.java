package com.altus.model;

public enum RegimeTributario {
	MEI("MEI"),
	SIMPLES_NACIONAL("Simples Nacional"),
	LUCRO_PRESUMIDO("Lucro Presumido"),
	LUCRO_REAL("Lucro Real");
	
	private String descricao;
	
	RegimeTributario(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
