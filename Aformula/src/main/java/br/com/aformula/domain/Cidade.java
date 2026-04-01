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

@Entity
@Table(name = "tbl_cidade")
@NamedQueries({ 
	@NamedQuery(name = "Cidade.listar", query = "SELECT cidade FROM Cidade cidade"),
	@NamedQuery(name = "Cidade.buscarPorCodigo", query = "SELECT cidade FROM Cidade cidade WHERE cidade.codigo = :codigo")
})
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_cidade")
	@SequenceGenerator(name =  "sequence_id_cidade", sequenceName = "sequence_cidade", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "codigo_ibge")
	private String codigo_ibge;

	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "uf", length = 2, nullable = false)
	private String uf;
	

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo_ibge() {
		return codigo_ibge;
	}

	public void setCodigo_ibge(String codigo_ibge) {
		this.codigo_ibge = codigo_ibge;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "Cidade [codigo=" + codigo + ", codigo_ibge=" + codigo_ibge + ", nome=" + nome + ", uf=" + uf + "]";
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
		Cidade other = (Cidade) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
