package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.EntregadorDAO;
import br.com.aformula.domain.Entregador;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class EntregadorBean {

	public Entregador entregadorCadastro;
	private List<Entregador> listaEntregadores;
	private List<Entregador> listaEntregadoresFiltrados;
	private String acao;
	private Long codigo;
	
	
	public Entregador getEntregadorCadastro() {
		return entregadorCadastro;
	}

	public void setEntregadorCadastro(Entregador entregadorCadastro) {
		this.entregadorCadastro = entregadorCadastro;
	}

	public List<Entregador> getListaEntregadores() {
		return listaEntregadores;
	}

	public void setListaEntregadores(List<Entregador> listaEntregadores) {
		this.listaEntregadores = listaEntregadores;
	}

	public List<Entregador> getListaEntregadoresFiltrados() {
		return listaEntregadoresFiltrados;
	}

	public void setListaEntregadoresFiltrados(List<Entregador> listaEntregadoresFiltrados) {
		this.listaEntregadoresFiltrados = listaEntregadoresFiltrados;
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

	public void novo() {
		entregadorCadastro = new Entregador();
	}
	
	public void salvar() {
		try {
			EntregadorDAO entregadorDAO = new EntregadorDAO();				
			entregadorDAO.salvar(entregadorCadastro);			
			entregadorCadastro = new Entregador();
			
			FacesUtil.adicionarMsgInfo("Entregador salva com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar entregador:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			EntregadorDAO entregadorDAO = new EntregadorDAO();
			listaEntregadores = entregadorDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar entregador:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				EntregadorDAO entregadorDAO = new EntregadorDAO();
				entregadorCadastro = entregadorDAO.buscarPorCodigo(codigo);
			}else {
				entregadorCadastro = new Entregador();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da entregador:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			EntregadorDAO entregadorDAO = new EntregadorDAO();
			entregadorDAO.excluir(entregadorCadastro);
			
			FacesUtil.adicionarMsgInfo("Entregador removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover entregador:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			EntregadorDAO entregadorDAO = new EntregadorDAO();						
			entregadorDAO.editar(entregadorCadastro);

			FacesUtil.adicionarMsgInfo("Entregador editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar entregador:" + ex.getMessage());
		}	
	}
}
