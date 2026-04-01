package br.com.aformula.domain;
import java.io.Serializable;
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
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "tbl_filial")
@NamedQueries({ 
	@NamedQuery(name = "Filial.listar", query = "SELECT filial FROM Filial filial"),
	@NamedQuery(name = "Filial.listarAtivo", query = "SELECT filial FROM Filial filial WHERE filial.status = true"),
	@NamedQuery(name = "Filial.buscarPorCodigo", query = "SELECT filial FROM Filial filial WHERE filial.codigo = :codigo")
})
public class Filial implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_filial")
	@SequenceGenerator(name =  "sequence_id_filial", sequenceName = "sequence_filial", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@NotEmpty(message = "O campo RAZÃO SOCIAL é obrigatório.")
	@Size(min = 3, max= 100, message = "Tamanho inválido para a RAZÃO SOCIAL.")
	@Column(name = "razao_social", length = 100, nullable = false)
	private String razaoSocial;

	@CNPJ(message = "O CNPJ informado é inválido")
	@Column(name = "cnpj", length = 18, nullable = false, unique = true)
	private String cnpj;
	
	@Column(name="status")
	private boolean status;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Filial [codigo=" + codigo + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", status=" + status
				+ "]";
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
		Filial other = (Filial) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
