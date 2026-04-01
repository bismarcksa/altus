package br.com.aformula.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_fechamento_producao")
@NamedQueries({ 
	@NamedQuery(name = "FechamentoProducao.listar", query = "SELECT fechamentoProducao FROM FechamentoProducao fechamentoProducao ORDER BY fechamentoProducao.codigo DESC"),
	@NamedQuery(name = "FechamentoProducaoMesFechado.listar", query = "SELECT fechamentoProducao FROM FechamentoProducao fechamentoProducao WHERE fechamentoProducao.status = 'FECHADO' ORDER BY fechamentoProducao.codigo ASC"),
	@NamedQuery(name = "FechamentoProducao.buscarPorCodigo", query = "SELECT fechamentoProducao FROM FechamentoProducao fechamentoProducao WHERE fechamentoProducao.codigo = :codigo")
})
public class FechamentoProducao {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_fechamento_producao")
	@SequenceGenerator(name =  "sequence_fechamento_producao", sequenceName = "sequence_fechamento_producao", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@Column(name="ano")
	private Integer ano;
	
	@Column(name="mes", nullable = false)
	private String mes;
	
	@Column(name="total_contratos", nullable = false)
	private Long total_contratos;
	
	@Column(name="total_sache", nullable = false)
	private Long total_sache;
	
	@Column(name="total_capsula", nullable = false)
	private Long total_capsula;
	
	@Column(name="total_shake", nullable = false)
	private Long total_shake;

	@Column(name="total_envase", nullable = false)
	private Long total_envase;
	
	@Column(name="total_producao_sache", nullable = false)
	private Long total_producao_sache;
	
	@Column(name="total_producao_capsula", nullable = false)
	private Long total_producao_capsula;
	
	@Column(name="media_contrato_sache", nullable = false)
	private Double media_contrato_sache;
	
	@Column(name="media_contrato_capsula", nullable = false)
	private Double media_contrato_capsula;
	
	@Column(name="status")
	private String status;

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

	public Long getTotal_contratos() {
		return total_contratos;
	}

	public void setTotal_contratos(Long total_contratos) {
		this.total_contratos = total_contratos;
	}

	public Long getTotal_sache() {
		return total_sache;
	}

	public void setTotal_sache(Long total_sache) {
		this.total_sache = total_sache;
	}

	public Long getTotal_capsula() {
		return total_capsula;
	}

	public void setTotal_capsula(Long total_capsula) {
		this.total_capsula = total_capsula;
	}
	
	public long getTotal_shake() {
		return total_shake;
	}

	public void setTotal_shake(Long total_shake) {
		this.total_shake = total_shake;
	}

	public Long getTotal_envase() {
		return total_envase;
	}

	public void setTotal_envase(Long total_envase) {
		this.total_envase = total_envase;
	}

	public Long getTotal_producao_sache() {
		return total_producao_sache;
	}

	public void setTotal_producao_sache(Long total_producao_sache) {
		this.total_producao_sache = total_producao_sache;
	}

	public Long getTotal_producao_capsula() {
		return total_producao_capsula;
	}

	public void setTotal_producao_capsula(Long total_producao_capsula) {
		this.total_producao_capsula = total_producao_capsula;
	}

	public Double getMedia_contrato_sache() {
		return media_contrato_sache;
	}

	public void setMedia_contrato_sache(Double media_contrato_sache) {
		this.media_contrato_sache = media_contrato_sache;
	}

	public Double getMedia_contrato_capsula() {
		return media_contrato_capsula;
	}

	public void setMedia_contrato_capsula(Double media_contrato_capsula) {
		this.media_contrato_capsula = media_contrato_capsula;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	@Override
	public String toString() {
		return "FechamentoProducao [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", total_contratos="
				+ total_contratos + ", total_sache=" + total_sache + ", total_capsula=" + total_capsula
				+ ", total_shake=" + total_shake + ", total_envase=" + total_envase + ", total_producao_sache="
				+ total_producao_sache + ", total_producao_capsula=" + total_producao_capsula
				+ ", media_contrato_sache=" + media_contrato_sache + ", media_contrato_capsula="
				+ media_contrato_capsula + ", status=" + status + "]";
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
		FechamentoProducao other = (FechamentoProducao) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
