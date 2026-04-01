package br.com.aformula.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.MetasLoja70DAO;
import br.com.aformula.domain.MetasLoja70;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class MetasLoja70Bean {

	public MetasLoja70 metasLoja70Cadastro;
	private List<MetasLoja70> listaMetasLoja70;
	private String acao;
	private Long codigo;
	
	private BigDecimal totalMetaLoja70;
	private BigDecimal totalVendasLoja70;
	
	private BigDecimal mediaMetaPercentualLoja70;
	
	public MetasLoja70 getMetasLoja70Cadastro() {
		return metasLoja70Cadastro;
	}

	public void setMetasLoja70Cadastro(MetasLoja70 metasLoja70Cadastro) {
		this.metasLoja70Cadastro = metasLoja70Cadastro;
	}

	public List<MetasLoja70> getListaMetasLoja70() {
		return listaMetasLoja70;
	}

	public void setListaMetasLoja70(List<MetasLoja70> listaMetasLoja70) {
		this.listaMetasLoja70 = listaMetasLoja70;
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
	
	public BigDecimal getTotalMetaLoja70() {
		return totalMetaLoja70;
	}

	public void setTotalMetaLoja70(BigDecimal totalMetaLoja70) {
		this.totalMetaLoja70 = totalMetaLoja70;
	}

	public BigDecimal getTotalVendasLoja70() {
		return totalVendasLoja70;
	}

	public void setTotalVendasLoja70(BigDecimal totalVendasLoja70) {
		this.totalVendasLoja70 = totalVendasLoja70;
	}

	public BigDecimal getMediaMetaPercentualLoja70() {
		return mediaMetaPercentualLoja70;
	}

	public void setMediaMetaPercentualLoja70(BigDecimal mediaMetaPercentualLoja70) {
		this.mediaMetaPercentualLoja70 = mediaMetaPercentualLoja70;
	}

	public void novo() {
		metasLoja70Cadastro = new MetasLoja70();
	}
	
	public void salvar() {
		try {
			MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();
			//PEGA ANO ATUAL
//			Calendar cal = GregorianCalendar.getInstance();
//			metasLoja70Cadastro.setAno(cal.get(Calendar.YEAR));
						
			metasLoja70DAO.salvar(metasLoja70Cadastro);
			
			metasLoja70Cadastro = new MetasLoja70();
//			novo();
			
			FacesUtil.adicionarMsgInfo("Metas Loja 70 salvo com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Metas Loja 70:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();
			listaMetasLoja70 = metasLoja70DAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Metas Loja 70:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();
				metasLoja70Cadastro = metasLoja70DAO.buscarPorCodigo(codigo);

			}else {
				metasLoja70Cadastro = new MetasLoja70();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();
			metasLoja70DAO.excluir(metasLoja70Cadastro);
			
			FacesUtil.adicionarMsgInfo("Metas Loja 70 removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Metas Loja 70:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();
		
			metasLoja70DAO.editar(metasLoja70Cadastro);

			FacesUtil.adicionarMsgInfo("Metas Loja 70 editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Metas Loja 70:" + ex.getMessage());
		}	
	}
	
	public void carregarTotalLoja70() {
		try {
			
			MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();

			//PEGA ANO ATUAL
			Calendar cal = GregorianCalendar.getInstance();
			
			totalMetaLoja70 = metasLoja70DAO.totalMetaLoja70(cal.get(Calendar.YEAR));
			totalVendasLoja70 = metasLoja70DAO.totalVendasLoja70(cal.get(Calendar.YEAR));
			mediaMetaPercentualLoja70 = metasLoja70DAO.mediaMetaPercentualLoja70(cal.get(Calendar.YEAR));

		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}
}
