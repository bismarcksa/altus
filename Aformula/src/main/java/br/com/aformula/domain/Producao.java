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
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_producoes")
@NamedQueries({ 
	@NamedQuery(name = "Producao.listar", query = "SELECT producao FROM Producao producao ORDER BY producao.codigo DESC"),
	@NamedQuery(name = "Producao.listarConferencia", query = "SELECT producao FROM Producao producao WHERE producao.status != 'DISPENSADO' ORDER BY producao.codigo DESC"),
	@NamedQuery(name = "Producao.listarProducaoUsuario", query = "SELECT producao FROM Producao producao WHERE producao.funcionario.codigo = :codigo AND producao.status = 'PENDENTE' ORDER BY producao.codigo DESC"),
	@NamedQuery(name = "Producao.buscarPorCodigo", query = "SELECT producao FROM Producao producao WHERE producao.codigo = :codigo"),
	@NamedQuery(name = "Producao.listarPendenteProducao", query = "SELECT producao FROM Producao producao WHERE producao.status != 'DISPENSADO' ORDER BY producao.codigo DESC")
})
public class Producao {
	 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_producao")
	@SequenceGenerator(name =  "sequence_id_producao", sequenceName = "sequence_producao", initialValue = 1, allocationSize = 1)
	@Column(name = "pro_codigo")
	private Long codigo;
	
	@NotNull(message = "O campo FILIAL é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pro_filial", referencedColumnName = "codigo", nullable = false)
	private Filial filial;
	
	@NotNull(message = "O campo DAV é obrigatório.")
	@Column(name="pro_dav", nullable = false, unique = true)
	private Integer dav;

	@Column(name="pro_data_solicitacao")
	private Date data_solicitacao;
	
	@NotNull(message = "O campo DATA DISPENSAÇÃO é obrigatório.")
	@Column(name="pro_data_dispensacao")
	private Date data_dispensacao;
	
	@Column(name="pro_data_dispensacao2")
	private Date data_dispensacao2;
	
	@NotNull(message = "O campo QUANTIDADE é obrigatório.")
	@Column(name="pro_quantidade", nullable = false)
	private Integer quantidade;
	
	@Column(name="pro_capsula_dobrada")
	private boolean capsula_dobrada;
	
	@Column(name="pro_capsula_triplicada")
	private boolean capsula_triplicada;
	
	@Column(name="pro_capsula_quadruplicada")
	private boolean capsula_quadruplicada;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_funcionarios_fun_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;
	
	@Column(name="pro_status")
	private String status;
	
	@NotEmpty(message = "O campo TIPO é obrigatório.")
	@Column(name="pro_tipo")
	private String tipo;
	
	@NotNull(message = "O campo IDENTIFICADOR é obrigatório.")
	@Column(name="pro_identificador", nullable = false)
	private String identificador;
	
	@Transient
	private Long totalProducaoDia;
	
	@Transient
	private Integer totalQuantidade;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Integer getDav() {
		return dav;
	}

	public void setDav(Integer dav) {
		this.dav = dav;
	}

	public Date getData_solicitacao() {
		return data_solicitacao;
	}

	public void setData_solicitacao(Date data_solicitacao) {
		this.data_solicitacao = data_solicitacao;
	}

	public Date getData_dispensacao() {
		return data_dispensacao;
	}

	public void setData_dispensacao(Date data_dispensacao) {
		this.data_dispensacao = data_dispensacao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getTotalProducaoDia() {
		return totalProducaoDia;
	}

	public void setTotalProducaoDia(Long totalProducaoDia) {
		this.totalProducaoDia = totalProducaoDia;
	}

	public Date getData_dispensacao2() {
		return data_dispensacao2;
	}

	public void setData_dispensacao2(Date data_dispensacao2) {
		this.data_dispensacao2 = data_dispensacao2;
	}

	public Integer getTotalQuantidade() { 
		return totalQuantidade;
	}

	public void setTotalQuantidade(Integer totalQuantidade) {
		this.totalQuantidade = totalQuantidade;
	}

	public boolean isCapsula_dobrada() {
		return capsula_dobrada;
	}

	public void setCapsula_dobrada(boolean capsula_dobrada) {
		this.capsula_dobrada = capsula_dobrada;
	}

	public boolean isCapsula_triplicada() {
		return capsula_triplicada;
	}

	public void setCapsula_triplicada(boolean capsula_triplicada) {
		this.capsula_triplicada = capsula_triplicada;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public boolean isCapsula_quadruplicada() {
		return capsula_quadruplicada;
	}

	public void setCapsula_quadruplicada(boolean capsula_quadruplicada) {
		this.capsula_quadruplicada = capsula_quadruplicada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public String toString() {
		return "Producao [codigo=" + codigo + ", filial=" + filial + ", dav=" + dav + ", data_solicitacao="
				+ data_solicitacao + ", data_dispensacao=" + data_dispensacao + ", data_dispensacao2="
				+ data_dispensacao2 + ", quantidade=" + quantidade + ", capsula_dobrada=" + capsula_dobrada
				+ ", capsula_triplicada=" + capsula_triplicada + ", capsula_quadruplicada=" + capsula_quadruplicada
				+ ", funcionario=" + funcionario + ", status=" + status + ", tipo=" + tipo + ", identificador="
				+ identificador + ", totalProducaoDia=" + totalProducaoDia + ", totalQuantidade=" + totalQuantidade
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producao other = (Producao) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
