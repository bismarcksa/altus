package br.com.aformula.domain;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_chatmensagem")
public class ChatMensagem {
    
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_chatmensagem")
	@SequenceGenerator(name =  "sequence_id_chatmensagem", sequenceName = "sequence_chatmensagem", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_origem", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario usuario_origem;	
	
	@Column(name = "usuario_destino")
	private Long usuario_destino;
    
	@Column(name = "mensagem")
    private String mensagem;
	
	@Column(name = "data")
    private Timestamp data;

	// getters and setters
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Funcionario getUsuario_origem() {
		return usuario_origem;
	}

	public void setUsuario_origem(Funcionario usuario_origem) {
		this.usuario_origem = usuario_origem;
	}

	public Long getUsuario_destino() {
		return usuario_destino;
	}

	public void setUsuario_destino(Long usuario_destino) {
		this.usuario_destino = usuario_destino;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ChatMensagem [codigo=" + codigo + ", usuario_origem=" + usuario_origem + ", usuario_destino="
				+ usuario_destino + ", mensagem=" + mensagem + ", data=" + data + "]";
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
		ChatMensagem other = (ChatMensagem) obj;
		return Objects.equals(codigo, other.codigo);
	}   

}


