package br.com.aformula.domain;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SupervisaoEquipeID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supervisor_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario supervisor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;

	public Funcionario getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Funcionario supervisor) {
		this.supervisor = supervisor;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String toString() {
		return "SupervisaoEquipeID [supervisor=" + supervisor + ", funcionario=" + funcionario + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(funcionario, supervisor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupervisaoEquipeID other = (SupervisaoEquipeID) obj;
		return Objects.equals(funcionario, other.funcionario) && Objects.equals(supervisor, other.supervisor);
	}
	
}
