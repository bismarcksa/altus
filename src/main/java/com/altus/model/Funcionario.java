package com.altus.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")    
    private Long codigo;

    @NotBlank(message = "Nome é obrigatório.")
    @Size(max = 80)
    @Column(name = "nome")
    private String nome;
    
    @Size(max = 80)
    @Column(name = "nome_social")
    private String nomeSocial;

    @NotBlank(message = "Telefone é obrigatório.")
    @Column(name = "telefone")
    private String telefone;

    @Email(message = "E-mail inválido")
    @Column(name = "email")
    private String email;
    
    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "Data de nascimento deve ser no passado.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @NotNull(message="Sexo é obrigatótio")
	@Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sexo sexo;
    
    @NotNull(message="Estado Civil é obrigatório.")
	@Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivil estadoCivil;
    
    @NotBlank(message = "Nome da mãe é obrigatório.")
    @Column(name = "nome_mae")
    private String nomeMae;

    @Column(name = "nome_pai")
    private String nomePai;
    
    @Column(name = "telefone_contato_emergencia")
    private String telefoneContatoEmergencia;
    
    @Column(name = "nome_contato_emergencia")
    @Size(max = 50)
    private String nomeContatoEmergencia;
    
    @NotNull(message = "Data de admissão é obrigatória.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_admissao")
    private LocalDate dataAdmissao;

    @JsonIgnore
    @Valid
    @Embedded
    private Endereco endereco;
    
    @NotBlank(message = "CPF é obrigatório.")
    @CPF(message = "CPF inválido")
    @Column(name = "cpf", length = 11)
    private String cpf;
    
    @NotBlank(message = "RG é obrigatório.")
    private String rg;
    
    //PIS, PASEP, NIT, NIS 
    @Column(name = "pis", length = 11)
    private String pis;
    
    @Column(name = "titulo_eleitor", length = 13)
    private String tituloEleitor;
    
    @Column(name = "carteira_trabalho")
    private String carteiraTrabalho;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "naturalidade")
    private String naturalidade;
    
    @NotBlank(message = "Cargo é obrigatório.")
    private String cargo;

    @NotBlank(message = "Departamento é obrigatório.")
    private String departamento;

    @Column(name = "salario")
    private BigDecimal salario;
    
    @Column(name = "data_demissao")
    private LocalDate dataDemissao;
    
    @NotBlank(message = "Tipo de contrato é obrigatório.")
    @Column(name = "tipo_contrato")
    private String tipoContrato; // CLT, PJ, Estágio

    @Column(name = "jornada_trabalho")
    private String jornadaTrabalho;
    
    @Column(name = "situacao")
    private String situacao; // ATIVO, AFASTADO, DEMITIDO, FERIAS
    
    @Column(name = "observacoes")
    private String observacoes;
    
    @Column(name = "data_cadastro", insertable = false, updatable = false)
    private LocalDateTime dataCadastro;
      
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
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
    		// REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
     	this.cpf = cpf != null ? cpf.replaceAll("\\D", "") : null;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
     // REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
        this.telefone = telefone != null ? telefone.replaceAll("\\D", "") : null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getTelefoneContatoEmergencia() {
		return telefoneContatoEmergencia;
	}

	public void setTelefoneContatoEmergencia(String telefoneContatoEmergencia) {
		// REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
     	this.telefoneContatoEmergencia = telefoneContatoEmergencia != null ? telefoneContatoEmergencia.replaceAll("\\D", "") : null;
	}

	public String getNomeContatoEmergencia() {
		return nomeContatoEmergencia;
	}

	public void setNomeContatoEmergencia(String nomeContatoEmergencia) {
		this.nomeContatoEmergencia = nomeContatoEmergencia;
	}

	public LocalDate getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(LocalDate dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getTituloEleitor() {
		return tituloEleitor;
	}

	public void setTituloEleitor(String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
	}

	public String getCarteiraTrabalho() {
		return carteiraTrabalho;
	}

	public void setCarteiraTrabalho(String carteiraTrabalho) {
		this.carteiraTrabalho = carteiraTrabalho;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public LocalDate getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(LocalDate dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getJornadaTrabalho() {
		return jornadaTrabalho;
	}

	public void setJornadaTrabalho(String jornadaTrabalho) {
		this.jornadaTrabalho = jornadaTrabalho;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario funcionario = (Funcionario) o;
        return Objects.equals(codigo, funcionario.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
