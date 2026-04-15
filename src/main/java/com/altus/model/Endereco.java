package com.altus.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Endereco implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "logradouro")
    @NotBlank(message = "Logradouro é obrigatório.")
    private String logradouro;
    
    @Column(name = "numero")
    @NotBlank(message = "Número é obrigatório.")
    private String numero;
    
    @Column(name = "complemento")
    private String complemento;

    @Column(name = "cep")
    @NotBlank(message = "CEP é obrigatório.")
    private String cep;
    
    @Column(name = "bairro")
    @NotBlank(message = "Bairro é obrigatório.")
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "codigo_cidade")
    @NotNull(message="Cidade é obrigatótio.")
    private Cidade cidade;

    @jakarta.persistence.Transient
    @NotNull(message="Estado é obrigatótio.")
    private Estado estado;
    
    
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        // REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
        this.cep = cep != null ? cep.replaceAll("\\D", "") : null;
    }
    
    public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public String getNomeCidadeSiglaEstado() {
		if(this.cidade != null) {
			return this.cidade.getNome() + "/" + this.cidade.getEstado().getSigla();
		}
		
		return null;
	}
    
}
