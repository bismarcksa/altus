package br.com.aformula.domain;

import java.math.BigDecimal;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_indicadores_ticketmedio")
@NamedQueries({ 
	@NamedQuery(name = "IndicadoresTicketMedio.listar", query = "SELECT indicadoresTicketMedio FROM IndicadoresTicketMedio indicadoresTicketMedio ORDER BY indicadoresTicketMedio.codigo DESC"),	
	@NamedQuery(name = "IndicadoresTicketMedio.listarDesc", query = "SELECT indicadoresTicketMedio FROM IndicadoresTicketMedio indicadoresTicketMedio ORDER BY indicadoresTicketMedio.codigo DESC"),
	@NamedQuery(name = "IndicadoresTicketMedio.buscarPorCodigo", query = "SELECT indicadoresTicketMedio FROM IndicadoresTicketMedio indicadoresTicketMedio WHERE indicadoresTicketMedio.codigo = :codigo"),
	@NamedQuery(name = "IndicadoresTicketMedio.ticketMedioExiste", query = "SELECT indicadoresTicketMedio FROM IndicadoresTicketMedio indicadoresTicketMedio WHERE indicadoresTicketMedio.mes = :mes AND indicadoresTicketMedio.ano = :ano")
})
public class IndicadoresTicketMedio {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_indicadores_ticketmedio")
	@SequenceGenerator(name =  "sequence_id_indicadores_ticketmedio", sequenceName = "sequence_indicadores_ticketmedio", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name="ano")
	private Integer ano;
	
	@NotEmpty(message = "O campo MÊS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotNull(message = "O campo LOJA 67 é obrigatório.")
	@Column(name="filial01", precision = 4, scale = 2, nullable = false)
	private BigDecimal filial01;
	
	@NotNull(message = "O campo LOJA 68 é obrigatório.")
	@Column(name="filial02", precision = 4, scale = 2, nullable = false)
	private BigDecimal filial02;
	
	@NotNull(message = "O campo LOJA 70 é obrigatório.")
	@Column(name="filial03", precision = 4, scale = 2, nullable = false)
	private BigDecimal filial03;
	
	@NotNull(message = "O campo GRUPO é obrigatório.")
	@Column(name="grupo", precision = 4, scale = 2, nullable = false)
	private BigDecimal grupo;

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
	
	public BigDecimal getFilial01() {
		return filial01;
	}

	public void setFilial01(BigDecimal filial01) {
		this.filial01 = filial01;
	}

	public BigDecimal getFilial02() {
		return filial02;
	}

	public void setFilial02(BigDecimal filial02) {
		this.filial02 = filial02;
	}

	public BigDecimal getFilial03() {
		return filial03;
	}

	public void setFilial03(BigDecimal filial03) {
		this.filial03 = filial03;
	}

	public BigDecimal getGrupo() {
		return grupo;
	}

	public void setGrupo(BigDecimal grupo) {
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
		IndicadoresTicketMedio other = (IndicadoresTicketMedio) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "IndicadoresTicketMedio [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", filial01=" + filial01
				+ ", filial02=" + filial02 + ", filial03=" + filial03 + ", grupo=" + grupo + "]";
	}	
}
