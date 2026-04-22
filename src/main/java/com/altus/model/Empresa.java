package com.altus.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
    
    @Column(name = "razao_social")
    @NotBlank(message = "Razão Social é obrigatória.")
    private String razaoSocial;
    
    @Column(name = "nome_fantasia")
    @NotBlank(message = "Nome Fantasia é obrigatório.")
    private String nomeFantasia;
    
    @Column(name = "cnpj", length = 14)
    @NotBlank(message = "CNPJ é obrigatório.")
    private String cnpj;
    
    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_abertura")
    private LocalDate dataAbertura;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "regime_tributario")
    @NotNull(message = "Regime tributário é obrigatório.")
    private RegimeTributario regimeTributario;
    
    @NotNull(message="Tipo de empresa é obrigatótio")
	@Enumerated(EnumType.STRING)
    @Column(name = "tipo")
	private Tipo tipo;

    @JsonIgnore
    @Valid
    @Embedded
    private Endereco endereco;

    @Column(name = "email")
    @NotBlank(message = "Email é obrigatório.")
    private String email;
    
    @Column(name = "telefone")
    @NotBlank(message = "Telefone é obrigatório.")
    private String telefone;
    
    @Column(name = "celular")
    private String celular;
    
    @Column(name = "site")
    private String site;
    
    @Column(name = "ativo")
    private Boolean ativo = true;
    
    @Column(name = "data_cadastro", insertable = false, updatable = false)
    private LocalDateTime dataCadastro;  
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		// REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
		this.cnpj = cnpj != null ? cnpj.replaceAll("\\D", "") : null;
	}
	
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	
	public LocalDate getDataAbertura() {
		return dataAbertura;
	}
	
	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public RegimeTributario getRegimeTributario() {
		return regimeTributario;
	}

	public void setRegimeTributario(RegimeTributario regimeTributario) {
		this.regimeTributario = regimeTributario;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		// REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
        this.telefone = telefone != null ? telefone.replaceAll("\\D", "") : null;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		// REMOVE OS ELEMENTOS DA MASCARA PARA SALVAR APENAS O NÚMERO
        this.celular = celular != null ? celular.replaceAll("\\D", "") : null;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		return Objects.equals(id, other.id);
	} 
}