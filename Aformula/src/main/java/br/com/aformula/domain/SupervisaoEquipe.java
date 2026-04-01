package br.com.aformula.domain;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_supervisao_equipe")
@NamedQueries({ 
	@NamedQuery(name = "SupervisaoEquipe.listar", query = "SELECT DISTINCT supervisaoEquipe.supervisaoEquipeID.supervisor FROM SupervisaoEquipe supervisaoEquipe WHERE supervisaoEquipe.supervisaoEquipeID.supervisor.lideranca = true"),
	@NamedQuery(name = "SupervisaoEquipe.buscarPorCodigo", query = "SELECT supervisaoEquipe FROM SupervisaoEquipe supervisaoEquipe WHERE supervisaoEquipe.supervisaoEquipeID.supervisor.codigo = :codigoSupervisor"),
	@NamedQuery(name = "SupervisaoEquipe.buscarPorFuncionario", query = "SELECT supervisaoEquipe.supervisaoEquipeID.funcionario FROM SupervisaoEquipe supervisaoEquipe WHERE supervisaoEquipe.supervisaoEquipeID.funcionario.codigo = :codigoFuncionario")
})
public class SupervisaoEquipe {
	
	@Id
	private SupervisaoEquipeID supervisaoEquipeID;
	
	public SupervisaoEquipeID getSupervisaoEquipeID() {
		return supervisaoEquipeID;
	}

	public void setSupervisaoEquipeID(SupervisaoEquipeID supervisaoEquipeID) {
		this.supervisaoEquipeID = supervisaoEquipeID;
	}


	@Override
	public int hashCode() {
		return Objects.hash(supervisaoEquipeID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SupervisaoEquipe other = (SupervisaoEquipe) obj;
		return Objects.equals(supervisaoEquipeID, other.supervisaoEquipeID);
	}
	
}
