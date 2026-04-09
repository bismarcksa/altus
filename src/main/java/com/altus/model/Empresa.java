package com.altus.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long codigo;
    
    @Column(name = "razaoSocial")
    private String razaoSocial;
    
    @Column(name = "nomeFantasia")
    private String nomeFantasia;
    
    @Column(name = "cnpj", length = 14)
    private String cnpj;
    
    @Column(name = "inscricaoEstadual")
    private String inscricaoEstadual;
    
    @Column(name = "inscricaoMunicipal")
    private String inscricaoMunicipal;
    
    @Column(name = "dataAbertura")
    private LocalDateTime dataAbertura;
    
    @Column(name = "regimeTributario")
    private String regimeTributario;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @Column(name = "email")
    private String email;
    
    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "celular")
    private String celular;
    
    @Column(name = "site")
    private String site;

    @Column(name = "limiteFuncionarios")
    private Integer limiteFuncionarios;
    
    @Column(name = "ativo")
    private Boolean ativo;
    
    @Column(name = "dataCadastro")
    private LocalDateTime dataCadastro;
    
    public Long getCodigo() {
		return codigo;
	}
	
    public void setCodigo(Long codigo) {
		this.codigo = codigo;
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
		this.cnpj = cnpj;
	}
	
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}
	
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
	
	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}
	
	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}
	
	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}
	
	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}
	
	public String getRegimeTributario() {
		return regimeTributario;
	}
	
	public void setRegimeTributario(String regimeTributario) {
		this.regimeTributario = regimeTributario;
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
		this.telefone = telefone;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public Integer getLimiteFuncionarios() {
		return limiteFuncionarios;
	}
	
	public void setLimiteFuncionarios(Integer limiteFuncionarios) {
		this.limiteFuncionarios = limiteFuncionarios;
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
}