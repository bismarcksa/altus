package br.com.aformula.domain;

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

@Entity
@Table(name = "tbl_fechamento_entrega")
@NamedQueries({ 
	@NamedQuery(name = "FechamentoEntrega.listar", query = "SELECT fechamentoEntrega FROM FechamentoEntrega fechamentoEntrega ORDER BY fechamentoEntrega.codigo DESC"),
	@NamedQuery(name = "FechamentoEntregaMesFechado.listar", query = "SELECT fechamentoEntrega FROM FechamentoEntrega fechamentoEntrega WHERE fechamentoEntrega.status = 'FECHADO' ORDER BY fechamentoEntrega.codigo ASC"),
	@NamedQuery(name = "FechamentoEntrega.buscarPorCodigo", query = "SELECT fechamentoEntrega FROM FechamentoEntrega fechamentoEntrega WHERE fechamentoEntrega.codigo = :codigo")
})
public class FechamentoEntrega {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_fechamento_entrega")
	@SequenceGenerator(name =  "sequence_fechamento_entrega", sequenceName = "sequence_fechamento_entrega", initialValue = 1, allocationSize = 1)
	@Column(name = "codigo")
	private Long codigo;

	@Column(name="ano")
	private Integer ano;
	
	@Column(name="mes", nullable = false)
	private String mes;
	
	@Column(name="total_entregas", nullable = false)
	private Long total_entregas;
	
	@Column(name="total_dav", nullable = false)
	private Double total_dav;
	
	@Column(name="total_recebidos", nullable = false)
	private Double total_recebidos;
	
	@Column(name="total_taxas", nullable = false)
	private Double total_taxas;
	
	@Column(name="ticket_medio", nullable = false)
	private Double ticket_medio;
	
	@Column(name="status")
	private String status;
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public Long getTotal_entregas() {
		return total_entregas;
	}

	public void setTotal_entregas(Long total_entregas) {
		this.total_entregas = total_entregas;
	}

	public Double getTotal_recebidos() {
		return total_recebidos;
	}

	public void setTotal_recebidos(Double total_recebidos) {
		this.total_recebidos = total_recebidos;
	}

	public Double getTotal_taxas() {
		return total_taxas;
	}

	public void setTotal_taxas(Double total_taxas) {
		this.total_taxas = total_taxas;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotal_dav() {
		return total_dav;
	}

	public void setTotal_dav(Double total_dav) {
		this.total_dav = total_dav;
	}

	public Double getTicket_medio() {
		return ticket_medio;
	}

	public void setTicket_medio(Double ticket_medio) {
		this.ticket_medio = ticket_medio;
	}

	@Override
	public String toString() {
		return "FechamentoEntrega [codigo=" + codigo + ", ano=" + ano + ", mes=" + mes + ", total_entregas="
				+ total_entregas + ", total_dav=" + total_dav + ", total_recebidos=" + total_recebidos
				+ ", total_taxas=" + total_taxas + ", ticket_medio=" + ticket_medio + ", status=" + status + "]";
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
		FechamentoEntrega other = (FechamentoEntrega) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
}
