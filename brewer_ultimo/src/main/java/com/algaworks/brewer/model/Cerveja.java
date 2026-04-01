package com.algaworks.brewer.model;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.util.StringUtils;

import com.algaworks.brewer.repository.listener.CervejaEntityListener;
import com.algaworks.brewer.validation.Sku;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@EntityListeners(CervejaEntityListener.class)
@Entity
@Table(name="cerveja")
public class Cerveja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_cerveja")
	@SequenceGenerator(name =  "sequence_cerveja", sequenceName = "sequence_cerveja", initialValue = 1, allocationSize = 1)
	private Long codigo;
	
	@Sku
	@NotBlank(message = "SKU é obrigatório")
	private String sku;
	
	@NotBlank(message = "NOME é obrigatório")
	private String nome;
	
	@NotBlank(message="A descrição é obrigatória")
	@Size(min = 1, max = 50, message = "O tamanho da DESCRIÇÃO deve estar entre 1 e 50")
	private String descricao;
	
	@NotNull(message="Valor é obrigatótio")
	@DecimalMin("0.01")
	@DecimalMax(value="9999999.99", message="O valor da cerveja deve ser menor que R$9.999.999,99")
	private BigDecimal valor;
	
	@NotNull(message="Teor alcoolíco é obrigatótio")
	@DecimalMax(value="100.0", message="O valor do teor alcoolico deve ser menor que 100")
	@Column(name="teor_alcoolico")
	private BigDecimal teorAlcoolico;
	
	@NotNull(message="A comissão é obrigatótio")
	@DecimalMax(value="100.0", message="A comissão deve ser igual ou menor que 100")
	private BigDecimal comissao;
	
	@NotNull(message="A quantidade em estoque é obrigatória")
	@Max(value = 9999, message="A quantidade em estoque deve ser menor que 9.999")
	@Column(name="quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@NotNull(message="A origem é obrigatótia")
	@Enumerated(EnumType.STRING)
	private Origem origem;
	
	@NotNull(message="O sabor é obrigatótio")
	@Enumerated(EnumType.STRING)
	private Sabor sabor;
	
	@NotNull(message="O estilo é obrigatótio")
	@ManyToOne
	@JoinColumn(name = "codigo_estilo")
	private Estilo estilo;
	
	private String foto;
	
	@Column(name="content_type")
	private String contentType;
	
	@Transient
	private boolean novaFoto;
	
	@Transient
	private String urlFoto;
	
	@Transient
	private String urlThumbnailFoto;
	
	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		sku = sku.toUpperCase();
	}
	
	public String getSku() {
		return sku;
	}
	
	public void setSku(String sku) {
		this.sku = sku;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}

	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@SuppressWarnings("deprecation")
	public String getFotoOuMock() {
        return !StringUtils.isEmpty(foto) ? foto : "sem-foto.png";
    }
	
	@SuppressWarnings("deprecation")
	public boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
	}
	
	 public boolean isNovo() {
		 return this.codigo == null;
	 }
	 
	public boolean isNovaFoto() {
		return novaFoto;
	}

	 public void setNovaFoto(boolean novaFoto) {
		 this.novaFoto = novaFoto;
	 }

	public String getUrlFoto() {
		return urlFoto;
	}

	 public void setUrlFoto(String urlFoto) {
		 this.urlFoto = urlFoto;
	 }

	public String getUrlThumbnailFoto() {
		return urlThumbnailFoto;
	}

	 public void setUrlThumbnailFoto(String urlThumbnailFoto) {
		 this.urlThumbnailFoto = urlThumbnailFoto;
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
		Cerveja other = (Cerveja) obj;
		return Objects.equals(codigo, other.codigo);
	}

	@Override
	public String toString() {
		return "Cerveja [codigo=" + codigo + ", sku=" + sku + ", nome=" + nome + ", descricao=" + descricao + ", valor="
				+ valor + ", teorAlcoolico=" + teorAlcoolico + ", comissao=" + comissao + ", quantidadeEstoque="
				+ quantidadeEstoque + ", origem=" + origem + ", sabor=" + sabor + ", estilo=" + estilo + ", foto="
				+ foto + ", contentType=" + contentType + ", novaFoto=" + novaFoto + "]";
	}

		
}
