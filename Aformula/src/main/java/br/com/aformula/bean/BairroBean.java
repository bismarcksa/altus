package br.com.aformula.bean;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.BairroDAO;
import br.com.aformula.domain.Bairro;
import br.com.aformula.relatorio.RelatorioBairro;
import br.com.aformula.relatorio.RelatorioBairro2;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class BairroBean {

	public Bairro bairroCadastro;
	private List<Bairro> listaBairros;
	private List<Bairro> listaBairrosFiltrados;
	private String acao;
	private Long codigo;	
	
	public Bairro getBairroCadastro() {
		return bairroCadastro;
	}

	public void setBairroCadastro(Bairro bairroCadastro) {
		this.bairroCadastro = bairroCadastro;
	}

	public List<Bairro> getListaBairros() {
		return listaBairros;
	}

	public void setListaBairros(List<Bairro> listaBairros) {
		this.listaBairros = listaBairros;
	}

	public List<Bairro> getListaBairrosFiltrados() {
		return listaBairrosFiltrados;
	}

	public void setListaBairrosFiltrados(List<Bairro> listaBairrosFiltrados) {
		this.listaBairrosFiltrados = listaBairrosFiltrados;
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
		bairroCadastro = new Bairro();
	}
	
	public void salvar() {
		try {
			BairroDAO bairroDAO = new BairroDAO();				
			bairroDAO.salvar(bairroCadastro);			
			bairroCadastro = new Bairro();
			
			FacesUtil.adicionarMsgInfo("Bairro salva com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar bairro:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			BairroDAO bairroDAO = new BairroDAO();
			listaBairros = bairroDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar bairro:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				BairroDAO bairroDAO = new BairroDAO();
				bairroCadastro = bairroDAO.buscarPorCodigo(codigo);
			}else {
				bairroCadastro = new Bairro();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da bairro:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			BairroDAO bairroDAO = new BairroDAO();
			bairroDAO.excluir(bairroCadastro);
			
			FacesUtil.adicionarMsgInfo("Bairro removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover bairro:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			BairroDAO bairroDAO = new BairroDAO();						
			bairroDAO.editar(bairroCadastro);

			FacesUtil.adicionarMsgInfo("Bairro editado com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar bairro:" + ex.getMessage());
		}	
	}
	
	public void gerarRelatorioBairro() {
	    RelatorioBairro relatorioBairro = new RelatorioBairro();
	    relatorioBairro.getRelatorioBairro(listaBairros);
	}
	
	public void gerarRelatorioBairro2(Long codigoBairro) {
	    RelatorioBairro2 relatorioBairro2 = new RelatorioBairro2();
	    System.out.println("Qual é o código? " + codigoBairro);
	    
		BairroDAO bairroDAO = new BairroDAO();
		bairroCadastro = bairroDAO.buscarPorCodigo(codigoBairro);

	    
	    
	    relatorioBairro2.getRelatorioBairro2(bairroCadastro, codigoBairro);
	}
}
