package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.CargoDAO;
import br.com.aformula.dao.DescricaoCargoDAO;
import br.com.aformula.domain.Cargo;
import br.com.aformula.domain.DescricaoCargo;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class DescricaoCargoBean {

	public DescricaoCargo descricaoCargoCadastro;
	private List<DescricaoCargo> listaDescricaoCargos;
	private List<DescricaoCargo> listaDescricaoCargosFiltrados;
	private String acao;
	private Long codigo;
	private List<Cargo> listaCargos;
	
	
	public DescricaoCargo getDescricaoCargoCadastro() {
		return descricaoCargoCadastro;
	}

	public void setDescricaoCargoCadastro(DescricaoCargo descricaoCargoCadastro) {
		this.descricaoCargoCadastro = descricaoCargoCadastro;
	}

	public List<DescricaoCargo> getListaDescricaoCargos() {
		return listaDescricaoCargos;
	}

	public void setListaDescricaoCargos(List<DescricaoCargo> listaDescricaoCargos) {
		this.listaDescricaoCargos = listaDescricaoCargos;
	}

	public List<DescricaoCargo> getListaDescricaoCargosFiltrados() {
		return listaDescricaoCargosFiltrados;
	}

	public void setListaDescricaoCargosFiltrados(List<DescricaoCargo> listaDescricaoCargosFiltrados) {
		this.listaDescricaoCargosFiltrados = listaDescricaoCargosFiltrados;
	}
	
	public List<Cargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
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
		descricaoCargoCadastro = new DescricaoCargo();
	}
	
	public void salvar() {
		try {
			DescricaoCargoDAO descricaoCargoDAO = new DescricaoCargoDAO();				
			descricaoCargoDAO.salvar(descricaoCargoCadastro);			
			descricaoCargoCadastro = new DescricaoCargo();
			
			FacesUtil.adicionarMsgInfo("Descricao Cargo salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Descricao Cargo:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			DescricaoCargoDAO descricaoCargoDAO = new DescricaoCargoDAO();
			listaDescricaoCargos = descricaoCargoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Descricao Cargo:" + ex.getMessage());
		}
	}

	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				DescricaoCargoDAO descricaoCargoDAO = new DescricaoCargoDAO();
				descricaoCargoCadastro = descricaoCargoDAO.buscarPorCodigo(codigo);
			}else {
				descricaoCargoCadastro = new DescricaoCargo();
			} 
			
			CargoDAO cargoDAO = new CargoDAO();
			listaCargos = cargoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da Descricao Cargo:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			DescricaoCargoDAO descricaoCargoDAO = new DescricaoCargoDAO();
			descricaoCargoDAO.excluir(descricaoCargoCadastro);
			
			FacesUtil.adicionarMsgInfo("Descricao Cargo removido com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Descricao Cargo:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			DescricaoCargoDAO descricaoCargoDAO = new DescricaoCargoDAO();						
			descricaoCargoDAO.editar(descricaoCargoCadastro);

			FacesUtil.adicionarMsgInfo("Descricao Cargo editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Descricao Cargo:" + ex.getMessage());
		}	
	}
}
