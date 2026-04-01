package br.com.aformula.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
@Table(name = "tbl_gestao_performance_func")
@NamedQueries({ 
	@NamedQuery(name = "GestaoPerformanceFunc.listar", query = "SELECT gestaoPerformanceFunc FROM GestaoPerformanceFunc gestaoPerformanceFunc WHERE gestaoPerformanceFunc.gestaoPerformance.funcionario = :funcionario AND gestaoPerformanceFunc.gestaoPerformance.ano = :ano ORDER BY gestaoPerformanceFunc.codigo"),
	@NamedQuery(name = "GestaoPerformanceFunc.buscarPorCodigo", query = "SELECT gestaoPerformanceFunc FROM GestaoPerformanceFunc gestaoPerformanceFunc WHERE gestaoPerformanceFunc.codigo = :codigo"),
	@NamedQuery(name = "GestaoPerformanceFunc.performanceFuncExiste", query = "SELECT gestaoPerformanceFunc FROM GestaoPerformanceFunc gestaoPerformanceFunc WHERE gestaoPerformanceFunc.gestaoPerformance.funcionario = :funcionario AND gestaoPerformanceFunc.gestaoPerformance.ano = :ano AND gestaoPerformanceFunc.mes = :mes")
})
public class GestaoPerformanceFunc {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_gestao_performance_func")
	@SequenceGenerator(name =  "sequence_id_gestao_performance_func", sequenceName = "sequence_gestao_performance_func", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_gestao_performance_codigo", referencedColumnName = "codigo", nullable = false)
	private GestaoPerformance gestaoPerformance;
	
	@NotEmpty(message = "O campo MêS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotNull(message = "O campo META é obrigatório.")
	@Column(name="meta", precision = 9, scale = 2, nullable = false)
	private BigDecimal meta;
	
	@NotNull(message = "O campo VENDA é obrigatório.")
	@Column(name="venda", precision = 9, scale = 2, nullable = false)
	private BigDecimal venda;
	
	@NotNull(message = "O campo REJEIÇÃO é obrigatório.")
	@Column(name="rejeicao", precision = 4, scale = 2, nullable = false)
	private BigDecimal rejeicao;
	
	@Transient
	private BigDecimal metaPercentual;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public BigDecimal getMeta() {
		return meta;
	}

	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}

	public BigDecimal getVenda() {
		return venda;
	}

	public void setVenda(BigDecimal venda) {
		this.venda = venda;
	}

	public BigDecimal getRejeicao() {
		return rejeicao;
	}

	public void setRejeicao(BigDecimal rejeicao) {
		this.rejeicao = rejeicao;
	}

	public BigDecimal getMetaPercentual() {
		double vendaconvertida = venda.doubleValue();
		double metaconvertida = meta.doubleValue();
		
		double resultado = (vendaconvertida/metaconvertida);
		 
		metaPercentual = new BigDecimal(resultado*100).setScale(2, RoundingMode.HALF_UP);		
		return metaPercentual;
	}

	public void setMetaPercentual(BigDecimal metaPercentual) {
		this.metaPercentual = metaPercentual;
	}
	
	public GestaoPerformance getGestaoPerformance() {
		return gestaoPerformance;
	}

	public void setGestaoPerformance(GestaoPerformance gestaoPerformance) {
		this.gestaoPerformance = gestaoPerformance;
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
		GestaoPerformanceFunc other = (GestaoPerformanceFunc) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "GestaoPerformanceFunc [codigo=" + codigo + ", gestaoPerformance=" + gestaoPerformance + ", mes=" + mes
				+ ", meta=" + meta + ", venda=" + venda + ", rejeicao=" + rejeicao + ", metaPercentual="
				+ metaPercentual + "]";
	}

}
