package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.aformula.dao.IndicadoresTicketMedioDAO;
import br.com.aformula.domain.IndicadoresTicketMedio;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class IndicadoresTicketMedioBean {

	public IndicadoresTicketMedio indicadoresTicketMedioCadastro, ticketMedioExiste;
	private List<IndicadoresTicketMedio> listaIndicadoresTicketMedio, listaIndicadoresTicketMedio2;
	private String acao;
	private Long codigo;
	
	private String valorMediaLoja67;
	private String valorMediaLoja68;
	private String valorMediaLoja70;
	private String valorMediaGrupo;

	public IndicadoresTicketMedio getIndicadoresTicketMedioCadastro() {
		return indicadoresTicketMedioCadastro;
	}

	public void setIndicadoresTicketMedioCadastro(IndicadoresTicketMedio indicadoresTicketMedioCadastro) {
		this.indicadoresTicketMedioCadastro = indicadoresTicketMedioCadastro;
	}

	public List<IndicadoresTicketMedio> getListaIndicadoresTicketMedio() {
		return listaIndicadoresTicketMedio;
	}

	public void setListaIndicadoresTicketMedio(List<IndicadoresTicketMedio> listaIndicadoresTicketMedio) {
		this.listaIndicadoresTicketMedio = listaIndicadoresTicketMedio;
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
	
	public List<IndicadoresTicketMedio> getListaIndicadoresTicketMedio2() {
		return listaIndicadoresTicketMedio2;
	}

	public void setListaIndicadoresTicketMedio2(List<IndicadoresTicketMedio> listaIndicadoresTicketMedio2) {
		this.listaIndicadoresTicketMedio2 = listaIndicadoresTicketMedio2;
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
		indicadoresTicketMedioCadastro = new IndicadoresTicketMedio();
	}
	
	public void salvar() {
		try {
			IndicadoresTicketMedioDAO indicadoresTicketMedioDAO = new IndicadoresTicketMedioDAO();
			
			ticketMedioExiste = indicadoresTicketMedioDAO.ticketMedioExiste(indicadoresTicketMedioCadastro.getAno(), indicadoresTicketMedioCadastro.getMes());
			
			if(ticketMedioExiste == null) {	
				indicadoresTicketMedioDAO.salvar(indicadoresTicketMedioCadastro);
				indicadoresTicketMedioCadastro = new IndicadoresTicketMedio();
				FacesUtil.adicionarMsgInfo("Indicador de Ticket Médio salvo com sucesso.");
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");	
			}else {
				FacesUtil.adicionarMsgAlerta("Existe Indicador de Ticket Médio cadastrado.");
			}
			
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Indicador de Ticket Médio:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			IndicadoresTicketMedioDAO indicadoresTicketMedioDAO = new IndicadoresTicketMedioDAO();
			listaIndicadoresTicketMedio = indicadoresTicketMedioDAO.listar();
			
			IndicadoresTicketMedioDAO indicadoresTicketMedioDAO2 = new IndicadoresTicketMedioDAO();
			listaIndicadoresTicketMedio2 = indicadoresTicketMedioDAO2.listar12Ultimos();	
			
			valorMediaLoja67 = String.format("%.02f", indicadoresTicketMedioDAO2.mediaLoja67);
			valorMediaLoja68 = String.format("%.02f", indicadoresTicketMedioDAO2.mediaLoja68);
			valorMediaLoja70 = String.format("%.02f", indicadoresTicketMedioDAO2.mediaLoja70);
			valorMediaGrupo = String.format("%.02f", indicadoresTicketMedioDAO2.mediaGrupo);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Indicador de Ticket Médio:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				IndicadoresTicketMedioDAO indicadoresTicketMedioDAO = new IndicadoresTicketMedioDAO();
				indicadoresTicketMedioCadastro = indicadoresTicketMedioDAO.buscarPorCodigo(codigo);

			}else {
				indicadoresTicketMedioCadastro = new IndicadoresTicketMedio();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			IndicadoresTicketMedioDAO indicadoresTicketMedioDAO = new IndicadoresTicketMedioDAO();
			indicadoresTicketMedioDAO.excluir(indicadoresTicketMedioCadastro);
			
			FacesUtil.adicionarMsgInfo("Indicador de Ticket Médio removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Indicador de Ticket Médio:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			IndicadoresTicketMedioDAO indicadoresTicketMedioDAO = new IndicadoresTicketMedioDAO();
		
			indicadoresTicketMedioDAO.editar(indicadoresTicketMedioCadastro);

			FacesUtil.adicionarMsgInfo("Indicador de Ticket M�dio editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Indicador de Ticket Médio:" + ex.getMessage());
		}	
	}
}
