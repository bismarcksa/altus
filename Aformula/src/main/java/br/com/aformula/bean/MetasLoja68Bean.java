package br.com.aformula.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.MetasLoja68DAO;
import br.com.aformula.domain.MetasLoja68;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MetasLoja68Bean {

	public MetasLoja68 metasLoja68Cadastro;
	private List<MetasLoja68> listaMetasLoja68;
	private String acao;
	private Long codigo;
	
	private BigDecimal totalMetaLoja68;
	private BigDecimal totalVendasLoja68;
	
	private BigDecimal mediaMetaPercentualLoja68;
	
	public MetasLoja68 getMetasLoja68Cadastro() {
		return metasLoja68Cadastro;
	}

	public void setMetasLoja68Cadastro(MetasLoja68 metasLoja68Cadastro) {
		this.metasLoja68Cadastro = metasLoja68Cadastro;
	}

	public List<MetasLoja68> getListaMetasLoja68() {
		return listaMetasLoja68;
	}

	public void setListaMetasLoja68(List<MetasLoja68> listaMetasLoja68) {
		this.listaMetasLoja68 = listaMetasLoja68;
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
	
	public BigDecimal getTotalMetaLoja68() {
		return totalMetaLoja68;
	}

	public void setTotalMetaLoja68(BigDecimal totalMetaLoja68) {
		this.totalMetaLoja68 = totalMetaLoja68;
	}

	public BigDecimal getTotalVendasLoja68() {
		return totalVendasLoja68;
	}

	public void setTotalVendasLoja68(BigDecimal totalVendasLoja68) {
		this.totalVendasLoja68 = totalVendasLoja68;
	}

	public BigDecimal getMediaMetaPercentualLoja68() {
		return mediaMetaPercentualLoja68;
	}

	public void setMediaMetaPercentualLoja68(BigDecimal mediaMetaPercentualLoja68) {
		this.mediaMetaPercentualLoja68 = mediaMetaPercentualLoja68;
	}

	public void novo() {
		metasLoja68Cadastro = new MetasLoja68();
	}
	
	public void salvar() {
		try {
			MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();
			//PEGA ANO ATUAL
//			Calendar cal = GregorianCalendar.getInstance();
//			metasLoja68Cadastro.setAno(cal.get(Calendar.YEAR));
						
			metasLoja68DAO.salvar(metasLoja68Cadastro);
			
			metasLoja68Cadastro = new MetasLoja68();
//			novo();
			
			FacesUtil.adicionarMsgInfo("Metas Loja 68 salvo com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Metas Loja 68:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();
			listaMetasLoja68 = metasLoja68DAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Metas Loja 68:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();
				metasLoja68Cadastro = metasLoja68DAO.buscarPorCodigo(codigo);

			}else {
				metasLoja68Cadastro = new MetasLoja68();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();
			metasLoja68DAO.excluir(metasLoja68Cadastro);
			
			FacesUtil.adicionarMsgInfo("Metas Loja 68 removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Metas Loja 68:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();
		
			metasLoja68DAO.editar(metasLoja68Cadastro);

			FacesUtil.adicionarMsgInfo("Metas Loja 68 editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Metas Loja 68:" + ex.getMessage());
		}	
	}
	
	public void carregarTotalLoja68() {
		try {
			
			MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();

			//PEGA ANO ATUAL
			Calendar cal = GregorianCalendar.getInstance();
			
			totalMetaLoja68 = metasLoja68DAO.totalMetaLoja68(cal.get(Calendar.YEAR));
			totalVendasLoja68 = metasLoja68DAO.totalVendasLoja68(cal.get(Calendar.YEAR));
			mediaMetaPercentualLoja68 = metasLoja68DAO.mediaMetaPercentualLoja68(cal.get(Calendar.YEAR));

		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}
}
