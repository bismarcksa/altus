package br.com.aformula.domain;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_descricao_cargo")
@NamedQueries({ 
	@NamedQuery(name = "DescricaoCargo.listar", query = "SELECT descricaoCargo FROM DescricaoCargo descricaoCargo"),
	@NamedQuery(name = "DescricaoCargo.buscarPorCodigo", query = "SELECT descricaoCargo FROM DescricaoCargo descricaoCargo WHERE descricaoCargo.codigo = :codigo")
})
public class DescricaoCargo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_descricao_cargo")
	@SequenceGenerator(name =  "sequence_id_descricao_cargo", sequenceName = "sequence_descricao_cargo", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;

	@NotNull(message = "O campo CARGO é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cargo", referencedColumnName = "codigo", nullable = false)
	private Cargo cargo;

	@Column(name="qualid_comport_obrig")
	private String qualid_comport_obrig;
	
	@Column(name="qualid_desejavel")
	private String qualid_desejavel;
	
	@Column(name="conhec_tec_obrig")
	private String conhec_tec_obrig;
	
	@Column(name="conhec_tec_desejavel")
	private String conhec_tec_desejavel;
	
	@Column(name="informacoes_adicionais")
	private String informacoes_adicionais;
	

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getQualid_comport_obrig() {
		return qualid_comport_obrig;
	}

	public void setQualid_comport_obrig(String qualid_comport_obrig) {
		this.qualid_comport_obrig = qualid_comport_obrig;
	}

	public String getQualid_desejavel() {
		return qualid_desejavel;
	}

	public void setQualid_desejavel(String qualid_desejavel) {
		this.qualid_desejavel = qualid_desejavel;
	}

	public String getConhec_tec_obrig() {
		return conhec_tec_obrig;
	}

	public void setConhec_tec_obrig(String conhec_tec_obrig) {
		this.conhec_tec_obrig = conhec_tec_obrig;
	}

	public String getConhec_tec_desejavel() {
		return conhec_tec_desejavel;
	}

	public void setConhec_tec_desejavel(String conhec_tec_desejavel) {
		this.conhec_tec_desejavel = conhec_tec_desejavel;
	}

	public String getInformacoes_adicionais() {
		return informacoes_adicionais;
	}

	public void setInformacoes_adicionais(String informacoes_adicionais) {
		this.informacoes_adicionais = informacoes_adicionais;
	}

	@Override
	public String toString() {
		return "DescricaoCargo [codigo=" + codigo + ", cargo=" + cargo + ", qualid_comport_obrig="
				+ qualid_comport_obrig + ", qualid_desejavel=" + qualid_desejavel + ", conhec_tec_obrig="
				+ conhec_tec_obrig + ", conhec_tec_desejavel=" + conhec_tec_desejavel + ", informacoes_adicionais="
				+ informacoes_adicionais + "]";
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
		DescricaoCargo other = (DescricaoCargo) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
