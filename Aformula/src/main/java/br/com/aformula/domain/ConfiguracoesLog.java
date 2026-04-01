package br.com.aformula.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_log_configuracoes")
public class ConfiguracoesLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_log_configuracoes")
	@SequenceGenerator(name =  "sequence_id_log_configuracoes", sequenceName = "sequence_log_configuracoes", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@Column(name = "usuario", nullable = false)
	private Long usuario;
	
	@Column(name = "data_alteracao", nullable = false)
	private Date data_alteracao;
	
	@Column(name = "maquina", nullable = false)
	private String maquina;
	
	@Column(name = "ip", nullable = false)
	private String ip;

	@Column(name = "limite_diario_sache", nullable = false)
	private Long limiteDiarioSache;
	
	@Column(name = "limite_diario_capsula", nullable = false)
	private Long limiteDiarioCapsula;
	
	@Column(name = "limite_diario_shake", nullable = false)
	private Long limiteDiarioShake;
	
	@Column(name = "limite_diario_envase", nullable = false)
	private Long limiteDiarioEnvase;
	
	@Column(name = "bloquear_lancamento_producao")
	private Date bloquearLancamentoProducao;
	
	@Column(name = "bloquear_lancamento_producao2")
	private Date bloquearLancamentoProducao2;
	
	@Column(name = "bloquear_lancamento_producao3")
	private Date bloquearLancamentoProducao3;
	
	@Column(name = "bloquear_lancamento_producao_sache")
	private Boolean bloquearLancamentoProducaoItemSache;
	
	@Column(name = "bloquear_lancamento_producao_capsula")
	private Boolean bloquearLancamentoProducaoItemCapsula;
	
	@Column(name = "bloquear_lancamento_producao_shake")
	private Boolean bloquearLancamentoProducaoItemShake;
	
	@Column(name = "bloquear_lancamento_producao_envase")
	private Boolean bloquearLancamentoProducaoItemEnvase;
	
	@Column(name = "bloquear_lancamento_producao2_sache")
	private Boolean bloquearLancamentoProducao2ItemSache;
	
	@Column(name = "bloquear_lancamento_producao2_capsula")
	private Boolean bloquearLancamentoProducao2ItemCapsula;
	
	@Column(name = "bloquear_lancamento_producao2_shake")
	private Boolean bloquearLancamentoProducao2ItemShake;
	
	@Column(name = "bloquear_lancamento_producao2_envase")
	private Boolean bloquearLancamentoProducao2ItemEnvase;
	
	@Column(name = "bloquear_lancamento_producao3_sache")
	private Boolean bloquearLancamentoProducao3ItemSache;
	
	@Column(name = "bloquear_lancamento_producao3_capsula")
	private Boolean bloquearLancamentoProducao3ItemCapsula;
	
	@Column(name = "bloquear_lancamento_producao3_shake")
	private Boolean bloquearLancamentoProducao3ItemShake;
	
	@Column(name = "bloquear_lancamento_producao3_envase")
	private Boolean bloquearLancamentoProducao3ItemEnvase;
	
	@Column(name = "ano_base", nullable = false)
	private Long anoBase;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getLimiteDiarioSache() {
		return limiteDiarioSache;
	}

	public void setLimiteDiarioSache(Long limiteDiarioSache) {
		this.limiteDiarioSache = limiteDiarioSache;
	}

	public Long getLimiteDiarioCapsula() {
		return limiteDiarioCapsula;
	}

	public void setLimiteDiarioCapsula(Long limiteDiarioCapsula) {
		this.limiteDiarioCapsula = limiteDiarioCapsula;
	}

	public Long getLimiteDiarioShake() {
		return limiteDiarioShake;
	}

	public void setLimiteDiarioShake(Long limiteDiarioShake) {
		this.limiteDiarioShake = limiteDiarioShake;
	}

	public Long getLimiteDiarioEnvase() {
		return limiteDiarioEnvase;
	}

	public void setLimiteDiarioEnvase(Long limiteDiarioEnvase) {
		this.limiteDiarioEnvase = limiteDiarioEnvase;
	}

	public Date getBloquearLancamentoProducao() {
		return bloquearLancamentoProducao;
	}

	public void setBloquearLancamentoProducao(Date bloquearLancamentoProducao) {
		this.bloquearLancamentoProducao = bloquearLancamentoProducao;
	}

	public Date getBloquearLancamentoProducao2() {
		return bloquearLancamentoProducao2;
	}

	public void setBloquearLancamentoProducao2(Date bloquearLancamentoProducao2) {
		this.bloquearLancamentoProducao2 = bloquearLancamentoProducao2;
	}

	public Date getBloquearLancamentoProducao3() {
		return bloquearLancamentoProducao3;
	}

	public void setBloquearLancamentoProducao3(Date bloquearLancamentoProducao3) {
		this.bloquearLancamentoProducao3 = bloquearLancamentoProducao3;
	}

	public Boolean getBloquearLancamentoProducaoItemSache() {
		return bloquearLancamentoProducaoItemSache;
	}

	public void setBloquearLancamentoProducaoItemSache(Boolean bloquearLancamentoProducaoItemSache) {
		this.bloquearLancamentoProducaoItemSache = bloquearLancamentoProducaoItemSache;
	}

	public Boolean getBloquearLancamentoProducaoItemCapsula() {
		return bloquearLancamentoProducaoItemCapsula;
	}

	public void setBloquearLancamentoProducaoItemCapsula(Boolean bloquearLancamentoProducaoItemCapsula) {
		this.bloquearLancamentoProducaoItemCapsula = bloquearLancamentoProducaoItemCapsula;
	}

	public Boolean getBloquearLancamentoProducaoItemShake() {
		return bloquearLancamentoProducaoItemShake;
	}

	public void setBloquearLancamentoProducaoItemShake(Boolean bloquearLancamentoProducaoItemShake) {
		this.bloquearLancamentoProducaoItemShake = bloquearLancamentoProducaoItemShake;
	}

	public Boolean getBloquearLancamentoProducaoItemEnvase() {
		return bloquearLancamentoProducaoItemEnvase;
	}

	public void setBloquearLancamentoProducaoItemEnvase(Boolean bloquearLancamentoProducaoItemEnvase) {
		this.bloquearLancamentoProducaoItemEnvase = bloquearLancamentoProducaoItemEnvase;
	}

	public Boolean getBloquearLancamentoProducao2ItemSache() {
		return bloquearLancamentoProducao2ItemSache;
	}

	public void setBloquearLancamentoProducao2ItemSache(Boolean bloquearLancamentoProducao2ItemSache) {
		this.bloquearLancamentoProducao2ItemSache = bloquearLancamentoProducao2ItemSache;
	}

	public Boolean getBloquearLancamentoProducao2ItemCapsula() {
		return bloquearLancamentoProducao2ItemCapsula;
	}

	public void setBloquearLancamentoProducao2ItemCapsula(Boolean bloquearLancamentoProducao2ItemCapsula) {
		this.bloquearLancamentoProducao2ItemCapsula = bloquearLancamentoProducao2ItemCapsula;
	}

	public Boolean getBloquearLancamentoProducao2ItemShake() {
		return bloquearLancamentoProducao2ItemShake;
	}

	public void setBloquearLancamentoProducao2ItemShake(Boolean bloquearLancamentoProducao2ItemShake) {
		this.bloquearLancamentoProducao2ItemShake = bloquearLancamentoProducao2ItemShake;
	}

	public Boolean getBloquearLancamentoProducao2ItemEnvase() {
		return bloquearLancamentoProducao2ItemEnvase;
	}

	public void setBloquearLancamentoProducao2ItemEnvase(Boolean bloquearLancamentoProducao2ItemEnvase) {
		this.bloquearLancamentoProducao2ItemEnvase = bloquearLancamentoProducao2ItemEnvase;
	}

	public Boolean getBloquearLancamentoProducao3ItemSache() {
		return bloquearLancamentoProducao3ItemSache;
	}

	public void setBloquearLancamentoProducao3ItemSache(Boolean bloquearLancamentoProducao3ItemSache) {
		this.bloquearLancamentoProducao3ItemSache = bloquearLancamentoProducao3ItemSache;
	}

	public Boolean getBloquearLancamentoProducao3ItemCapsula() {
		return bloquearLancamentoProducao3ItemCapsula;
	}

	public void setBloquearLancamentoProducao3ItemCapsula(Boolean bloquearLancamentoProducao3ItemCapsula) {
		this.bloquearLancamentoProducao3ItemCapsula = bloquearLancamentoProducao3ItemCapsula;
	}

	public Boolean getBloquearLancamentoProducao3ItemShake() {
		return bloquearLancamentoProducao3ItemShake;
	}

	public void setBloquearLancamentoProducao3ItemShake(Boolean bloquearLancamentoProducao3ItemShake) {
		this.bloquearLancamentoProducao3ItemShake = bloquearLancamentoProducao3ItemShake;
	}

	public Boolean getBloquearLancamentoProducao3ItemEnvase() {
		return bloquearLancamentoProducao3ItemEnvase;
	}

	public void setBloquearLancamentoProducao3ItemEnvase(Boolean bloquearLancamentoProducao3ItemEnvase) {
		this.bloquearLancamentoProducao3ItemEnvase = bloquearLancamentoProducao3ItemEnvase;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Date getData_alteracao() {
		return data_alteracao;
	}

	public void setData_alteracao(Date data_alteracao) {
		this.data_alteracao = data_alteracao;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Long getAnoBase() {
		return anoBase;
	}

	public void setAnoBase(Long anoBase) {
		this.anoBase = anoBase;
	}

	@Override
	public String toString() {
		return "ConfiguracoesLog [codigo=" + codigo + ", usuario=" + usuario + ", data_alteracao=" + data_alteracao
				+ ", maquina=" + maquina + ", ip=" + ip + ", limiteDiarioSache=" + limiteDiarioSache
				+ ", limiteDiarioCapsula=" + limiteDiarioCapsula + ", limiteDiarioShake=" + limiteDiarioShake
				+ ", limiteDiarioEnvase=" + limiteDiarioEnvase + ", bloquearLancamentoProducao="
				+ bloquearLancamentoProducao + ", bloquearLancamentoProducao2=" + bloquearLancamentoProducao2
				+ ", bloquearLancamentoProducao3=" + bloquearLancamentoProducao3
				+ ", bloquearLancamentoProducaoItemSache=" + bloquearLancamentoProducaoItemSache
				+ ", bloquearLancamentoProducaoItemCapsula=" + bloquearLancamentoProducaoItemCapsula
				+ ", bloquearLancamentoProducaoItemShake=" + bloquearLancamentoProducaoItemShake
				+ ", bloquearLancamentoProducaoItemEnvase=" + bloquearLancamentoProducaoItemEnvase
				+ ", bloquearLancamentoProducao2ItemSache=" + bloquearLancamentoProducao2ItemSache
				+ ", bloquearLancamentoProducao2ItemCapsula=" + bloquearLancamentoProducao2ItemCapsula
				+ ", bloquearLancamentoProducao2ItemShake=" + bloquearLancamentoProducao2ItemShake
				+ ", bloquearLancamentoProducao2ItemEnvase=" + bloquearLancamentoProducao2ItemEnvase
				+ ", bloquearLancamentoProducao3ItemSache=" + bloquearLancamentoProducao3ItemSache
				+ ", bloquearLancamentoProducao3ItemCapsula=" + bloquearLancamentoProducao3ItemCapsula
				+ ", bloquearLancamentoProducao3ItemShake=" + bloquearLancamentoProducao3ItemShake
				+ ", bloquearLancamentoProducao3ItemEnvase=" + bloquearLancamentoProducao3ItemEnvase + ", anoBase="
				+ anoBase + "]";
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
		ConfiguracoesLog other = (ConfiguracoesLog) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
}
