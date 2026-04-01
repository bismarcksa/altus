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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_indicadores_cliente")
@NamedQueries({ 
	@NamedQuery(name = "IndicadoresCliente.listar", query = "SELECT indicadoresCliente FROM IndicadoresCliente indicadoresCliente ORDER BY indicadoresCliente.codigo DESC"),
	@NamedQuery(name = "IndicadoresCliente.listarDesc", query = "SELECT indicadoresCliente FROM IndicadoresCliente indicadoresCliente ORDER BY indicadoresCliente.codigo DESC"),
	@NamedQuery(name = "IndicadoresCliente.buscarPorCodigo", query = "SELECT indicadoresCliente FROM IndicadoresCliente indicadoresCliente WHERE indicadoresCliente.codigo = :codigo"),
	@NamedQuery(name = "IndicadoresCliente.clienteExiste", query = "SELECT indicadoresCliente FROM IndicadoresCliente indicadoresCliente WHERE indicadoresCliente.mes = :mes AND indicadoresCliente.ano = :ano")
})
public class IndicadoresCliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_indicadores_cliente")
	@SequenceGenerator(name =  "sequence_id_indicadores_cliente", sequenceName = "sequence_indicadores_cliente", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name="ano")
	private Integer ano;
	
	@NotEmpty(message = "O campo MÊS é obrigatório.")
	@Column(name="mes", nullable = false)
	private String mes;
	
	@NotNull(message = "O campo LOJA 67 é obrigatório.")
	@Column(name="filial01", nullable = false)
	private Integer filial01;
	
	@NotNull(message = "O campo LOJA 68 é obrigatório.")
	@Column(name="filial02", nullable = false)
	private Integer filial02;
	
	@NotNull(message = "O campo LOJA 70 é obrigatório.")
	@Column(name="filial03", nullable = false)
	private Integer filial03;
	
	@NotNull(message = "O campo GRUPO é obrigatório.")
	@Column(name="grupo", nullable = false)
	private Integer grupo;

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
	
	public Integer getFilial01() {
		return filial01;
	}

	public void setFilial01(Integer filial01) {
		this.filial01 = filial01;
	}

	public Integer getFilial02() {
		return filial02;
	}

	public void setFilial02(Integer filial02) {
		this.filial02 = filial02;
	}

	public Integer getFilial03() {
		return filial03;
	}

	public void setFilial03(Integer filial03) {
		this.filial03 = filial03;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
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
		IndicadoresCliente other = (IndicadoresCliente) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "IndicadoresCliente [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", filial01=" + filial01
				+ ", filial02=" + filial02 + ", filial03=" + filial03 + ", grupo=" + grupo + "]";
	}	
}
