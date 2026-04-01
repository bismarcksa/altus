package br.com.aformula.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.dao.SupervisaoEquipeDAO;
import br.com.aformula.domain.SupervisaoEquipe;
import br.com.aformula.domain.SupervisaoEquipeID;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class SupervisaoEquipeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SupervisaoEquipeID supervisaoEquipeID;
	public SupervisaoEquipe supervisaoEquipeCadastro;
	private List<SupervisaoEquipe> listaSupervisoesEquipe;
	private List<SupervisaoEquipe> listaSupervisoesEquipeFiltrados;
	private String acao;
	private Long codigo;
	private boolean funcionarioVinculado;
	private List<Funcionario> listaFuncionariosSupervisaoEquipe;
	private List<Funcionario> listaFuncionariosTodosAtivos;
	private List<SupervisaoEquipe> listarEquipeLista;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;	
	
	public SupervisaoEquipe getSupervisaoEquipeCadastro() {
		return supervisaoEquipeCadastro;
	}

	public void setSupervisaoEquipeCadastro(SupervisaoEquipe supervisaoEquipeCadastro) {
		this.supervisaoEquipeCadastro = supervisaoEquipeCadastro;
	}

	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
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
	
	public List<Funcionario> getListaFuncionariosSupervisaoEquipe() {
		return listaFuncionariosSupervisaoEquipe;
	}

	public void setListaFuncionariosSupervisaoEquipe(List<Funcionario> listaFuncionariosSupervisaoEquipe) {
		this.listaFuncionariosSupervisaoEquipe = listaFuncionariosSupervisaoEquipe;
	}

	public SupervisaoEquipeID getSupervisaoEquipeID() {
		return supervisaoEquipeID;
	}

	public void setSupervisaoEquipeID(SupervisaoEquipeID supervisaoEquipeID) {
		this.supervisaoEquipeID = supervisaoEquipeID;
	}

	public List<SupervisaoEquipe> getListaSupervisoesEquipe() {
		return listaSupervisoesEquipe;
	}

	public void setListaSupervisoesEquipe(List<SupervisaoEquipe> listaSupervisoesEquipe) {
		this.listaSupervisoesEquipe = listaSupervisoesEquipe;
	}

	public List<SupervisaoEquipe> getListaSupervisoesEquipeFiltrados() {
		return listaSupervisoesEquipeFiltrados;
	}

	public void setListaSupervisoesEquipeFiltrados(List<SupervisaoEquipe> listaSupervisoesEquipeFiltrados) {
		this.listaSupervisoesEquipeFiltrados = listaSupervisoesEquipeFiltrados;
	}

	public List<Funcionario> getListaFuncionariosTodosAtivos() {
		return listaFuncionariosTodosAtivos;
	}

	public void setListaFuncionariosTodosAtivos(List<Funcionario> listaFuncionariosTodosAtivos) {
		this.listaFuncionariosTodosAtivos = listaFuncionariosTodosAtivos;
	}

	public List<SupervisaoEquipe> getListarEquipeLista() {
		return listarEquipeLista;
	}

	public void setListarEquipeLista(List<SupervisaoEquipe> listarEquipeLista) {
		this.listarEquipeLista = listarEquipeLista;
	}

	public void novo() {
		supervisaoEquipeCadastro = new SupervisaoEquipe();
	}
	
	public void salvar() {
		try {
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			
			//GRAVA O SUPERVISOR COMO O USUÁRIO LOGADO
			supervisaoEquipeID.setSupervisor(funcionario);
			supervisaoEquipeCadastro.setSupervisaoEquipeID(supervisaoEquipeID);
			
			
			SupervisaoEquipeDAO supervisaoEquipeDAO2 = new SupervisaoEquipeDAO();					
			funcionarioVinculado = supervisaoEquipeDAO2.buscarPorFuncionario(supervisaoEquipeCadastro.getSupervisaoEquipeID().getFuncionario().getCodigo());
			

			if(funcionarioVinculado) {
				FacesUtil.adicionarMsgErro("Funcionário já vinculado a um supervisor.");
				return;
			}
			
			SupervisaoEquipeDAO supervisaoEquipeDAO = new SupervisaoEquipeDAO();				
			supervisaoEquipeDAO.salvar(supervisaoEquipeCadastro);			
			supervisaoEquipeCadastro = new SupervisaoEquipe();
			
			FacesUtil.adicionarMsgInfo("Supervisao de Equipe salva com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Supervisão:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			SupervisaoEquipeDAO supervisaoEquipeDAO = new SupervisaoEquipeDAO();
			listaSupervisoesEquipe = supervisaoEquipeDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Supervisão de Equipe:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {				
			supervisaoEquipeCadastro = new SupervisaoEquipe();
			supervisaoEquipeID = new SupervisaoEquipeID();	
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			listaFuncionariosTodosAtivos = funcionarioDAO.listarTodosAtivoNaoLiderancao();			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da Supervisão:" + ex.getMessage());
		}
	}
	
	public void excluir(SupervisaoEquipe supervisaoEquipe) {
		
		this.supervisaoEquipeCadastro = supervisaoEquipe;
		
		try {
			SupervisaoEquipeDAO supervisaoEquipeDAO = new SupervisaoEquipeDAO();
			supervisaoEquipeDAO.excluir(supervisaoEquipeCadastro);
			
			// Atualiza a lista para refletir a exclusão
	        listarEquipeLista = supervisaoEquipeDAO.buscarPorCodigo(supervisaoEquipe.getSupervisaoEquipeID().getSupervisor().getCodigo());

			
			FacesUtil.adicionarMsgInfo("SupervisaoEquipe removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Supervisão:" + ex.getMessage());
		}	
	}
	
	public void editar() {
		try {
			SupervisaoEquipeDAO supervisaoEquipeDAO = new SupervisaoEquipeDAO();						
			supervisaoEquipeDAO.editar(supervisaoEquipeCadastro);

			FacesUtil.adicionarMsgInfo("SupervisaoEquipe editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Supervisão:" + ex.getMessage());
		}	
	}
	
	//LISTA FUNCIONARIOS DE UMA EQUIPE
	public void listarEquipe(Long codigoSupervisor) {
		try {
			SupervisaoEquipeDAO supervisaoEquipeDAO = new SupervisaoEquipeDAO();		
			listarEquipeLista = supervisaoEquipeDAO.buscarPorCodigo(codigoSupervisor);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar equipe do Supervisor:" + ex.getMessage());
		}
	}
}
