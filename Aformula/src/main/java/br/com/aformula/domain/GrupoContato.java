package br.com.aformula.domain;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_grupo_contato")
@NamedQueries({ 
	@NamedQuery(name = "GrupoContato.listar", query = "SELECT grupoContato FROM GrupoContato grupoContato WHERE grupoContato.grupoContatoID.grupo.codigo = :codigo"),
	@NamedQuery(name = "GrupoContato.buscarPorCodigo", query = "SELECT grupoContato FROM GrupoContato grupoContato WHERE grupoContato.grupoContatoID.grupo.codigo = :codigo"),
	@NamedQuery(name = "GrupoContato.buscarGrupoFunc", query = "SELECT grupoContato FROM GrupoContato grupoContato WHERE grupoContato.grupoContatoID.funcionario.codigo = :fun_codigo"),
	@NamedQuery(name = "GrupoContato.buscarContatoPorCodigoGrupo", query = "SELECT grupoContato FROM GrupoContato grupoContato WHERE grupoContato.grupoContatoID.funcionario.codigo = :codigoContato AND grupoContato.grupoContatoID.grupo.codigo = :codigoGrupo")
})
public class GrupoContato {
	
	@Id
	private GrupoContatoID grupoContatoID;

	@Column(name = "mensagem_nao_lida")
	private Integer mensagemNaoLida;
	
	public GrupoContatoID getGrupoContatoID() {
		return grupoContatoID;
	}

	public void setGrupoContatoID(GrupoContatoID grupoContatoID) {
		this.grupoContatoID = grupoContatoID;
	}

	public Integer getMensagemNaoLida() {
		return mensagemNaoLida;
	}

	public void setMensagemNaoLida(Integer mensagemNaoLida) {
		this.mensagemNaoLida = mensagemNaoLida;
	}

	@Override
	public int hashCode() {
		return Objects.hash(grupoContatoID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoContato other = (GrupoContato) obj;
		return Objects.equals(grupoContatoID, other.grupoContatoID);
	}
	
}
