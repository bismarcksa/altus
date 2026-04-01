package br.com.aformula.domain;
import java.util.Date;
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
@Table(name = "tbl_grupo")
@NamedQueries({ 
	@NamedQuery(name = "Grupo.listar", query = "SELECT grupo FROM Grupo grupo ORDER BY grupo.codigo DESC"),
	@NamedQuery(name = "Grupo.buscarPorCodigo", query = "SELECT grupo FROM Grupo grupo WHERE grupo.codigo = :codigo")
})
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_grupo")
	@SequenceGenerator(name =  "sequence_id_grupo", sequenceName = "sequence_grupo", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;

	@NotEmpty(message = "O campo DESCRIÇÃO é obrigatrio.")
	@Column(name = "descricao", length = 100, nullable = false)
	private String descricao;

	@Column(name = "foto", length = 255) // ou um tamanho maior, se necessário
	private String foto;
	
	@Column(name="data_cadastro")
	private Date data_cadastro;

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
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public Date getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(Date data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	@Override
	public String toString() {
		return "Grupo [codigo=" + codigo + ", descricao=" + descricao + ", foto=" + foto + ", data_cadastro="
				+ data_cadastro + "]";
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
		Grupo other = (Grupo) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
