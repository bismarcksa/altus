package br.com.aformula.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.FilterMeta;
import org.primefaces.util.LangUtils;
import br.com.aformula.dao.AnaliseRepresentanteGrupoDAO;
import br.com.aformula.domain.AnaliseRepresentanteGrupo;
import br.com.aformula.grafico.Grafico;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class AnaliseRepresentanteGrupoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	public AnaliseRepresentanteGrupo analiseRepresentanteGrupoCadastro;
	private List<AnaliseRepresentanteGrupo> listaAnaliseRepresentanteGrupo;
	private List<AnaliseRepresentanteGrupo>  listaAnaliseRepresentanteGrupoFiltrados;
	private String acao;
	private Long codigo;
	
	private List<FilterMeta> filterBy;
	
	private boolean globalFilterOnly;
	
	@PostConstruct
    public void init() {
		globalFilterOnly = false;
		
        filterBy = new ArrayList<>();
    }
	
	public List<FilterMeta> getFilterBy() {
		return filterBy;
	}

	public void setFilterBy(List<FilterMeta> filterBy) {
		this.filterBy = filterBy;
	}

	public boolean isGlobalFilterOnly() {
		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {
		this.globalFilterOnly = globalFilterOnly;
	}

	public AnaliseRepresentanteGrupo getAnaliseRepresentanteGrupoCadastro() {
		return analiseRepresentanteGrupoCadastro;
	}

	public void setAnaliseRepresentanteGrupoCadastro(AnaliseRepresentanteGrupo analiseRepresentanteGrupoCadastro) {
		this.analiseRepresentanteGrupoCadastro = analiseRepresentanteGrupoCadastro;
	}

	public List<AnaliseRepresentanteGrupo> getListaAnaliseRepresentanteGrupo() {
		return listaAnaliseRepresentanteGrupo;
	}

	public void setListaAnaliseRepresentanteGrupo(List<AnaliseRepresentanteGrupo> listaAnaliseRepresentanteGrupo) {
		this.listaAnaliseRepresentanteGrupo = listaAnaliseRepresentanteGrupo;
	}
	
	public List<AnaliseRepresentanteGrupo> getListaAnaliseRepresentanteGrupoFiltrados() {
		return listaAnaliseRepresentanteGrupoFiltrados;
	}

	public void setListaAnaliseRepresentanteGrupoFiltrados(
			List<AnaliseRepresentanteGrupo> listaAnaliseRepresentanteGrupoFiltrados) {
		this.listaAnaliseRepresentanteGrupoFiltrados = listaAnaliseRepresentanteGrupoFiltrados;
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
		analiseRepresentanteGrupoCadastro = new AnaliseRepresentanteGrupo();
	}
	
	public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }
	
	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }

        AnaliseRepresentanteGrupo analiseRepresentanteGrupo = (AnaliseRepresentanteGrupo) value;        
        return analiseRepresentanteGrupo.getAno().equals(filterText);
        
    }
	
	public void salvar() {
		try {
			AnaliseRepresentanteGrupoDAO analiseRepresentanteGrupoDAO = new AnaliseRepresentanteGrupoDAO();
			//PEGA ANO ATUAL
//			Calendar cal = GregorianCalendar.getInstance();
//			analiseRepresentanteGrupoCadastro.setAno(cal.get(Calendar.YEAR));
						
			analiseRepresentanteGrupoDAO.salvar(analiseRepresentanteGrupoCadastro);
			
			analiseRepresentanteGrupoCadastro = new AnaliseRepresentanteGrupo();
//			novo();
			
			FacesUtil.adicionarMsgInfo("Análise Representante Grupo salvo com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Análise Representante Grupo:" + ex.getMessage());
		}	
	}
	
	public void reload(){
		Grafico grafico = new Grafico();
		grafico.createBarraAnaliseRepresentanteGrupo();
	}
	
	public void carregarPesquisa() {
		try {			
			AnaliseRepresentanteGrupoDAO analiseRepresentanteGrupoDAO = new AnaliseRepresentanteGrupoDAO();
			listaAnaliseRepresentanteGrupo = analiseRepresentanteGrupoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Análise Representante Grupo:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				AnaliseRepresentanteGrupoDAO analiseRepresentanteGrupoDAO = new AnaliseRepresentanteGrupoDAO();
				analiseRepresentanteGrupoCadastro = analiseRepresentanteGrupoDAO.buscarPorCodigo(codigo);

			}else {
				analiseRepresentanteGrupoCadastro = new AnaliseRepresentanteGrupo();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			AnaliseRepresentanteGrupoDAO analiseRepresentanteGrupoDAO = new AnaliseRepresentanteGrupoDAO();
			analiseRepresentanteGrupoDAO.excluir(analiseRepresentanteGrupoCadastro);
			
			FacesUtil.adicionarMsgInfo("Análise Representante Grupo removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Análise Representante Grupo:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			AnaliseRepresentanteGrupoDAO analiseRepresentanteGrupoDAO = new AnaliseRepresentanteGrupoDAO();
		
			analiseRepresentanteGrupoDAO.editar(analiseRepresentanteGrupoCadastro);

			FacesUtil.adicionarMsgInfo("Análise Representante Grupo editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Análise Representante Grupo:" + ex.getMessage());
		}	
	}
}
