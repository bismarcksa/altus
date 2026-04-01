package br.com.aformula.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_metas_loja68")
@NamedQueries({ 
	@NamedQuery(name = "MetasLoja68.listar", query = "SELECT metasLoja68 FROM MetasLoja68 metasLoja68 ORDER BY metasLoja68.codigo"),	
	@NamedQuery(name = "MetasLoja68.buscarPorCodigo", query = "SELECT metasLoja68 FROM MetasLoja68 metasLoja68 WHERE metasLoja68.codigo = :codigo")
})
public class MetasLoja68 {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_metas_loja68")
	@SequenceGenerator(name =  "sequence_id_metas_loja68", sequenceName = "sequence_metas_loja68", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name="ano")
	private Integer ano;
	
	@NotEmpty(message = "O campo MÊS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotNull(message = "O campo META é obrigatório.")
	@Column(name="meta", precision = 9, scale = 2, nullable = false)
	private BigDecimal meta;
	
	@NotNull(message = "O campo VENDAS é obrigatório.")
	@Column(name="vendas", precision = 9, scale = 2, nullable = false)
	private BigDecimal vendas;

	@Transient
	private BigDecimal percentual;
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
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

	public BigDecimal getMeta() {
		return meta;
	}

	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}

	public BigDecimal getVendas() {
		return vendas;
	}

	public void setVendas(BigDecimal vendas) {
		this.vendas = vendas;
	}

	public BigDecimal getPercentual() {
		double vendasconvertida = vendas.doubleValue();
		double metaconvertida = meta.doubleValue();
		double resultado = (vendasconvertida/metaconvertida);
		 
		percentual = new BigDecimal(resultado*100).setScale(2, RoundingMode.HALF_UP);
		
		return percentual;
	}

	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
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
		MetasLoja68 other = (MetasLoja68) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "MetasLoja68 [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", meta=" + meta + ", vendas="
				+ vendas + "]";
	}	
}
