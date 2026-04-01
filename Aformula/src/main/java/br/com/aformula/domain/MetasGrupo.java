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
@Table(name = "tbl_metas_grupo")
@NamedQueries({ 
	@NamedQuery(name = "MetasGrupo.listar", query = "SELECT metasGrupo FROM MetasGrupo metasGrupo ORDER BY metasGrupo.codigo"),	
	@NamedQuery(name = "MetasGrupo.buscarPorCodigo", query = "SELECT metasGrupo FROM MetasGrupo metasGrupo WHERE metasGrupo.codigo = :codigo")
})
public class MetasGrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_metas_grupo")
	@SequenceGenerator(name =  "sequence_id_metas_grupo", sequenceName = "sequence_metas_grupo", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name="ano")
	private Integer ano;
	
	@NotEmpty(message = "O campo MÊS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotNull(message = "O campo META é obrigatorio.")
	@Column(name="meta", precision = 9, scale = 2, nullable = false)
	private BigDecimal meta;
	
	@Column(name="vendas", precision = 9, scale = 2, nullable = false)
	private BigDecimal vendas;

	@Transient
	private BigDecimal percentual;
	
	@Transient
	private String periodo;
	
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

	public String getPeriodo() {
		periodo = mes.substring(0, 3) + "/" + ano.toString();
		
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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
		MetasGrupo other = (MetasGrupo) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "MetasGrupo [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", meta=" + meta + ", vendas="
				+ vendas + ", percentual=" + percentual + ", periodo=" + periodo + "]";
	}

	
}
