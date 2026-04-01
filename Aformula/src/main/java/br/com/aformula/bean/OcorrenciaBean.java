package br.com.aformula.bean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.OcorrenciaDAO;
import br.com.aformula.domain.Ocorrencia;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class OcorrenciaBean {

	public Ocorrencia ocorrenciaCadastro;
	private List<Ocorrencia> listaOcorrencias;
	private List<Ocorrencia> listaOcorrenciasFiltrados;
	private String acao;
	private Long codigo;	
	
	public Ocorrencia getOcorrenciaCadastro() {
		return ocorrenciaCadastro;
	}

	public void setOcorrenciaCadastro(Ocorrencia ocorrenciaCadastro) {
		this.ocorrenciaCadastro = ocorrenciaCadastro;
	}

	public List<Ocorrencia> getListaOcorrencias() {
		return listaOcorrencias;
	}

	public void setListaOcorrencias(List<Ocorrencia> listaOcorrencias) {
		this.listaOcorrencias = listaOcorrencias;
	}

	public List<Ocorrencia> getListaOcorrenciasFiltrados() {
		return listaOcorrenciasFiltrados;
	}

	public void setListaOcorrenciasFiltrados(List<Ocorrencia> listaOcorrenciasFiltrados) {
		this.listaOcorrenciasFiltrados = listaOcorrenciasFiltrados;
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
		ocorrenciaCadastro = new Ocorrencia();
	}
	
	public void salvar() {
		try {
			OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();				
			ocorrenciaDAO.salvar(ocorrenciaCadastro);			
			ocorrenciaCadastro = new Ocorrencia();
			
			FacesUtil.adicionarMsgInfo("Ocorrência salva com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar ocorrência:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
			listaOcorrencias = ocorrenciaDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar ocorrências:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
				ocorrenciaCadastro = ocorrenciaDAO.buscarPorCodigo(codigo);
			}else {
				ocorrenciaCadastro = new Ocorrencia();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da ocorrência:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			
			System.out.println("ENTROU AQUI: " + ocorrenciaCadastro);
			OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
			
			ocorrenciaDAO.excluir(ocorrenciaCadastro);
			
			FacesUtil.adicionarMsgInfo("Ocorrência removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover ocorrência:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();						
			ocorrenciaDAO.editar(ocorrenciaCadastro);

			FacesUtil.adicionarMsgInfo("Ocorrência editada com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar ocorrência:" + ex.getMessage());
		}	
	}
}
