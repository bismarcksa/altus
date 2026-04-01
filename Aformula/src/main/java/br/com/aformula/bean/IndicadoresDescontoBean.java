package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.aformula.dao.IndicadoresDescontoDAO;
import br.com.aformula.domain.IndicadoresDesconto;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class IndicadoresDescontoBean {

	public IndicadoresDesconto indicadoresDescontoCadastro, descontoExiste;
	private List<IndicadoresDesconto> listaIndicadoresDesconto, listaIndicadoresDesconto2;
	private String acao;
	private Long codigo;
	
	private String valorMediaLoja67;
	private String valorMediaLoja68;
	private String valorMediaLoja70;
	private String valorMediaGrupo;

	public IndicadoresDesconto getIndicadoresDescontoCadastro() {
		return indicadoresDescontoCadastro;
	}

	public void setIndicadoresDescontoCadastro(IndicadoresDesconto indicadoresDescontoCadastro) {
		this.indicadoresDescontoCadastro = indicadoresDescontoCadastro;
	}

	public List<IndicadoresDesconto> getListaIndicadoresDesconto() {
		return listaIndicadoresDesconto;
	}

	public void setListaIndicadoresDesconto(List<IndicadoresDesconto> listaIndicadoresDesconto) {
		this.listaIndicadoresDesconto = listaIndicadoresDesconto;
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

	public List<IndicadoresDesconto> getListaIndicadoresDesconto2() {
		return listaIndicadoresDesconto2;
	}

	public void setListaIndicadoresDesconto2(List<IndicadoresDesconto> listaIndicadoresDesconto2) {
		this.listaIndicadoresDesconto2 = listaIndicadoresDesconto2;
	}

	public String getValorMediaLoja67() {
		return valorMediaLoja67;
	}

	public void setValorMediaLoja67(String valorMediaLoja67) {
		this.valorMediaLoja67 = valorMediaLoja67;
	}

	public String getValorMediaLoja68() {
		return valorMediaLoja68;
	}

	public void setValorMediaLoja68(String valorMediaLoja68) {
		this.valorMediaLoja68 = valorMediaLoja68;
	}

	public String getValorMediaLoja70() {
		return valorMediaLoja70;
	}

	public void setValorMediaLoja70(String valorMediaLoja70) {
		this.valorMediaLoja70 = valorMediaLoja70;
	}

	public String getValorMediaGrupo() {
		return valorMediaGrupo;
	}

	public void setValorMediaGrupo(String valorMediaGrupo) {
		this.valorMediaGrupo = valorMediaGrupo;
	}

	public void novo() {
		indicadoresDescontoCadastro = new IndicadoresDesconto();
	}
	
	public void salvar() {
		try {
			IndicadoresDescontoDAO indicadoresDescontoDAO = new IndicadoresDescontoDAO();
			
			descontoExiste = indicadoresDescontoDAO.descontoExiste(indicadoresDescontoCadastro.getAno(), indicadoresDescontoCadastro.getMes());
			
			if(descontoExiste == null) {	
				indicadoresDescontoDAO.salvar(indicadoresDescontoCadastro);
				indicadoresDescontoCadastro = new IndicadoresDesconto();
				FacesUtil.adicionarMsgInfo("Indicador de Desconto salvo com sucesso.");
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");	
			}else {
				FacesUtil.adicionarMsgAlerta("Existe Indicador de Desconto cadastrado.");
			}				
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Indicador de Desconto:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			IndicadoresDescontoDAO indicadoresDescontoDAO = new IndicadoresDescontoDAO();
			listaIndicadoresDesconto = indicadoresDescontoDAO.listar();
			
			IndicadoresDescontoDAO indicadoresDescontoDAO2 = new IndicadoresDescontoDAO();
			listaIndicadoresDesconto2 = indicadoresDescontoDAO2.listar12Ultimos();	
			
			valorMediaLoja67 = String.format("%.02f", indicadoresDescontoDAO2.mediaLoja67);
			valorMediaLoja68 = String.format("%.02f", indicadoresDescontoDAO2.mediaLoja68);
			valorMediaLoja70 = String.format("%.02f", indicadoresDescontoDAO2.mediaLoja70);
			valorMediaGrupo = String.format("%.02f", indicadoresDescontoDAO2.mediaGrupo);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Indicadores de Desconto:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				IndicadoresDescontoDAO indicadoresDescontoDAO = new IndicadoresDescontoDAO();
				indicadoresDescontoCadastro = indicadoresDescontoDAO.buscarPorCodigo(codigo);

			}else {
				indicadoresDescontoCadastro = new IndicadoresDesconto();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			IndicadoresDescontoDAO indicadoresDescontoDAO = new IndicadoresDescontoDAO();
			indicadoresDescontoDAO.excluir(indicadoresDescontoCadastro);
			
			FacesUtil.adicionarMsgInfo("Indicador de Desconto removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Indicador de Desconto:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			IndicadoresDescontoDAO indicadoresDescontoDAO = new IndicadoresDescontoDAO();
		
			indicadoresDescontoDAO.editar(indicadoresDescontoCadastro);

			FacesUtil.adicionarMsgInfo("Indicador de Desconto editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Indicador de Desconto:" + ex.getMessage());
		}	
	}
}
