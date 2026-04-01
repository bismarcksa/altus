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
@Table(name = "tbl_entrega")
@NamedQueries({ 
	@NamedQuery(name = "Entrega.listar", query = "SELECT entrega FROM Entrega entrega WHERE entrega.status != 'REALIZADA' ORDER BY entrega.codigo DESC"),
	@NamedQuery(name = "Entrega.listarEntregaExtra", query = "SELECT entrega FROM Entrega entrega WHERE entrega.entregador.vinculo != 'CELETISTA' and entrega.status = 'BLOQUEADO' and entrega.autorizacao_entrega = false ORDER BY entrega.codigo DESC"),
	@NamedQuery(name = "Entrega.listarEntregaFin", query = "SELECT entrega FROM Entrega entrega WHERE entrega.autorizacao_entrega = true ORDER BY entrega.codigo DESC"),
	@NamedQuery(name = "Entrega.buscarPorCodigo", query = "SELECT entrega FROM Entrega entrega WHERE entrega.codigo = :codigo")
})
public class Entrega {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_entrega")
	@SequenceGenerator(name =  "sequence_id_entrega", sequenceName = "sequence_entrega", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name="data_lancamento")
	private Date data_lancamento;
	
	@NotNull(message = "O campo DATA ENTREGA é obrigatório.")
	@Column(name="data_entrega")
	private Date data_entrega;
	
	@Column(name="data_autorizacao")
	private Date data_autorizacao;
	
	@Column(name="data_financeiro")
	private Date data_financeiro;
	
	@NotNull(message = "O campo DAV é obrigatório.")
	@Column(name="dav", nullable = false, unique = true)
	private Integer dav;
	
	@NotEmpty(message = "O campo IDENTIFICADOR é obrigatório.")
	@Column(name="identificador", length = 30, nullable = false)
	private String identificador;
	
	@NotNull(message = "O campo FUNCIONÁRIO é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_func_codigo", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario funcionario;
	
	@NotNull(message = "O campo USUÁRIO é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_func_codigo_usuario_lanc", referencedColumnName = "fun_codigo", nullable = false)
	private Funcionario usuario;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_codigo_entregador", referencedColumnName = "codigo")
	private Entregador entregador;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_codigo_cidade", referencedColumnName = "codigo", nullable = true)
	private Cidade cidade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_func_codigo_usuario_aut", referencedColumnName = "fun_codigo", nullable = true)
	private Funcionario autorizador;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_func_codigo_usuario_fin", referencedColumnName = "fun_codigo", nullable = true)
	private Funcionario financeiro;
	
	@NotEmpty(message = "O campo CLIENTE é obrigatório.")
	@Column(name="cliente", length = 100, nullable = false)
	private String cliente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_bairro_codigo", referencedColumnName = "codigo", nullable = true)
	private Bairro bairro;
	
	@NotNull(message = "O campo TAXA é obrigatório.")
	@Column(name="taxa", precision = 9, scale = 2)
	private Double taxa;
	
	@NotNull(message = "O campo VALOR RECEBER é obrigatório.")
	@Column(name="valor_receber", precision = 9, scale = 2)
	private Double valor_receber;
	
	//VALOR QUE ESTAVA NO CADASTRO DE ENTREGA E FOI ALTERADO
	@Column(name="valor_receber_alterado", precision = 9, scale = 2)
	private Double valor_receber_alterado;
	
	@NotNull(message = "O campo VALOR DAV é obrigatório.")
	@Column(name="valor_dav", precision = 9, scale = 2)
	private Double valor_dav;
	
	//VALOR QUE ESTAVA NO CADASTRO DE ENTREGA E FOI ALTERADO
	@Column(name="valor_dav_alterado", precision = 9, scale = 2)
	private Double valor_dav_alterado;
	
	@Column(name="status")
	private String status;
	
	@Column(name="observacao", length = 250)
	private String observacao;
	
	@Column(name="observacao_fin", length = 250)
	private String observacao_fin;
	
	@Column(name="autorizacao_entrega")
	private boolean autorizacao_entrega;
	
	@Transient
	private Long totalEntregaEntregador;
	
	@Transient
	private String nomeEntregador;
	
	@Transient
	private Long codigoEntregador;
	
	public Double getValor_receber_alterado() {
		return valor_receber_alterado;
	}

	public void setValor_receber_alterado(Double valor_receber_alterado) {
		this.valor_receber_alterado = valor_receber_alterado;
	}

	public Double getValor_dav_alterado() {
		return valor_dav_alterado;
	}

	public void setValor_dav_alterado(Double valor_dav_alterado) {
		this.valor_dav_alterado = valor_dav_alterado;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getData_lancamento() {
		return data_lancamento;
	}

	public void setData_lancamento(Date data_lancamento) {
		this.data_lancamento = data_lancamento;
	}

	public Date getData_entrega() {
		return data_entrega;
	}

	public void setData_entrega(Date data_entrega) {
		this.data_entrega = data_entrega;
	}

	public Date getData_autorizacao() {
		return data_autorizacao;
	}

	public void setData_autorizacao(Date data_autorizacao) {
		this.data_autorizacao = data_autorizacao;
	}

	public Date getData_financeiro() {
		return data_financeiro;
	}

	public void setData_financeiro(Date data_financeiro) {
		this.data_financeiro = data_financeiro;
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Funcionario getUsuario() {
		return usuario;
	}

	public void setUsuario(Funcionario usuario) {
		this.usuario = usuario;
	}

	public Entregador getEntregador() {
		return entregador;
	}

	public void setEntregador(Entregador entregador) {
		this.entregador = entregador;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Funcionario getAutorizador() {
		return autorizador;
	}

	public void setAutorizador(Funcionario autorizador) {
		this.autorizador = autorizador;
	}

	public Funcionario getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Funcionario financeiro) {
		this.financeiro = financeiro;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		//SEMPRE VAI GUARDA O TEXTO EM MAIUSCULO
		this.cliente = cliente != null ? cliente.toUpperCase() : null;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Double getTaxa() {
		return taxa;
	}

	public void setTaxa(Double taxa) {
		this.taxa = taxa;
	}

	public Double getValor_receber() {
		return valor_receber;
	}

	public void setValor_receber(Double valor_receber) {
		this.valor_receber = valor_receber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getObservacao_fin() {
		return observacao_fin;
	}

	public void setObservacao_fin(String observacao_fin) {
		//SEMPRE VAI GUARDA O TEXTO EM MAIUSCULO
		this.observacao_fin = observacao_fin != null ? observacao_fin.toUpperCase() : null;
	}

	public boolean isAutorizacao_entrega() {
		return autorizacao_entrega;
	}

	public void setAutorizacao_entrega(boolean autorizacao_entrega) {
		this.autorizacao_entrega = autorizacao_entrega;
	}

	public Double getValor_dav() {
		return valor_dav;
	}

	public void setValor_dav(Double valor_dav) {
		this.valor_dav = valor_dav;
	}

	public Long getTotalEntregaEntregador() {
		return totalEntregaEntregador;
	}

	public void setTotalEntregaEntregador(Long totalEntregaEntregador) {
		this.totalEntregaEntregador = totalEntregaEntregador;
	}

	public String getNomeEntregador() {
		return nomeEntregador;
	}

	public void setNomeEntregador(String nomeEntregador) {
		this.nomeEntregador = nomeEntregador;
	}

	public Long getCodigoEntregador() {
		return codigoEntregador;
	}

	public void setCodigoEntregador(Long codigoEntregador) {
		this.codigoEntregador = codigoEntregador;
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
		Entrega other = (Entrega) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
