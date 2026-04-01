package br.com.aformula.domain;
import java.util.Date;
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
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_mensagem")
@NamedQueries({ 
	// VOU CORRIGIR AS REPETIÇÕES DESSA CONSULTA DENTRO DA CLASSE BEAN
//	@NamedQuery(name = "Mensagem.listarContato", query = "SELECT DISTINCT m FROM Mensagem m WHERE m.remetente.codigo = :codigoUsuario UNION SELECT DISTINCT m.remetente FROM Mensagem m WHERE m.destinatario.codigo = :codigoUsuario"),	
	@NamedQuery(name = "Mensagem.buscarPorCodigo", query = "SELECT mensagem FROM Mensagem mensagem WHERE mensagem.codigo = :codigo"),
	@NamedQuery(name = "Mensagem.listarMensagensFiltradas", query = "SELECT mensagem FROM Mensagem mensagem WHERE (mensagem.remetente = :remetente and mensagem.destinatario = :destinatario) or (mensagem.remetente = :destinatario and mensagem.destinatario = :remetente) order by mensagem.codigo"),
	@NamedQuery(name = "Mensagem.listarMensagensFiltradasGrupo", query = "SELECT mensagem FROM Mensagem mensagem WHERE mensagem.grupo.codigo = :grupo order by mensagem.codigo")
})
public class Mensagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_mensagem")
	@SequenceGenerator(name =  "sequence_id_mensagem", sequenceName = "sequence_mensagem", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "remetente", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario remetente;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "destinatario", referencedColumnName = "fun_codigo", nullable = true)
	private Funcionario destinatario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grupo", referencedColumnName = "codigo", nullable = true)
	private Grupo grupo;
	
	@Column(name="mensagem")
	private String mensagem;
	
	@Column(name="data_envio")
	private Date data_envio;
	
	@Column(name = "anexo", length = 255) // ou um tamanho maior, se necessário
	private String anexo;
	
	@Transient
	private boolean enviadaPorUsuario;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Funcionario getRemetente() {
		return remetente;
	}

	public void setRemetente(Funcionario remetente) {
		this.remetente = remetente;
	}

	public Funcionario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Funcionario destinatario) {
		this.destinatario = destinatario;
	}
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getData_envio() {
		return data_envio;
	}

	public void setData_envio(Date data_envio) {
		this.data_envio = data_envio;
	}

	public boolean isEnviadaPorUsuario() {
		return enviadaPorUsuario;
	}

	public void setEnviadaPorUsuario(boolean enviadaPorUsuario) {
		this.enviadaPorUsuario = enviadaPorUsuario;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	@Override
	public String toString() {
		return "Mensagem [codigo=" + codigo + ", remetente=" + remetente + ", destinatario=" + destinatario + ", grupo="
				+ grupo + ", mensagem=" + mensagem + ", data_envio=" + data_envio + ", anexo=" + anexo
				+ ", enviadaPorUsuario=" + enviadaPorUsuario + "]";
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
		Mensagem other = (Mensagem) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
