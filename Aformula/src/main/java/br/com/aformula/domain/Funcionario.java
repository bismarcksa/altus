package br.com.aformula.domain;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "tbl_funcionarios")
@NamedQueries({ 
	@NamedQuery(name = "Funcionario.listar", query = "SELECT funcionario FROM Funcionario funcionario ORDER BY funcionario.status DESC, funcionario.nome ASC"),
	@NamedQuery(name = "Funcionario.listarTodosAtivo", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.status = true ORDER BY funcionario.nome"),
	@NamedQuery(name = "Funcionario.listarTodosAtivoNaoLideranca", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.status = true AND funcionario.lideranca = false ORDER BY funcionario.nome"),
	@NamedQuery(name = "Funcionario.listarAtivo", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.status = true AND (funcionario.funcao = 'ATENDENTE' OR funcionario.funcao = 'TÉCNICO DE LABORATÓRIO') ORDER BY funcionario.nome"),
	@NamedQuery(name = "Funcionario.listarEntregadores", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.status = true AND funcionario.funcao = 'ENTREGADOR' ORDER BY funcionario.nome"),
	@NamedQuery(name = "Funcionario.listarAtendentes", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.status = true AND (funcionario.funcao = 'ATENDENTE' OR funcionario.funcao = 'CAIXA' OR funcionario.funcao = 'FARMACÊUTICO' OR funcionario.funcao = 'COORDENADOR DE LABORATÓRIO') ORDER BY funcionario.nome"),
	@NamedQuery(name = "Funcionario.listarFuncFilial", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.filial.codigo = :codigoFilial ORDER BY funcionario.codigo DESC"),
	@NamedQuery(name = "Funcionario.listarFuncAtivoFilial", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.status = true AND funcionario.filial.codigo = :codigoFilial AND (funcionario.funcao = 'ATENDENTE' OR funcionario.funcao = 'TÉCNICO DE LABORATÓRIO')"),
	@NamedQuery(name = "Funcionario.buscarPorCodigo", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.codigo = :codigo"),
	@NamedQuery(name = "Funcionario.autenticar", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.login = :login AND funcionario.senha = :senha"),
	@NamedQuery(name = "Funcionario.buscarPorCPFNascimento", query = "SELECT funcionario FROM Funcionario funcionario WHERE funcionario.cpf = :cpf AND funcionario.data_nascimento = :data_nacimento")
})
public class Funcionario implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_funcionario")
	@SequenceGenerator(name =  "sequence_id_funcionario", sequenceName = "sequence_funcionario", initialValue = 1, allocationSize = 1)
	@Column(name = "fun_codigo")
	private Long codigo;

	@NotEmpty(message = "O campo Nome é obrigatório.")
	@Size(min = 3, max= 100, message = "Tamanho inválido para o nome.")
	@Column(name = "fun_nome", length = 50, nullable = false)
	private String nome;

	@CPF(message = "O CPF informado é inválido")
	@Column(name = "fun_cpf", length = 14, nullable = false, unique = true)
	private String cpf;

	@Column(name="fun_data_nascimento")
	private Date data_nascimento;
	
	@NotEmpty(message = "O campo Senha é obrigatório.")
	@Column(name = "fun_senha", length = 32)
	private String senha;
	
	@NotEmpty(message = "O campo LOGIN é obrigatório.")
	@Size(min = 3, max= 20, message = "Tamanho inválido para a login.")
	@Column(name = "login", length = 20)
	private String login;

	@NotEmpty(message = "O campo Função é obrigatório.")
	@Column(name = "fun_funcao", length = 50, nullable = false)
	private String funcao;
	
	@Column(name="status")
	private boolean status;
	
	//NÃO GERA FOLHA DE PONTO EM CASO DE LIDERANÇA true
	@Column(name="lideranca")
	private boolean lideranca;
	
	@NotNull(message = "O campo FILIAL é obrigatório.")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_filial", referencedColumnName = "codigo", nullable = false)
	private Filial filial;
	
	@NotEmpty(message = "O campo VÍNCULO é obrigatório.")
	@Column(name = "vinculo")
	private String vinculo;
	
	@Column(name = "fun_foto", length = 255) // ou um tamanho maior, se necessário
	private String foto;

	@Transient
	private Long quantidadeProducao;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = (nome != null) ? nome.toUpperCase() : null;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = (login != null) ? login.toUpperCase() : null;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Long getQuantidadeProducao() {
		return quantidadeProducao;
	}

	public void setQuantidadeProducao(Long quantidadeProducao) {
		this.quantidadeProducao = quantidadeProducao;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public boolean isLideranca() {
		return lideranca;
	}

	public void setLideranca(boolean lideranca) {
		this.lideranca = lideranca;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public String getFoto() {
	    return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Funcionario [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", data_nascimento="
				+ data_nascimento + ", senha=" + senha + ", login=" + login + ", funcao=" + funcao + ", status="
				+ status + ", filial=" + filial + ", vinculo=" + vinculo + ", foto=" + foto + ", quantidadeProducao="
				+ quantidadeProducao + "]";
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
		Funcionario other = (Funcionario) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
