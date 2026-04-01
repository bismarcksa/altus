package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "estilo")
public class Estilo implements Serializable{
    private static final long serialVersionUID = -1L;

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_estilo")
	@SequenceGenerator(name =  "sequence_estilo", sequenceName = "sequence_estilo", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
    private Long codigo;

    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @OneToMany(mappedBy = "estilo")
    private List<Cerveja> cervejaList;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Cerveja> getCervejaList() {
        return cervejaList;
    }

    public void setCervejaList(List<Cerveja> cervejaList) {
        this.cervejaList = cervejaList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estilo estilo = (Estilo) o;
        return Objects.equals(codigo, estilo.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

	@Override
	public String toString() {
		return "Estilo [codigo=" + codigo + ", descricao=" + descricao + "]";
	}
    
}
