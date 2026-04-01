package br.com.aformula.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.dao.GestaoPerformanceDAO;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.GestaoPerformance;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class GestaoPerformanceBean {

	public GestaoPerformance gestaoPerformanceCadastro, performanceExiste;
	private List<GestaoPerformance> listaGestaoPerformance;
	private List<GestaoPerformance> listaGestaoPerformanceFiltrados;
	
	public Funcionario funcionarioCadastro;
	private List<Funcionario> listaFuncionarios;
	
	private String acao;
	protected Long codigo;
	
	public GestaoPerformance getGestaoPerformanceCadastro() {
		return gestaoPerformanceCadastro;
	}

	public void setGestaoPerformanceCadastro(GestaoPerformance gestaoPerformanceCadastro) {
		this.gestaoPerformanceCadastro = gestaoPerformanceCadastro;
	}

	public List<GestaoPerformance> getListaGestaoPerformance() {
		return listaGestaoPerformance;
	}

	public void setListaGestaoPerformance(List<GestaoPerformance> listaGestaoPerformance) {
		this.listaGestaoPerformance = listaGestaoPerformance;
	}
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
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

	public List<GestaoPerformance> getListaGestaoPerformanceFiltrados() {
		return listaGestaoPerformanceFiltrados;
	}

	public void setListaGestaoPerformanceFiltrados(List<GestaoPerformance> listaGestaoPerformanceFiltrados) {
		this.listaGestaoPerformanceFiltrados = listaGestaoPerformanceFiltrados;
	}
	
	public Funcionario getFuncionarioCadastro() {
		return funcionarioCadastro;
	}

	public void setFuncionarioCadastro(Funcionario funcionarioCadastro) {
		this.funcionarioCadastro = funcionarioCadastro;
	}

	public void novo() {
		gestaoPerformanceCadastro = new GestaoPerformance();
	}
	
	public void salvar() {
		try {
			GestaoPerformanceDAO gestaoPerformanceDAO = new GestaoPerformanceDAO();
			performanceExiste = gestaoPerformanceDAO.performanceExiste(gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceCadastro.getAno());
			

			if(performanceExiste == null) {	
				//PEGA ANO ATUAL
				//Calendar cal = GregorianCalendar.getInstance();
				//gestaoPerformanceCadastro.setAno(cal.get(Calendar.YEAR));
				
				gestaoPerformanceDAO.salvar(gestaoPerformanceCadastro);
				FacesUtil.adicionarMsgInfo("Gestão de Performance salvo com sucesso.");	
			}else {
				FacesUtil.adicionarMsgAlerta("Existe Gestão de Performance para o Funcionário.");
			}
			
			gestaoPerformanceCadastro = new GestaoPerformance();
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Gestão de Performance:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {						
			GestaoPerformanceDAO gestaoPerformanceDAO = new GestaoPerformanceDAO();
			listaGestaoPerformance = gestaoPerformanceDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Gestão de Performance:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {	
		try {
			if (codigo != null) {
				GestaoPerformanceDAO gestaoPerformanceDAO = new GestaoPerformanceDAO();
				gestaoPerformanceCadastro = gestaoPerformanceDAO.buscarPorCodigo(codigo);

			}else {
				gestaoPerformanceCadastro = new GestaoPerformance();
			} 
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			listaFuncionarios = funcionarioDAO.listarAtivo();	
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			GestaoPerformanceDAO gestaoPerformanceDAO = new GestaoPerformanceDAO();
			gestaoPerformanceDAO.excluir(gestaoPerformanceCadastro);
			
			FacesUtil.adicionarMsgInfo("Gestão de Performance removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Gestão de Performance:" + ex.getMessage());
		}	
	}
	
	public void editar() {
		try {
			GestaoPerformanceDAO gestaoPerformanceDAO = new GestaoPerformanceDAO();
			gestaoPerformanceDAO.editar(gestaoPerformanceCadastro);

			FacesUtil.adicionarMsgInfo("Gestão de Performance editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Gestão de Performance:" + ex.getMessage());
		}	
	}
}
