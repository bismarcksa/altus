package com.altus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable{
    private static final long serialVersionUID = -354972066147544764L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
    private Long codigo;
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @Column(name="codigo_ibge")
    private String codigo_ibge;

    @NotNull(message = "Estado é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_estado")
    @JsonIgnore
    private Estado estado;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo_ibge() {
		return codigo_ibge;
	}

	public void setCodigo_ibge(String codigo_ibge) {
		this.codigo_ibge = codigo_ibge;
	}

	public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public boolean temEstado() {
    		return this.estado != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(codigo, cidade.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
