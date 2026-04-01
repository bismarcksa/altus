package br.com.aformula.domain;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ControlePontoDetalheID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="competencia", nullable = false)
	private String competencia;
	
	@Column(name="data")
	private Date data;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_funcionarios_fun_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String toString() {
		return "ControlePontoDetalheID [competencia=" + competencia + ", data=" + data + ", funcionario=" + funcionario
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(competencia, data, funcionario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ControlePontoDetalheID other = (ControlePontoDetalheID) obj;
		return Objects.equals(competencia, other.competencia) && Objects.equals(data, other.data)
				&& Objects.equals(funcionario, other.funcionario);
	}
	
}
