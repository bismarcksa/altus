package br.com.aformula.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.MetasGrupoDAO;
import br.com.aformula.domain.MetasGrupo;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MetasGrupoBean {

	public MetasGrupo metasGrupoCadastro;
	private List<MetasGrupo> listaMetasGrupo;
	private String acao;
	private Long codigo;
	
	private BigDecimal totalMetaGrupo;
	private BigDecimal totalVendasGrupo;
	
	private BigDecimal mediaMetaPercentualGrupo;
	
	public MetasGrupo getMetasGrupoCadastro() {
		return metasGrupoCadastro;
	}

	public void setMetasGrupoCadastro(MetasGrupo metasGrupoCadastro) {
		this.metasGrupoCadastro = metasGrupoCadastro;
	}

	public List<MetasGrupo> getListaMetasGrupo() {
		return listaMetasGrupo;
	}

	public void setListaMetasGrupo(List<MetasGrupo> listaMetasGrupo) {
		this.listaMetasGrupo = listaMetasGrupo;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public BigDecimal getTotalMetaGrupo() {
		return totalMetaGrupo;
	}

	public void setTotalMetaGrupo(BigDecimal totalMetaGrupo) {
		this.totalMetaGrupo = totalMetaGrupo;
	}

	public BigDecimal getTotalVendasGrupo() {
		return totalVendasGrupo;
	}

	public void setTotalVendasGrupo(BigDecimal totalVendasGrupo) {
		this.totalVendasGrupo = totalVendasGrupo;
	}

	public BigDecimal getMediaMetaPercentualGrupo() {
		return mediaMetaPercentualGrupo;
	}

	public void setMediaMetaPercentualGrupo(BigDecimal mediaMetaPercentualGrupo) {
		this.mediaMetaPercentualGrupo = mediaMetaPercentualGrupo;
	}
	
	public void novo() {
		metasGrupoCadastro = new MetasGrupo();
	}
	
	public void salvar() {
		try {
			MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();
			metasGrupoCadastro.setVendas(BigDecimal.ZERO);
			metasGrupoDAO.salvar(metasGrupoCadastro);
			metasGrupoCadastro = new MetasGrupo();

			FacesUtil.adicionarMsgInfo("Meta do Grupo salvo com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Metas do Grupo:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();
			listaMetasGrupo = metasGrupoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Metas do Grupo:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();
				metasGrupoCadastro = metasGrupoDAO.buscarPorCodigo(codigo);

			}else {
				metasGrupoCadastro = new MetasGrupo();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();
			metasGrupoDAO.excluir(metasGrupoCadastro);
			
			FacesUtil.adicionarMsgInfo("Metas do Grupo removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Metas do Grupo:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();
		
			metasGrupoDAO.editar(metasGrupoCadastro);

			FacesUtil.adicionarMsgInfo("Metas do Grupo editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Metas do Grupo:" + ex.getMessage());
		}	
	}
	
	public void carregarTotalGrupo() {
		try {
			
			MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();

			//PEGA ANO ATUAL
			Calendar cal = GregorianCalendar.getInstance();
			
			totalMetaGrupo = metasGrupoDAO.totalMetaGrupo(cal.get(Calendar.YEAR));
			totalVendasGrupo = metasGrupoDAO.totalVendasGrupo(cal.get(Calendar.YEAR));
			mediaMetaPercentualGrupo = metasGrupoDAO.mediaMetaPercentualGrupo(cal.get(Calendar.YEAR));

		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}
}
