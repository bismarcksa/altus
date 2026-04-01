package br.com.aformula.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import br.com.aformula.dao.IndicadoresRejeicaoDAO;
import br.com.aformula.domain.IndicadoresRejeicao;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class IndicadoresRejeicaoBean {

	IndicadoresRejeicao indicadoresRejeicao = new IndicadoresRejeicao();
	IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();
	
	public IndicadoresRejeicao indicadoresRejeicaoCadastro, rejeicaoExiste;
	private List<IndicadoresRejeicao> listaIndicadoresRejeicao;
	private List<IndicadoresRejeicao> listaIndicadoresRejeicao2;
	private List<IndicadoresRejeicao> listaIndicadoresRejeicaoFiltrados;
	private String acao;
	private Long codigo;
	
	private String valorMediaLoja67;
	private String valorMediaLoja68;
	private String valorMediaLoja70;
	private String valorMediaGrupo;
	
	private LineChartModel graficoLinhaRejeitados;
	
	@PostConstruct
	public void init() {
		createLineModels();
	}

	public LineChartModel getGraficoLinhaRejeitados() {
		return graficoLinhaRejeitados;
	}

	public void setGraficoLinhaRejeitados(LineChartModel graficoLinhaRejeitados) {
		this.graficoLinhaRejeitados = graficoLinhaRejeitados;
	}

	public IndicadoresRejeicao getIndicadoresRejeicaoCadastro() {
		return indicadoresRejeicaoCadastro;
	}

	public void setIndicadoresRejeicaoCadastro(IndicadoresRejeicao indicadoresRejeicaoCadastro) {
		this.indicadoresRejeicaoCadastro = indicadoresRejeicaoCadastro;
	}

	public List<IndicadoresRejeicao> getListaIndicadoresRejeicao() {
		return listaIndicadoresRejeicao;
	}

	public void setListaIndicadoresRejeicao(List<IndicadoresRejeicao> listaIndicadoresRejeicao) {
		this.listaIndicadoresRejeicao = listaIndicadoresRejeicao;
	}
	
	public IndicadoresRejeicao getIndicadoresRejeicao() {
		return indicadoresRejeicao;
	}

	public void setIndicadoresRejeicao(IndicadoresRejeicao indicadoresRejeicao) {
		this.indicadoresRejeicao = indicadoresRejeicao;
	}

	public IndicadoresRejeicaoDAO getIndicadoresRejeicaoDAO() {
		return indicadoresRejeicaoDAO;
	}

	public void setIndicadoresRejeicaoDAO(IndicadoresRejeicaoDAO indicadoresRejeicaoDAO) {
		this.indicadoresRejeicaoDAO = indicadoresRejeicaoDAO;
	}

	public List<IndicadoresRejeicao> getListaIndicadoresRejeicaoFiltrados() {
		return listaIndicadoresRejeicaoFiltrados;
	}

	public void setListaIndicadoresRejeicaoFiltrados(List<IndicadoresRejeicao> listaIndicadoresRejeicaoFiltrados) {
		this.listaIndicadoresRejeicaoFiltrados = listaIndicadoresRejeicaoFiltrados;
	}
	
	public List<IndicadoresRejeicao> getListaIndicadoresRejeicao2() {
		return listaIndicadoresRejeicao2;
	}

	public void setListaIndicadoresRejeicao2(List<IndicadoresRejeicao> listaIndicadoresRejeicao2) {
		this.listaIndicadoresRejeicao2 = listaIndicadoresRejeicao2;
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

	public String getValorMediaLoja67() {
		return valorMediaLoja67;
	}

	public void setValorMediaLoja67(String valorMediaLoja67) {
		this.valorMediaLoja67 = valorMediaLoja67;
	}

	public String getValorMediaLoja68() {
		return valorMediaLoja68;
	}

	public void setValorMediaLoja68(String valorMediaLoja68) {
		this.valorMediaLoja68 = valorMediaLoja68;
	}

	public String getValorMediaLoja70() {
		return valorMediaLoja70;
	}

	public void setValorMediaLoja70(String valorMediaLoja70) {
		this.valorMediaLoja70 = valorMediaLoja70;
	}

	public String getValorMediaGrupo() {
		return valorMediaGrupo;
	}

	public void setValorMediaGrupo(String valorMediaGrupo) {
		this.valorMediaGrupo = valorMediaGrupo;
	}

	public void novo() {
		indicadoresRejeicaoCadastro = new IndicadoresRejeicao();
	}
	
	
	public void salvar() {
		try {
			IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();	
			
			rejeicaoExiste = indicadoresRejeicaoDAO.rejeicaoExiste(indicadoresRejeicaoCadastro.getAno(), indicadoresRejeicaoCadastro.getMes());
			

			if(rejeicaoExiste == null) {	

				indicadoresRejeicaoDAO.salvar(indicadoresRejeicaoCadastro);
				indicadoresRejeicaoCadastro = new IndicadoresRejeicao();
				FacesUtil.adicionarMsgInfo("Indicador de Rejeição salvo com sucesso.");
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");	
			}else {
				FacesUtil.adicionarMsgAlerta("Existe Indicador de Rejeição cadastrado.");
			}		
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Indicador de Rejeição:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();
			listaIndicadoresRejeicao = indicadoresRejeicaoDAO.listar();	
			
			IndicadoresRejeicaoDAO indicadoresRejeicaoDAO2 = new IndicadoresRejeicaoDAO();
			listaIndicadoresRejeicao2 = indicadoresRejeicaoDAO2.listar12Ultimos();	
			
			valorMediaLoja67 = String.format("%.02f", indicadoresRejeicaoDAO2.mediaLoja67);
			valorMediaLoja68 = String.format("%.02f", indicadoresRejeicaoDAO2.mediaLoja68);
			valorMediaLoja70 = String.format("%.02f", indicadoresRejeicaoDAO2.mediaLoja70);
			valorMediaGrupo = String.format("%.02f", indicadoresRejeicaoDAO2.mediaGrupo);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Indicadores de Rejeição:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();
				indicadoresRejeicaoCadastro = indicadoresRejeicaoDAO.buscarPorCodigo(codigo);

			}else {
				indicadoresRejeicaoCadastro = new IndicadoresRejeicao();
			} 	
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();
			indicadoresRejeicaoDAO.excluir(indicadoresRejeicaoCadastro);
			FacesUtil.adicionarMsgInfo("Indicador de Rejeição removido com sucesso.");
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Indicador de Rejeição:" + ex.getMessage());
		}		
	}
	
	public void editar() {
		try {
			IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();		
			indicadoresRejeicaoDAO.editar(indicadoresRejeicaoCadastro);
			FacesUtil.adicionarMsgInfo("Indicador de Rejeição editado com sucesso.");
			FacesContext.getCurrentInstance().getExternalContext().redirect("gestaoIndicadores.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Indicador de Rejeição:" + ex.getMessage());
		}	
	}
	
	public void createLineModels() {
		graficoLinhaRejeitados = new LineChartModel();
		ChartData graficoDadosRejeitados = new ChartData();

		LineChartDataSet dataSetRejeitados = new LineChartDataSet();
		LineChartDataSet dataSet2Rejeitados = new LineChartDataSet();
		LineChartDataSet dataSet3Rejeitados = new LineChartDataSet();
		LineChartDataSet dataSet4Rejeitados = new LineChartDataSet();
       
		List<Object> valoresRejeitados = new ArrayList<>();
		List<Object> valores2Rejeitados = new ArrayList<>();
		List<Object> valores3Rejeitados = new ArrayList<>();
		List<Object> valores4Rejeitados = new ArrayList<>();
		
		// REJEITADOS!!!!
		// VALORES EM % FILIAL01		
		for(IndicadoresRejeicao indicadoresRejeicao:this.indicadoresRejeicaoDAO.listar12Ultimos()) {
			valoresRejeitados.add(indicadoresRejeicao.getFilial01());
		}
		
		
		dataSetRejeitados.setData(valoresRejeitados);
		dataSetRejeitados.setFill(false);
		dataSetRejeitados.setLabel("LOJA 67");
		dataSetRejeitados.setBorderColor("rgb(0, 83, 91)");
		dataSetRejeitados.setTension(0.3);
		graficoDadosRejeitados.addChartDataSet(dataSetRejeitados);

		// VALORES EM % FILIAL02
		for(IndicadoresRejeicao indicadoresRejeicao:this.indicadoresRejeicaoDAO.listar12Ultimos()) {
			valores2Rejeitados.add(indicadoresRejeicao.getFilial02());
		}
		dataSet2Rejeitados.setData(valores2Rejeitados);
		dataSet2Rejeitados.setFill(false);
		dataSet2Rejeitados.setLabel("LOJA 68");
		dataSet2Rejeitados.setBorderColor("rgb(0, 138, 151)");
		dataSet2Rejeitados.setTension(0.3);
		graficoDadosRejeitados.addChartDataSet(dataSet2Rejeitados);

		// VALORES EM % FILIAL03
		for(IndicadoresRejeicao indicadoresRejeicao:this.indicadoresRejeicaoDAO.listar12Ultimos()) {
			valores3Rejeitados.add(indicadoresRejeicao.getFilial03());
		}
		dataSet3Rejeitados.setData(valores3Rejeitados);
		dataSet3Rejeitados.setFill(false);
		dataSet3Rejeitados.setLabel("LOJA 70");
		dataSet3Rejeitados.setBorderColor("rgb(102, 185, 193)");
		dataSet3Rejeitados.setTension(0.3);
		graficoDadosRejeitados.addChartDataSet(dataSet3Rejeitados);
       
		// VALORES EM % GRUPO
		for(IndicadoresRejeicao indicadoresRejeicao:this.indicadoresRejeicaoDAO.listar12Ultimos()) {
			valores4Rejeitados.add(indicadoresRejeicao.getGrupo());
		}
		dataSet4Rejeitados.setData(valores4Rejeitados);
		dataSet4Rejeitados.setFill(false);
		dataSet4Rejeitados.setLabel("GRUPO");
		dataSet4Rejeitados.setBorderColor("rgb(204, 232, 234)");
		dataSet4Rejeitados.setTension(0.3);
		graficoDadosRejeitados.addChartDataSet(dataSet4Rejeitados);
									      
		List<String> labelsRejeitados = new ArrayList<>();
		// LISTAGEM DOS MESES REJEITADOS
		for(IndicadoresRejeicao indicadoresRejeicao:this.indicadoresRejeicaoDAO.listar12Ultimos()) {
			labelsRejeitados.add(indicadoresRejeicao.getMes().substring(0,3) + "/" + indicadoresRejeicao.getAno().toString().substring(2,4));
		}
		graficoDadosRejeitados.setLabels(labelsRejeitados);
            
		LineChartOptions opcoesRejeitados = new LineChartOptions();

		opcoesRejeitados.setMaintainAspectRatio(false);
		Title tituloRejeitados = new Title();
		tituloRejeitados.setDisplay(true);
//     	  tituloRejeitados.setText("rejeitados");
		opcoesRejeitados.setTitle(tituloRejeitados);

//       Title subtitle = new Title();
//       subtitle.setDisplay(true);
//       subtitle.setText("Line Chart Subtitle");
//       options.setSubtitle(subtitle);

		graficoLinhaRejeitados.setOptions(opcoesRejeitados);
		graficoLinhaRejeitados.setData(graficoDadosRejeitados);
		
				
		Legend legendRejeitados = new Legend();
		legendRejeitados.setDisplay(true);
//        legendRejeitados.setPosition("right");

        LegendLabel legendLabelsRejeitados = new LegendLabel();
        legendLabelsRejeitados.setFontStyle("bold");
        legendLabelsRejeitados.setFontColor("#2980B9");
        legendLabelsRejeitados.setFontSize(13);
        legendRejeitados.setLabels(legendLabelsRejeitados);
        opcoesRejeitados.setLegend(legendRejeitados);	
	}
	
}
