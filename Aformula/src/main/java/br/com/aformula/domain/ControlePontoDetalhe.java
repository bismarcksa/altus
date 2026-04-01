package br.com.aformula.domain;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbl_controle_ponto_detalhe")
@NamedQueries({ 
	@NamedQuery(name = "ControlePontoDetalhe.listar", query = "SELECT controlePontoDetalhe FROM ControlePontoDetalhe controlePontoDetalhe WHERE controlePontoDetalhe.controlePontoDetalheID.funcionario.codigo = :codigo AND controlePontoDetalhe.controlePontoDetalheID.competencia = :competencia ORDER BY controlePontoDetalhe.controlePontoDetalheID.data"),
	@NamedQuery(name = "ControlePontoDetalhe.buscarPorCodigo", query = "SELECT controlePontoDetalhe FROM ControlePontoDetalhe controlePontoDetalhe WHERE controlePontoDetalhe.controlePontoDetalheID.funcionario.codigo = :codigo AND controlePontoDetalhe.controlePontoDetalheID.competencia = :competencia AND controlePontoDetalhe.controlePontoDetalheID.data = :data")
})
public class ControlePontoDetalhe {
	
	@Id
	private ControlePontoDetalheID controlePontoDetalheID;
	
	@Column(name = "controle_ponto_codigo")
	private Long controlePontoCodigo;
	
	@Column(name="dia")
	private String dia;
	
	@Column(name="entrada1")
	private Date entrada1;
	
	@Column(name="saida1")
	private Date saida1;
	
	@Column(name="entrada2")
	private Date entrada2;
	
	@Column(name="saida2")
	private Date saida2;
	
	@Column(name="entrada3")
	private Date entrada3;
	
	@Column(name="saida3")
	private Date saida3;
	
	@Column(name="horas_trabalhadas")
	private Date horas_trabalhadas;
	
	@Column(name="banco_hora")
	private Integer bancoHora;
	
	@Column(name="observacoes")
	private String observacoes;
	
	@Column(name = "feriado")
	private Boolean feriado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ocorrencia_codigo", referencedColumnName = "codigo", nullable = false)
	private Ocorrencia ocorrencia;
	
	@Transient
	private String bancoHoraFormatada;
	
	@Transient
	private Integer bancoHoraTotal;
	
	public ControlePontoDetalheID getControlePontoDetalheID() {
		return controlePontoDetalheID;
	}

	public void setControlePontoDetalheID(ControlePontoDetalheID controlePontoDetalheID) {
		this.controlePontoDetalheID = controlePontoDetalheID;
	}
	
	public Long getControlePontoCodigo() {
		return controlePontoCodigo;
	}

	public void setControlePontoCodigo(Long controlePontoCodigo) {
		this.controlePontoCodigo = controlePontoCodigo;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Date getEntrada1() {
		return entrada1;
	}

	public void setEntrada1(Date entrada1) {
		this.entrada1 = entrada1;
	}

	public Date getSaida1() {
		return saida1;
	}

	public void setSaida1(Date saida1) {
		this.saida1 = saida1;
	}

	public Date getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(Date entrada2) {
		this.entrada2 = entrada2;
	}

	public Date getSaida2() {
		return saida2;
	}

	public void setSaida2(Date saida2) {
		this.saida2 = saida2;
	}

	public Date getEntrada3() {
		return entrada3;
	}

	public void setEntrada3(Date entrada3) {
		this.entrada3 = entrada3;
	}

	public Date getSaida3() {
		return saida3;
	}

	public void setSaida3(Date saida3) {
		this.saida3 = saida3;
	}

	public Date getHoras_trabalhadas() {
		
//		System.out.println("--------------------------------------------");
//		System.out.println("FUNCIONARIO:" + this.controlePontoDetalheID.getFuncionario().getNome());
//		System.out.println("DATA:" + this.controlePontoDetalheID.getData());
//		System.out.println("BANCO DE HORAS:" + this.bancoHora);
		
//		if (this.bancoHoraTotal == null) {
//			this.bancoHoraTotal = 0;
//		}
//		this.bancoHoraTotal = this.bancoHoraTotal + this.bancoHora;
//		System.out.println("TOTAL:" + this.bancoHoraTotal);
		return horas_trabalhadas;
	}

	public void setHoras_trabalhadas(Date horas_trabalhadas) {
		this.horas_trabalhadas = horas_trabalhadas;
	}

	public Integer getBancoHora() {	
		return bancoHora;
	}

	public void setBancoHora(Integer bancoHora) {
		this.bancoHora = bancoHora;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Boolean getFeriado() {
		return feriado;
	}

	public void setFeriado(Boolean feriado) {
		this.feriado = feriado;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public String getBancoHoraFormatada() {	
		int horas = this.bancoHora / 60;
	    int minutos = this.bancoHora % 60;
	    
	    return String.format("%02d:%02d", horas, minutos);
	}

	public void setBancoHoraFormatada(String bancoHoraFormatada) {
		this.bancoHoraFormatada = bancoHoraFormatada;
	}

	public Integer getBancoHoraTotal() {	
		return bancoHoraTotal;
	}

	public void setBancoHoraTotal(Integer bancoHoraTotal) {
		this.bancoHoraTotal = bancoHoraTotal;
	}

	@Override
	public String toString() {
		return "ControlePontoDetalhe [controlePontoDetalheID=" + controlePontoDetalheID + ", controlePontoCodigo="
				+ controlePontoCodigo + ", dia=" + dia + ", entrada1=" + entrada1 + ", saida1=" + saida1 + ", entrada2="
				+ entrada2 + ", saida2=" + saida2 + ", entrada3=" + entrada3 + ", saida3=" + saida3
				+ ", horas_trabalhadas=" + horas_trabalhadas + ", bancoHora=" + bancoHora + ", observacoes="
				+ observacoes + ", feriado=" + feriado + ", ocorrencia=" + ocorrencia + ", bancoHoraFormatada="
				+ bancoHoraFormatada + ", bancoHoraTotal=" + bancoHoraTotal + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(controlePontoDetalheID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ControlePontoDetalhe other = (ControlePontoDetalhe) obj;
		return Objects.equals(controlePontoDetalheID, other.controlePontoDetalheID);
	}
	
}
