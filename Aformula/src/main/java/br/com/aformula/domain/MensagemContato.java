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
@Table(name = "tbl_mensagem_contato")
@NamedQueries({ 
	@NamedQuery(name = "MensagemContato.listar", query = "SELECT mensagemContato FROM MensagemContato mensagemContato WHERE mensagemContato.usuario.codigo = :codigoUsuario AND mensagemContato.grupo IS NULL ORDER BY mensagemContato.mensagemNaoLida DESC"),
	@NamedQuery(name = "MensagemContato.buscarContatoPorCodigo", query = "SELECT mensagemContato FROM MensagemContato mensagemContato WHERE mensagemContato.contato.codigo = :codigoContato AND mensagemContato.usuario.codigo = :codigoUsuario AND mensagemContato.grupo IS NULL"),
	@NamedQuery(name = "MensagemContato.buscarContatoPorCodigoGrupo", query = "SELECT mensagemContato FROM MensagemContato mensagemContato WHERE mensagemContato.contato.codigo = :codigoContato AND mensagemContato.usuario.codigo = :codigoUsuario AND mensagemContato.grupo.codigo = :codigoGrupo"),
	@NamedQuery(name = "MensagemContato.buscarPorCodigo", query = "SELECT mensagemContato FROM MensagemContato mensagemContato WHERE mensagemContato.codigo = :codigo")
})
public class MensagemContato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_mensagemContato")
	@SequenceGenerator(name =  "sequence_id_mensagemContato", sequenceName = "sequence_mensagemContato", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@NotNull(message = "O campo USUARIO é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario usuario;

	@NotNull(message = "O campo CONTATO é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contato", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario contato;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grupo", referencedColumnName = "codigo")
	private Grupo grupo;

	@Column(name = "mensagem_nao_lida")
	private Integer mensagemNaoLida;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Funcionario getUsuario() {
		return usuario;
	}

	public void setUsuario(Funcionario usuario) {
		this.usuario = usuario;
	}

	public Funcionario getContato() {
		return contato;
	}

	public void setContato(Funcionario contato) {
		this.contato = contato;
	}

	public Integer getMensagemNaoLida() {
		return mensagemNaoLida;
	}

	public void setMensagemNaoLida(Integer mensagemNaoLida) {
		this.mensagemNaoLida = mensagemNaoLida;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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
		MensagemContato other = (MensagemContato) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
