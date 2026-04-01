package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.FilialDAO;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.domain.Filial;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class FilialBean {

	public Filial filialCadastro;
	private List<Filial> listaFiliais;
	private List<Filial> listaFiliaisFiltrados;
	private String acao;
	private Long codigo;
	private List<Funcionario> listaFuncionariosFilial;
	
	
	public Filial getFilialCadastro() {
		return filialCadastro;
	}

	public void setFilialCadastro(Filial filialCadastro) {
		this.filialCadastro = filialCadastro;
	}

	public List<Filial> getListaFiliais() {
		return listaFiliais;
	}

	public void setListaFiliais(List<Filial> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}

	public List<Filial> getListaFiliaisFiltrados() {
		return listaFiliaisFiltrados;
	}

	public void setListaFiliaisFiltrados(List<Filial> listaFiliaisFiltrados) {
		this.listaFiliaisFiltrados = listaFiliaisFiltrados;
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
	
	public List<Funcionario> getListaFuncionariosFilial() {
		return listaFuncionariosFilial;
	}

	public void setListaFuncionariosFilial(List<Funcionario> listaFuncionariosFilial) {
		this.listaFuncionariosFilial = listaFuncionariosFilial;
	}

	public void novo() {
		filialCadastro = new Filial();
	}
	
	public void salvar() {
		try {
			FilialDAO filialDAO = new FilialDAO();				
			filialDAO.salvar(filialCadastro);			
			filialCadastro = new Filial();
			
			FacesUtil.adicionarMsgInfo("Filial salva com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar filial:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			FilialDAO filialDAO = new FilialDAO();
			listaFiliais = filialDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar filial:" + ex.getMessage());
		}
	}
	
	public void carregarPesquisaFuncFilial() {
		try {
			if (codigo != null) {
				FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
				listaFuncionariosFilial = funcionarioDAO.listarFuncFilial(filialCadastro.getCodigo());
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Funcionários da Filial:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				FilialDAO filialDAO = new FilialDAO();
				filialCadastro = filialDAO.buscarPorCodigo(codigo);
			}else {
				filialCadastro = new Filial();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da filial:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			FilialDAO filialDAO = new FilialDAO();
			filialDAO.excluir(filialCadastro);
			
			FacesUtil.adicionarMsgInfo("Filial removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover filial:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			FilialDAO filialDAO = new FilialDAO();						
			filialDAO.editar(filialCadastro);

			FacesUtil.adicionarMsgInfo("Filial editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar filial:" + ex.getMessage());
		}	
	}
}
