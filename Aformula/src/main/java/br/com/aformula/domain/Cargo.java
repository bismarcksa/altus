package br.com.aformula.domain;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_cargo")
@NamedQueries({ 
	@NamedQuery(name = "Cargo.listar", query = "SELECT cargo FROM Cargo cargo"),
	@NamedQuery(name = "Cargo.buscarPorCodigo", query = "SELECT cargo FROM Cargo cargo WHERE cargo.codigo = :codigo")
})
public class Cargo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_cargo")
	@SequenceGenerator(name =  "sequence_id_cargo", sequenceName = "sequence_cargo", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;

	@NotEmpty(message = "O campo DESCRIÇÃO é obrigatório.")
	@Size(min = 3, max= 100, message = "Tamanho inválido para a DESCRiÇÃO.")
	@Column(name = "descricao", length = 100, nullable = false)
	private String descricao;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Cargo [codigo=" + codigo + ", descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cargo other = (Cargo) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
