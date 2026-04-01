package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.CargoDAO;
import br.com.aformula.domain.Cargo;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class CargoBean {

	public Cargo cargoCadastro;
	private List<Cargo> listaCargos;
	private List<Cargo> listaCargosFiltrados;
	private String acao;
	private Long codigo;
	
	
	public Cargo getCargoCadastro() {
		return cargoCadastro;
	}

	public void setCargoCadastro(Cargo cargoCadastro) {
		this.cargoCadastro = cargoCadastro;
	}

	public List<Cargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<Cargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public List<Cargo> getListaCargosFiltrados() {
		return listaCargosFiltrados;
	}

	public void setListaCargosFiltrados(List<Cargo> listaCargosFiltrados) {
		this.listaCargosFiltrados = listaCargosFiltrados;
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
		cargoCadastro = new Cargo();
	}
	
	public void salvar() {
		try {
			CargoDAO cargoDAO = new CargoDAO();				
			cargoDAO.salvar(cargoCadastro);			
			cargoCadastro = new Cargo();
			
			FacesUtil.adicionarMsgInfo("Cargo salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar cargo:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			CargoDAO cargoDAO = new CargoDAO();
			listaCargos = cargoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar cargo:" + ex.getMessage());
		}
	}

	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				CargoDAO cargoDAO = new CargoDAO();
				cargoCadastro = cargoDAO.buscarPorCodigo(codigo);
			}else {
				cargoCadastro = new Cargo();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da cargo:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			CargoDAO cargoDAO = new CargoDAO();
			cargoDAO.excluir(cargoCadastro);
			
			FacesUtil.adicionarMsgInfo("Cargo removido com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover cargo:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			CargoDAO cargoDAO = new CargoDAO();						
			cargoDAO.editar(cargoCadastro);

			FacesUtil.adicionarMsgInfo("Cargo editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar cargo:" + ex.getMessage());
		}	
	}
}
