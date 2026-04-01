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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_ocorrencia")
@NamedQueries({ 
	@NamedQuery(name = "Ocorrencia.listar", query = "SELECT ocorrencia FROM Ocorrencia ocorrencia ORDER BY ocorrencia.titulo ASC"),
	@NamedQuery(name = "Ocorrencia.buscarPorCodigo", query = "SELECT ocorrencia FROM Ocorrencia ocorrencia WHERE ocorrencia.codigo = :codigo")
})
public class Ocorrencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_ocorrencia")
	@SequenceGenerator(name =  "sequence_id_ocorrencia", sequenceName = "sequence_ocorrencia", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotEmpty(message = "O campo TITULO é obrigatrio.")
	@Column(name = "titulo", length = 50, nullable = false)
	private String titulo;
	
	@Column(name = "descricao", length = 100)
	private String descricao;
		
	@Column(name="abonar_descontar")
	private String abonarDescontar;
	
	@NotNull(message = "O campo HORA é obrigatório.")
	@Column(name="quantidade_horas", nullable = false)
	private Integer quantidadeHoras;
	
	@Column(name = "gera_hora_extra")
	private Boolean geraHoraExtra;


	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = (titulo != null) ? titulo.toUpperCase() : null;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAbonarDescontar() {
		return abonarDescontar;
	}

	public void setAbonarDescontar(String abonarDescontar) {
		this.abonarDescontar = abonarDescontar;
	}

	public Integer getQuantidadeHoras() {
		return quantidadeHoras;
	}

	public void setQuantidadeHoras(Integer quantidadeHoras) {
		this.quantidadeHoras = quantidadeHoras;
	}

	public Boolean getGeraHoraExtra() {
		return geraHoraExtra;
	}

	public void setGeraHoraExtra(Boolean geraHoraExtra) {
		this.geraHoraExtra = geraHoraExtra;
	}

	@Override
	public String toString() {
		return "Ocorrencia [codigo=" + codigo + ", titulo=" + titulo + ", descricao=" + descricao + ", abonarDescontar="
				+ abonarDescontar + ", quantidadeHoras=" + quantidadeHoras + ", geraHoraExtra=" + geraHoraExtra + "]";
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
		Ocorrencia other = (Ocorrencia) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
