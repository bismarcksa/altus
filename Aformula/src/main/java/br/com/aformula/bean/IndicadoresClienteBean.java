package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.aformula.dao.IndicadoresClienteDAO;
import br.com.aformula.domain.IndicadoresCliente;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class IndicadoresClienteBean {

	public IndicadoresCliente indicadoresClienteCadastro, clienteExiste;
	private List<IndicadoresCliente> listaIndicadoresCliente, listaIndicadoresCliente2;
	private String acao;
	private Long codigo;
	
	private String valorMediaLoja67;
	private String valorMediaLoja68;
	private String valorMediaLoja70;
	private String valorMediaGrupo;

	public IndicadoresCliente getIndicadoresClienteCadastro() {
		return indicadoresClienteCadastro;
	}

	public void setIndicadoresClienteCadastro(IndicadoresCliente indicadoresClienteCadastro) {
		this.indicadoresClienteCadastro = indicadoresClienteCadastro;
	}

	public List<IndicadoresCliente> getListaIndicadoresCliente() {
		return listaIndicadoresCliente;
	}

	public void setListaIndicadoresCliente(List<IndicadoresCliente> listaIndicadoresCliente) {
		this.listaIndicadoresCliente = listaIndicadoresCliente;
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
	
	public List<IndicadoresCliente> getListaIndicadoresCliente2() {
		return listaIndicadoresCliente2;
	}

	public void setListaIndicadoresCliente2(List<IndicadoresCliente> listaIndicadoresCliente2) {
		this.listaIndicadoresCliente2 = listaIndicadoresCliente2;
	}

	public void novo() {
		indicadoresClienteCadastro = new IndicadoresCliente();
	}
	
	public void salvar() {
		try {
			IndicadoresClienteDAO indicadoresClienteDAO = new IndicadoresClienteDAO();
			
			clienteExiste = indicadoresClienteDAO.clienteExiste(indicadoresClienteCadastro.getAno(), indicadoresClienteCadastro.getMes());
			
			if(clienteExiste == null) {	
				indicadoresClienteDAO.salvar(indicadoresClienteCadastro);
				indicadoresClienteCadastro = new IndicadoresCliente();
				FacesUtil.adicionarMsgInfo("Indicador de Cliente salvo com sucesso.");
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");	
			}else {
				FacesUtil.adicionarMsgAlerta("Existe Indicador de Desconto cadastrado.");
			}		
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Indicador de Cliente:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			IndicadoresClienteDAO indicadoresClienteDAO = new IndicadoresClienteDAO();
			listaIndicadoresCliente = indicadoresClienteDAO.listar();
			
			IndicadoresClienteDAO indicadoresClienteDAO2 = new IndicadoresClienteDAO();
			listaIndicadoresCliente2 = indicadoresClienteDAO2.listar12Ultimos();	
			
			valorMediaLoja67 = String.format("%.0f", indicadoresClienteDAO2.mediaLoja67);
			valorMediaLoja68 = String.format("%.0f", indicadoresClienteDAO2.mediaLoja68);
			valorMediaLoja70 = String.format("%.0f", indicadoresClienteDAO2.mediaLoja70);
			valorMediaGrupo = String.format("%.0f", indicadoresClienteDAO2.mediaGrupo);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Indicadores de Cliente:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				IndicadoresClienteDAO indicadoresClienteDAO = new IndicadoresClienteDAO();
				indicadoresClienteCadastro = indicadoresClienteDAO.buscarPorCodigo(codigo);

			}else {
				indicadoresClienteCadastro = new IndicadoresCliente();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			IndicadoresClienteDAO indicadoresClienteDAO = new IndicadoresClienteDAO();
			indicadoresClienteDAO.excluir(indicadoresClienteCadastro);
			
			FacesUtil.adicionarMsgInfo("Indicador de Cliente removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Indicador de Cliente:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			IndicadoresClienteDAO indicadoresClienteDAO = new IndicadoresClienteDAO();
		
			indicadoresClienteDAO.editar(indicadoresClienteCadastro);

			FacesUtil.adicionarMsgInfo("Indicador de Cliente editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Indicador de Cliente:" + ex.getMessage());
		}	
	}
}
