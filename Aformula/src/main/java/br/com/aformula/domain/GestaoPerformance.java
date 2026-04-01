package br.com.aformula.domain;

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
@Table(name = "tbl_gestao_performance")
@NamedQueries({ 
	@NamedQuery(name = "GestaoPerformance.listar", query = "SELECT gestaoPerformance FROM GestaoPerformance gestaoPerformance ORDER BY gestaoPerformance.codigo"),
	@NamedQuery(name = "GestaoPerformance.buscarPorCodigo", query = "SELECT gestaoPerformance FROM GestaoPerformance gestaoPerformance WHERE gestaoPerformance.codigo = :codigo"),
	@NamedQuery(name = "GestaoPerformance.performanceExiste", query = "SELECT gestaoPerformance FROM GestaoPerformance gestaoPerformance WHERE gestaoPerformance.funcionario.codigo = :funcionario AND gestaoPerformance.ano = :ano")
})
public class GestaoPerformance {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_gestao_performance")
	@SequenceGenerator(name =  "sequence_id_gestao_performance", sequenceName = "sequence_gestao_performance", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name="ano")
	private Integer ano;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_funcionarios_fun_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;
	
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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
		GestaoPerformance other = (GestaoPerformance) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "GestaoPerformance [codigo=" + codigo + ", ano=" + ano + ", funcionario=" + funcionario + "]";
	}


}
