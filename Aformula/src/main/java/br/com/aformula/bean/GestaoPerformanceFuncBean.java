package br.com.aformula.bean;

import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.GestaoPerformanceFuncDAO;
import br.com.aformula.domain.GestaoPerformanceFunc;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class GestaoPerformanceFuncBean {

	public GestaoPerformanceFunc gestaoPerformanceFuncCadastro, performanceFuncExiste;
	private List<GestaoPerformanceFunc> listaGestaoPerformanceFunc;
	private List<GestaoPerformanceFunc> listaGestaoPerformanceFuncFiltrados;
	
	private String acao;
	private Long codigo;
	
	private BigDecimal totalMetaPerformanceFunc;
	private BigDecimal totalVendasPerformanceFunc;
	
	private Double mediaMetaPercentual;
	private Double mediaRejeicaoPercentual;
	
	private Integer quantMetaBatida;
	private Integer quantMetaAbaixo80;

	@ManagedProperty(value = "#{gestaoPerformanceBean}")
	private GestaoPerformanceBean gestaoPerformanceBean;
	
	public GestaoPerformanceFunc getGestaoPerformanceFuncCadastro() {
		return gestaoPerformanceFuncCadastro;
	}

	public void setGestaoPerformanceFuncCadastro(GestaoPerformanceFunc gestaoPerformanceFuncCadastro) {
		this.gestaoPerformanceFuncCadastro = gestaoPerformanceFuncCadastro;
	}

	public List<GestaoPerformanceFunc> getListaGestaoPerformanceFunc() {
		return listaGestaoPerformanceFunc;
	}

	public void setListaGestaoPerformanceFunc(List<GestaoPerformanceFunc> listaGestaoPerformanceFunc) {
		this.listaGestaoPerformanceFunc = listaGestaoPerformanceFunc;
	}

	public List<GestaoPerformanceFunc> getListaGestaoPerformanceFuncFiltrados() {
		return listaGestaoPerformanceFuncFiltrados;
	}

	public void setListaGestaoPerformanceFuncFiltrados(List<GestaoPerformanceFunc> listaGestaoPerformanceFuncFiltrados) {
		this.listaGestaoPerformanceFuncFiltrados = listaGestaoPerformanceFuncFiltrados;
	}	
	
	public GestaoPerformanceBean getGestaoPerformanceBean() {
		return gestaoPerformanceBean;
	}

	public void setGestaoPerformanceBean(GestaoPerformanceBean gestaoPerformanceBean) {
		this.gestaoPerformanceBean = gestaoPerformanceBean;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Long getCodigo(){
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getTotalMetaPerformanceFunc() {
		return totalMetaPerformanceFunc;
	}

	public void setTotalMetaPerformanceFunc(BigDecimal totalMetaPerformanceFunc) {
		this.totalMetaPerformanceFunc = totalMetaPerformanceFunc;
	}

	public BigDecimal getTotalVendasPerformanceFunc() {
		return totalVendasPerformanceFunc;
	}

	public void setTotalVendasPerformanceFunc(BigDecimal totalVendasPerformanceFunc) {
		this.totalVendasPerformanceFunc = totalVendasPerformanceFunc;
	}

	public Double getMediaMetaPercentual() {
		return mediaMetaPercentual;
	}

	public void setMediaMetaPercentual(Double mediaMetaPercentual) {
		this.mediaMetaPercentual = mediaMetaPercentual;
	}

	public Double getMediaRejeicaoPercentual() {
		return mediaRejeicaoPercentual;
	}

	public void setMediaRejeicaoPercentual(Double mediaRejeicaoPercentual) {
		this.mediaRejeicaoPercentual = mediaRejeicaoPercentual;
	}

	public Integer getQuantMetaBatida() {
		return quantMetaBatida;
	}

	public void setQuantMetaBatida(Integer quantMetaBatida) {
		this.quantMetaBatida = quantMetaBatida;
	}

	public Integer getQuantMetaAbaixo80() {
		return quantMetaAbaixo80;
	}

	public void setQuantMetaAbaixo80(Integer quantMetaAbaixo80) {
		this.quantMetaAbaixo80 = quantMetaAbaixo80;
	}

	public void novo() {		
		gestaoPerformanceFuncCadastro = new GestaoPerformanceFunc();
	}
	
	public void salvar() {
		try {
			GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();
			
			gestaoPerformanceFuncCadastro.setGestaoPerformance(gestaoPerformanceBean.gestaoPerformanceCadastro);
			
			performanceFuncExiste = gestaoPerformanceFuncDAO.performanceFuncExiste(gestaoPerformanceFuncCadastro.getGestaoPerformance().getFuncionario().getCodigo(), gestaoPerformanceFuncCadastro.getGestaoPerformance().getAno(), gestaoPerformanceFuncCadastro.getMes());
			
			if(performanceFuncExiste == null) {
				gestaoPerformanceFuncDAO.salvar(gestaoPerformanceFuncCadastro);
				FacesUtil.adicionarMsgInfo("Performance do Funcion�rio salva com sucesso.");
			}else {
				FacesUtil.adicionarMsgAlerta("Gestão de Performance já existente.");
			}
			gestaoPerformanceFuncCadastro = new GestaoPerformanceFunc();
				
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Performance do Funcionário:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {						
			GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();
			listaGestaoPerformanceFunc = gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.gestaoPerformanceCadastro.getAno());
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Performance de Funcion�rio: " + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {		
		try {
			if (codigo != null) {
				GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();
				gestaoPerformanceFuncCadastro = gestaoPerformanceFuncDAO.buscarPorCodigo(codigo);
			}else {
				gestaoPerformanceFuncCadastro = new GestaoPerformanceFunc();
			} 	
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();
			gestaoPerformanceFuncDAO.excluir(gestaoPerformanceFuncCadastro);
			
			FacesUtil.adicionarMsgInfo("Performance de Funcion�rio removida com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Performance de Funcionário:" + ex.getMessage());
		}	
	}
	
	public void editar() {
		try {
			GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();
			gestaoPerformanceFuncDAO.editar(gestaoPerformanceFuncCadastro);

			FacesUtil.adicionarMsgInfo("Performance de Funcionário editada com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Performance de Funcionário:" + ex.getMessage());
		}	
	}
	
	public void carregarTotalPerformanceFunc() {
		try {
			GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();

			totalMetaPerformanceFunc = gestaoPerformanceFuncDAO.totalMetaPerformanceFunc(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.gestaoPerformanceCadastro.getAno());
			totalVendasPerformanceFunc = gestaoPerformanceFuncDAO.totalVendasPerformanceFunc(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.gestaoPerformanceCadastro.getAno());
			
			mediaMetaPercentual = gestaoPerformanceFuncDAO.mediaMetaPercentual(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.gestaoPerformanceCadastro.getAno());
			mediaRejeicaoPercentual = gestaoPerformanceFuncDAO.mediaRejeicaoPercentual(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.gestaoPerformanceCadastro.getAno());
			
//			quantMetaBatida = gestaoPerformanceFuncDAO.totalContratoMes("SACH�",data_inicial_mes, data_final_mes);
//			quantMetaAbaixo80 = gestaoPerformanceFuncDAO.totalContratoMes("C�PSULA �LEOSA", data_inicial_mes, data_final_mes);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}	
}
