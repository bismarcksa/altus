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

@Entity
@Table(name = "tbl_producoes_pcp")
@NamedQueries({ 
	@NamedQuery(name = "ProducaoPCP.listar", query = "SELECT producaoPCP FROM ProducaoPCP producaoPCP ORDER BY producaoPCP.codigo DESC"),
	@NamedQuery(name = "ProducaoPCP.buscarPorCodigo", query = "SELECT producaoPCP FROM ProducaoPCP producaoPCP WHERE producaoPCP.funcionario.codigo = :funcionario AND producaoPCP.dav = :dav AND producaoPCP.identificador = :identificador"),
})
public class ProducaoPCP {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_producao_pcp")
	@SequenceGenerator(name =  "sequence_id_producao_pcp", sequenceName = "sequence_producao_pcp", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name="dav", nullable = false)
	private Integer dav;
	
	@Column(name="identificador", nullable = false)
	private String identificador;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fun_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;
	
	@Column(name="data_lancamento")
	private Date data_lancamento;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getDav() {
		return dav;
	}

	public void setDav(Integer dav) {
		this.dav = dav;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getData_lancamento() {
		return data_lancamento;
	}

	public void setData_lancamento(Date data_lancamento) {
		this.data_lancamento = data_lancamento;
	}

	@Override
	public String toString() {
		return "ProducaoPCP [codigo=" + codigo + ", dav=" + dav + ", identificador=" + identificador + ", status="
				+ status + ", funcionario=" + funcionario + ", data_lancamento=" + data_lancamento + "]";
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
		ProducaoPCP other = (ProducaoPCP) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
