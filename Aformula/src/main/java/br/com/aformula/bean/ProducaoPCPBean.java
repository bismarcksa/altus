package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.ProducaoPCPDAO;
import br.com.aformula.domain.ProducaoPCP;
import br.com.aformula.util.FacesUtil;


@ManagedBean
@ViewScoped
public class ProducaoPCPBean{

	private ProducaoPCP producaoPCPCadastro;
	private List<ProducaoPCP> listaProducoesPCP;

	public ProducaoPCP getProducaoPCPCadastro() {
		return producaoPCPCadastro;
	}

	public void setProducaoPCPCadastro(ProducaoPCP producaoPCPCadastro) {
		this.producaoPCPCadastro = producaoPCPCadastro;
	}

	public List<ProducaoPCP> getListaProducoesPCP() {
		return listaProducoesPCP;
	}

	public void setListaProducoesPCP(List<ProducaoPCP> listaProducoesPCP) {
		this.listaProducoesPCP = listaProducoesPCP;
	}
	
	

	public void novo() {
		producaoPCPCadastro = new ProducaoPCP();
	}

	
	//MÉTODO PARA CARREGAR TODAS AS SOLICTAÇÕES
	public void carregarPesquisa() {
		try {
			ProducaoPCPDAO producaoPCPDAO = new ProducaoPCPDAO();
			listaProducoesPCP = producaoPCPDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção pcp: " + ex.getMessage());
		}
	}
	
}
