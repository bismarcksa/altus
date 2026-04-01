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
@Table(name = "tbl_entregador")
@NamedQueries({ 
	@NamedQuery(name = "Entregador.listar", query = "SELECT entregador FROM Entregador entregador"),
	@NamedQuery(name = "Entregador.buscarPorCodigo", query = "SELECT entregador FROM Entregador entregador WHERE entregador.codigo = :codigo")
})
public class Entregador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_entregador")
	@SequenceGenerator(name =  "sequence_id_entregador", sequenceName = "sequence_entregador", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@NotEmpty(message = "O campo NOME é obrigatório.")
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@NotEmpty(message = "O campo VÍNCULO é obrigatório.")
	@Column(name = "vinculo", nullable = false)
	private String vinculo;
	
	@Column(name="transportadora")
	private boolean transportadora;
	
	@Column(name="status")
	private boolean status;

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
	
	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}
	
	public boolean isTransportadora() {
		return transportadora;
	}

	public void setTransportadora(boolean transportadora) {
		this.transportadora = transportadora;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

	@Override
	public String toString() {
		return "Entregador [codigo=" + codigo + ", nome=" + nome + ", vinculo=" + vinculo + ", transportadora="
				+ transportadora + ", status=" + status + "]";
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
		Entregador other = (Entregador) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
