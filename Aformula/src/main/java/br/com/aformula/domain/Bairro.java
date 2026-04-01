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

@Entity
@Table(name = "tbl_bairro")
@NamedQueries({ 
	@NamedQuery(name = "Bairro.listar", query = "SELECT bairro FROM Bairro bairro"),
	@NamedQuery(name = "Bairro.buscarPorCodigo", query = "SELECT bairro FROM Bairro bairro WHERE bairro.codigo = :codigo")
})
public class Bairro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_bairro")
	@SequenceGenerator(name =  "sequence_id_bairro", sequenceName = "sequence_bairro", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;

	@NotEmpty(message = "O campo DESCRIÇÃO é obrigatrio.")
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
		return "Bairro [codigo=" + codigo + ", descricao=" + descricao + "]";
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
		Bairro other = (Bairro) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
