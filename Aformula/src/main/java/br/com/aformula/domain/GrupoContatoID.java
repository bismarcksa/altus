package br.com.aformula.domain;
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class GrupoContatoID implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grupo_codigo", referencedColumnName = "codigo", nullable = false)
	private Grupo grupo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public String toString() {
		return "GrupoContatoID [grupo=" + grupo + ", funcionario=" + funcionario + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(funcionario, grupo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoContatoID other = (GrupoContatoID) obj;
		return Objects.equals(funcionario, other.funcionario) && Objects.equals(grupo, other.grupo);
	}
	
}
