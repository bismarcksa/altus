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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_controle_ponto")
@NamedQueries({ 
	@NamedQuery(name = "ControlePonto.listar", query = "SELECT controlePonto FROM ControlePonto controlePonto WHERE controlePonto.status = 'ABERTO' ORDER BY controlePonto.status ASC, controlePonto.funcionario.nome ASC"),
	@NamedQuery(name = "ControlePonto.listarPorSupervisor", query = "SELECT cp FROM ControlePonto cp, SupervisaoEquipe se WHERE cp.funcionario.codigo = se.supervisaoEquipeID.funcionario.codigo AND cp.status = 'ABERTO' AND se.supervisaoEquipeID.supervisor.codigo = :codigoSupervisor"),
	@NamedQuery(name = "ControlePonto.listarFechado", query = "SELECT controlePonto FROM ControlePonto controlePonto WHERE controlePonto.status = 'FECHADO' AND controlePonto.funcionario.codigo = :codigoFuncionario ORDER BY controlePonto.codigo DESC"),
	@NamedQuery(name = "ControlePonto.buscarPorCodigo", query = "SELECT controlePonto FROM ControlePonto controlePonto WHERE controlePonto.competencia = :competencia AND controlePonto.funcionario.codigo = :codigoFuncionario"),
	@NamedQuery(name = "ControlePonto.listarControlePontoUsuario", query = "SELECT controlePonto FROM ControlePonto controlePonto WHERE controlePonto.funcionario.codigo = :codigo ORDER BY controlePonto.codigo DESC")
})
public class ControlePonto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_controle_ponto")
	@SequenceGenerator(name =  "sequence_id_controle_ponto", sequenceName = "sequence_controle_ponto", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;
	
	@NotNull(message = "O campo ANO é obrigatório.")
	@Column(name="ano", nullable = false)
	private Integer ano;
	
	@NotEmpty(message = "O campo MÊS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotEmpty(message = "O campo COMPETÊNCIA é obrigatório.")
	@Column(name="competencia", nullable = false)
	private String competencia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_func_fun_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;
	
	@Column(name="banco_hora")
	private Integer bancoHora;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_func_fun_codigo_gerente", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario gerente;

	@Column(name="data_aceite_gerente")
	private Date dataAceiteGerente;

	@Transient
	private String bancoHoraTexto;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getBancoHora() {		
		return bancoHora;
	}

	public void setBancoHora(Integer bancoHora) {
		this.bancoHora = bancoHora;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Funcionario getGerente() {
		return gerente;
	}

	public void setGerente(Funcionario gerente) {
		this.gerente = gerente;
	}

	public Date getDataAceiteGerente() {
		return dataAceiteGerente;
	}

	public void setDataAceiteGerente(Date dataAceiteGerente) {
		this.dataAceiteGerente = dataAceiteGerente;
	}
	

	public String getBancoHoraTexto() {
		Integer dia, hora, minuto;
		dia = this.bancoHora / 480; //480 É A QUANTIDADE DE MINUTOS DE UM DIA DE TRABALHO DE 8 HORAS 
		hora = (dia * 8) + ((this.bancoHora % 480) / 60); //DIVIDE O RESTO PARA SABER QUANTAS HORAS
		minuto = ((this.bancoHora % 480) % 60);
		String horaTexto, minutoTexto;				
		
		if(hora > 1) {
			horaTexto = "horas";
		}else {
			horaTexto = "hora";
		}
		
		if(minuto > 1) {
			minutoTexto = "minutos";
		}else {
			minutoTexto = "minuto";
		}
		
		this.bancoHoraTexto = hora + " " + horaTexto + " e " + minuto + " " + minutoTexto;
		return bancoHoraTexto;
	}

	public void setBancoHoraTexto(String bancoHoraTexto) {
		this.bancoHoraTexto = bancoHoraTexto;
	}

	

	@Override
	public String toString() {
		return "ControlePonto [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", competencia=" + competencia
				+ ", funcionario=" + funcionario + ", bancoHora=" + bancoHora + ", status=" + status + ", gerente="
				+ gerente + ", dataAceiteGerente=" + dataAceiteGerente + ", bancoHoraTexto=" + bancoHoraTexto + "]";
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
		ControlePonto other = (ControlePonto) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
