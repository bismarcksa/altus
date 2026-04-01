package br.com.aformula.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.aformula.dao.MetasLoja67DAO;
import br.com.aformula.domain.MetasLoja67;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MetasLoja67Bean {

	public MetasLoja67 metasLoja67Cadastro;
	private List<MetasLoja67> listaMetasLoja67;
	private String acao;
	private Long codigo;
	
	private BigDecimal totalMetaLoja67;
	private BigDecimal totalVendasLoja67;
	
	private BigDecimal mediaMetaPercentualLoja67;
	
	public MetasLoja67 getMetasLoja67Cadastro() {
		return metasLoja67Cadastro;
	}

	public void setMetasLoja67Cadastro(MetasLoja67 metasLoja67Cadastro) {
		this.metasLoja67Cadastro = metasLoja67Cadastro;
	}

	public List<MetasLoja67> getListaMetasLoja67() {
		return listaMetasLoja67;
	}

	public void setListaMetasLoja67(List<MetasLoja67> listaMetasLoja67) {
		this.listaMetasLoja67 = listaMetasLoja67;
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

	public BigDecimal getTotalMetaLoja67() {
		return totalMetaLoja67;
	}

	public void setTotalMetaLoja67(BigDecimal totalMetaLoja67) {
		this.totalMetaLoja67 = totalMetaLoja67;
	}

	public BigDecimal getTotalVendasLoja67() {
		return totalVendasLoja67;
	}

	public void setTotalVendasLoja67(BigDecimal totalVendasLoja67) {
		this.totalVendasLoja67 = totalVendasLoja67;
	}

	public BigDecimal getMediaMetaPercentualLoja67() {
		return mediaMetaPercentualLoja67;
	}

	public void setMediaMetaPercentualLoja67(BigDecimal mediaMetaPercentualLoja67) {
		this.mediaMetaPercentualLoja67 = mediaMetaPercentualLoja67;
	}

	public void novo() {
		metasLoja67Cadastro = new MetasLoja67();
	}
	
	public void salvar() {
		try {
			MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
			//PEGA ANO ATUAL
//			Calendar cal = GregorianCalendar.getInstance();
//			metasLoja67Cadastro.setAno(cal.get(Calendar.YEAR));
						
			metasLoja67DAO.salvar(metasLoja67Cadastro);
			
			metasLoja67Cadastro = new MetasLoja67();
//			novo();
			
			FacesUtil.adicionarMsgInfo("Metas Loja 67 salvo com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Metas Loja 67:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
			listaMetasLoja67 = metasLoja67DAO.listar();
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Metas Loja 67:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
				metasLoja67Cadastro = metasLoja67DAO.buscarPorCodigo(codigo);

			}else {
				metasLoja67Cadastro = new MetasLoja67();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
			metasLoja67DAO.excluir(metasLoja67Cadastro);
			
			FacesUtil.adicionarMsgInfo("Metas Loja 67 removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Metas Loja 67:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
		
			metasLoja67DAO.editar(metasLoja67Cadastro);

			FacesUtil.adicionarMsgInfo("Metas Loja 67 editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Metas Loja 67:" + ex.getMessage());
		}	
	}
	
	public void carregarTotalLoja67() {
		try {		
			MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
			//PEGA ANO ATUAL
			Calendar cal = GregorianCalendar.getInstance();
			totalMetaLoja67 = metasLoja67DAO.totalMetaLoja67(cal.get(Calendar.YEAR));
			totalVendasLoja67 = metasLoja67DAO.totalVendasLoja67(cal.get(Calendar.YEAR));
			mediaMetaPercentualLoja67 = metasLoja67DAO.mediaMetaPercentualLoja67(cal.get(Calendar.YEAR));
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}
}
