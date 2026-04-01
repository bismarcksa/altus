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
@Table(name = "tbl_analise_representante_grupo")
@NamedQueries({ 
	@NamedQuery(name = "AnaliseRepresentanteGrupo.listar", query = "SELECT analiseRepresentanteGrupo FROM AnaliseRepresentanteGrupo analiseRepresentanteGrupo ORDER BY analiseRepresentanteGrupo.codigo"),
	@NamedQuery(name = "AnaliseRepresentanteGrupo.buscarPorCodigo", query = "SELECT analiseRepresentanteGrupo FROM AnaliseRepresentanteGrupo analiseRepresentanteGrupo WHERE analiseRepresentanteGrupo.codigo = :codigo")
})
public class AnaliseRepresentanteGrupo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_analise_representante_grupo")
	@SequenceGenerator(name =  "sequence_id_analise_representante_grupo", sequenceName = "sequence_analise_representante_grupo", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull(message = "O campo ANO é obrigatório.")
	@Column(name="ano", nullable = false)
	private Integer ano;
	
	@NotEmpty(message = "O campo MÊS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotNull(message = "O campo GRUPO é obrigatório.")
	@Column(name="grupo", precision = 9, scale = 2, nullable = false)
	private BigDecimal grupo;
	
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

	public BigDecimal getGrupo() {
		return grupo;
	}

	public void setGrupo(BigDecimal grupo) {
		this.grupo = grupo;
	}

	public BigDecimal getVendas() {
		return vendas;
	}

	public void setVendas(BigDecimal vendas) {
		this.vendas = vendas;
	}

	public BigDecimal getPercentual() {
		double vendasconvertida = vendas.doubleValue();
		double grupoconvertida = grupo.doubleValue();
		double resultado = (vendasconvertida/grupoconvertida);
		 
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
		AnaliseRepresentanteGrupo other = (AnaliseRepresentanteGrupo) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "AnaliseRepresentanteGrupo [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", grupo=" + grupo
				+ ", vendas=" + vendas + ", percentual=" + percentual + "]";
	}	

	
}
