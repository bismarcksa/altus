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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tbl_analise_representante")
@NamedQueries({ 
	@NamedQuery(name = "AnaliseRepresentante.listar", query = "SELECT analiseRepresentante FROM AnaliseRepresentante analiseRepresentante ORDER BY analiseRepresentante.codigo ASC"),
	@NamedQuery(name = "AnaliseRepresentante.buscarPorCodigo", query = "SELECT analiseRepresentante FROM AnaliseRepresentante analiseRepresentante WHERE analiseRepresentante.codigo = :codigo")
})
public class AnaliseRepresentante {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_analise_representante")
	@SequenceGenerator(name =  "sequence_id_analise_representante", sequenceName = "sequence_analise_representante", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Integer codigo;
	
	@NotNull(message = "O campo ANO é obrigatório.")
	@Column(name="ano", nullable = false)
	private Integer ano;
	
	@Column(name="janeiro", precision = 9, scale = 2)
	private BigDecimal janeiro;
	
	@Column(name="fevereiro", precision = 9, scale = 2)
	private BigDecimal fevereiro;
	
	@Column(name="marco", precision = 9, scale = 2)
	private BigDecimal marco;
	
	@Column(name="abril", precision = 9, scale = 2)
	private BigDecimal abril;
	
	@Column(name="maio", precision = 9, scale = 2)
	private BigDecimal maio;
	
	@Column(name="junho", precision = 9, scale = 2)
	private BigDecimal junho;
	
	@Column(name="julho", precision = 9, scale = 2)
	private BigDecimal julho;
	
	@Column(name="agosto", precision = 9, scale = 2)
	private BigDecimal agosto;
	
	@Column(name="setembro", precision = 9, scale = 2)
	private BigDecimal setembro;
	
	@Column(name="outubro", precision = 9, scale = 2)
	private BigDecimal outubro;
	
	@Column(name="novembro", precision = 9, scale = 2)
	private BigDecimal novembro;
	
	@Column(name="dezembro", precision = 9, scale = 2)
	private BigDecimal dezembro;
	
	@Transient
	private BigDecimal valorJaneiro;
	
	@Transient
	private BigDecimal valorFevereiro;
	
	@Transient
	private BigDecimal valorMarco;
	
	@Transient
	private BigDecimal valorAbril;
	
	@Transient
	private BigDecimal valorMaio;
	
	@Transient
	private BigDecimal valorJunho;
	
	@Transient
	private BigDecimal valorJulho;
	
	@Transient
	private BigDecimal valorAgosto;
	
	@Transient
	private BigDecimal valorSetembro;
	
	@Transient
	private BigDecimal valorOutubro;
	
	@Transient
	private BigDecimal valorNovembro;
	
	@Transient
	private BigDecimal valorDezembro;
	
	@Transient
	private BigDecimal totalAnual;
	
	@Transient
	private BigDecimal valorTotalAnual;
	
	
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

	public BigDecimal getJaneiro() {
		return janeiro;
	}

	public void setJaneiro(BigDecimal janeiro) {
		this.janeiro = janeiro;
	}

	public BigDecimal getFevereiro() {
		return fevereiro;
	}

	public void setFevereiro(BigDecimal fevereiro) {
		this.fevereiro = fevereiro;
	}

	public BigDecimal getMarco() {
		return marco;
	}

	public void setMarco(BigDecimal marco) {
		this.marco = marco;
	}

	public BigDecimal getAbril() {
		return abril;
	}

	public void setAbril(BigDecimal abril) {
		this.abril = abril;
	}

	public BigDecimal getMaio() {
		return maio;
	}

	public void setMaio(BigDecimal maio) {
		this.maio = maio;
	}

	public BigDecimal getJunho() {
		return junho;
	}

	public void setJunho(BigDecimal junho) {
		this.junho = junho;
	}

	public BigDecimal getJulho() {
		return julho;
	}

	public void setJulho(BigDecimal julho) {
		this.julho = julho;
	}

	public BigDecimal getAgosto() {
		return agosto;
	}

	public void setAgosto(BigDecimal agosto) {
		this.agosto = agosto;
	}

	public BigDecimal getSetembro() {
		return setembro;
	}

	public void setSetembro(BigDecimal setembro) {
		this.setembro = setembro;
	}

	public BigDecimal getOutubro() {
		return outubro;
	}

	public void setOutubro(BigDecimal outubro) {
		this.outubro = outubro;
	}

	public BigDecimal getNovembro() {
		return novembro;
	}

	public void setNovembro(BigDecimal novembro) {
		this.novembro = novembro;
	}

	public BigDecimal getDezembro() {
		return dezembro;
	}

	public void setDezembro(BigDecimal dezembro) {
		this.dezembro = dezembro;
	}
	
	public BigDecimal getValorJaneiro() {
		return valorJaneiro;
	}

	public void setValorJaneiro(BigDecimal valorJaneiro) {
		this.valorJaneiro = valorJaneiro;
	}

	public BigDecimal getValorFevereiro() {
		return valorFevereiro;
	}

	public void setValorFevereiro(BigDecimal valorFevereiro) {
		this.valorFevereiro = valorFevereiro;
	}

	public BigDecimal getValorMarco() {
		return valorMarco;
	}

	public void setValorMarco(BigDecimal valorMarco) {
		this.valorMarco = valorMarco;
	}

	public BigDecimal getValorAbril() {
		return valorAbril;
	}

	public void setValorAbril(BigDecimal valorAbril) {
		this.valorAbril = valorAbril;
	}

	public BigDecimal getValorMaio() {
		return valorMaio;
	}

	public void setValorMaio(BigDecimal valorMaio) {
		this.valorMaio = valorMaio;
	}

	public BigDecimal getValorJunho() {
		return valorJunho;
	}

	public void setValorJunho(BigDecimal valorJunho) {
		this.valorJunho = valorJunho;
	}

	public BigDecimal getValorJulho() {
		return valorJulho;
	}

	public void setValorJulho(BigDecimal valorJulho) {
		this.valorJulho = valorJulho;
	}

	public BigDecimal getValorAgosto() {
		return valorAgosto;
	}

	public void setValorAgosto(BigDecimal valorAgosto) {
		this.valorAgosto = valorAgosto;
	}

	public BigDecimal getValorSetembro() {
		return valorSetembro;
	}

	public void setValorSetembro(BigDecimal valorSetembro) {
		this.valorSetembro = valorSetembro;
	}

	public BigDecimal getValorOutubro() {
		return valorOutubro;
	}

	public void setValorOutubro(BigDecimal valorOutubro) {
		this.valorOutubro = valorOutubro;
	}

	public BigDecimal getValorNovembro() {
		return valorNovembro;
	}

	public void setValorNovembro(BigDecimal valorNovembro) {
		this.valorNovembro = valorNovembro;
	}

	public BigDecimal getValorDezembro() {
		return valorDezembro;
	}

	public void setValorDezembro(BigDecimal valorDezembro) {
		this.valorDezembro = valorDezembro;
	}

	public BigDecimal getTotalAnual() {
		return totalAnual;
	}

	public void setTotalAnual(BigDecimal totalAnual) {
		this.totalAnual = totalAnual;
	}

	public BigDecimal getValorTotalAnual() {
		return valorTotalAnual;
	}

	public void setValorTotalAnual(BigDecimal valorTotalAnual) {
		this.valorTotalAnual = valorTotalAnual;
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
		AnaliseRepresentante other = (AnaliseRepresentante) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "AnaliseRepresentante [codigo=" + codigo + ", ano=" + ano + ", janeiro=" + janeiro + ", fevereiro=" + fevereiro
				+ ", marco=" + marco + ", abril=" + abril + ", maio=" + maio + ", junho=" + junho + ", julho=" + julho
				+ ", agosto=" + agosto + ", setembro=" + setembro + ", outubro=" + outubro + ", novembro=" + novembro
				+ ", dezembro=" + dezembro + "]";
	}

}
