package br.com.aformula.grafico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import br.com.aformula.dao.AnaliseGrupoDAO;
import br.com.aformula.dao.AnaliseRepresentanteDAO;
import br.com.aformula.dao.AnaliseRepresentanteGrupoDAO;
import br.com.aformula.dao.IndicadoresClienteDAO;
import br.com.aformula.dao.IndicadoresCortesiaDAO;
import br.com.aformula.dao.IndicadoresDescontoDAO;
import br.com.aformula.dao.IndicadoresRejeicaoDAO;
import br.com.aformula.dao.IndicadoresTicketMedioDAO;
import br.com.aformula.domain.AnaliseGrupo;
import br.com.aformula.domain.AnaliseRepresentante;
import br.com.aformula.domain.AnaliseRepresentanteGrupo;
import br.com.aformula.domain.IndicadoresCliente;
import br.com.aformula.domain.IndicadoresCortesia;
import br.com.aformula.domain.IndicadoresDesconto;
import br.com.aformula.domain.IndicadoresRejeicao;
import br.com.aformula.domain.IndicadoresTicketMedio;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Grafico implements Serializable {

	private List<AnaliseGrupo> listaAnaliseGrupo;
	private List<IndicadoresCortesia> listaCortesia;
	private List<AnaliseRepresentante> listaAnaliseRepresentante;
	
	IndicadoresRejeicao indicadoresRejeicao = new IndicadoresRejeicao();
	IndicadoresRejeicaoDAO indicadoresRejeicaoDAO = new IndicadoresRejeicaoDAO();
	
	IndicadoresCliente indicadoresCliente = new IndicadoresCliente();
	IndicadoresClienteDAO indicadoresClienteDAO = new IndicadoresClienteDAO();
	
	IndicadoresDesconto indicadoresDesconto = new IndicadoresDesconto();
	IndicadoresDescontoDAO indicadoresDescontoDAO = new IndicadoresDescontoDAO();
	
	IndicadoresTicketMedio indicadoresTicketMedio = new IndicadoresTicketMedio();
	IndicadoresTicketMedioDAO indicadoresTicketMedioDAO = new IndicadoresTicketMedioDAO();
	
	AnaliseGrupo analiseGrupo = new AnaliseGrupo();
	AnaliseGrupoDAO analiseGrupoDAO = new AnaliseGrupoDAO();
	
	IndicadoresCortesia indicadoresCortesia = new IndicadoresCortesia();
	IndicadoresCortesiaDAO indicadoresCortesiaDAO = new IndicadoresCortesiaDAO();
	
	AnaliseRepresentante analiseRepresentante = new AnaliseRepresentante();
	AnaliseRepresentanteDAO analiseRepresentanteDAO = new AnaliseRepresentanteDAO();
	
	AnaliseRepresentanteGrupo analiseRepresentanteGrupo = new AnaliseRepresentanteGrupo();
	AnaliseRepresentanteGrupoDAO analiseRepresentanteGrupoDAO = new AnaliseRepresentanteGrupoDAO();
	
	private LineChartModel graficoLinhaClientes;
	private LineChartModel graficoLinhaDescontos;
	private LineChartModel graficoLinhaTicketMedios;
	private LineChartModel graficoLinhaCortesia;
	private LineChartModel graficoLinhaAnaliseGrupo;
	private LineChartModel graficoLinhaAnaliseRepresentante;
	private BarChartModel graficoBarraAnaliseRepresentanteGrupo;
	
	@PostConstruct
	public void init() {
		createLineModels();
		createLinhaAnaliseGrupo();
		createLinhaCortesia();
		createLinhaAnaliseRepresentante();
		createBarraAnaliseRepresentanteGrupo();
	}
	
	public LineChartModel getGraficoLinhaClientes() {
		return graficoLinhaClientes;
	}

	public void setGraficoLinhaClientes(LineChartModel graficoLinhaClientes) {
		this.graficoLinhaClientes = graficoLinhaClientes;
	}

	public LineChartModel getGraficoLinhaDescontos() {
		return graficoLinhaDescontos;
	}

	public void setGraficoLinhaDescontos(LineChartModel graficoLinhaDescontos) {
		this.graficoLinhaDescontos = graficoLinhaDescontos;
	}

	public LineChartModel getGraficoLinhaTicketMedios() {
		return graficoLinhaTicketMedios;
	}

	public void setGraficoLinhaTicketMedios(LineChartModel graficoLinhaTicketMedios) {
		this.graficoLinhaTicketMedios = graficoLinhaTicketMedios;
	}

	public LineChartModel getGraficoLinhaAnaliseGrupo() {
		return graficoLinhaAnaliseGrupo;
	}

	public void setGraficoLinhaAnaliseGrupo(LineChartModel graficoLinhaAnaliseGrupo) {
		this.graficoLinhaAnaliseGrupo = graficoLinhaAnaliseGrupo;
	}
	
	public LineChartModel getGraficoLinhaAnaliseRepresentante() {
		return graficoLinhaAnaliseRepresentante;
	}

	public void setGraficoLinhaAnaliseRepresentante(LineChartModel graficoLinhaAnaliseRepresentante) {
		this.graficoLinhaAnaliseRepresentante = graficoLinhaAnaliseRepresentante;
	}

	public BarChartModel getGraficoBarraAnaliseRepresentanteGrupo() {
		return graficoBarraAnaliseRepresentanteGrupo;
	}

	public void setGraficoBarraAnaliseRepresentanteGrupo(BarChartModel graficoBarraAnaliseRepresentanteGrupo) {
		this.graficoBarraAnaliseRepresentanteGrupo = graficoBarraAnaliseRepresentanteGrupo;
	}

	public List<AnaliseGrupo> getListaAnaliseGrupo() {
		return listaAnaliseGrupo;
	}

	public void setListaAnaliseGrupo(List<AnaliseGrupo> listaAnaliseGrupo) {
		this.listaAnaliseGrupo = listaAnaliseGrupo;
	}

	public LineChartModel getGraficoLinhaCortesia() {
		return graficoLinhaCortesia;
	}

	public void setGraficoLinhaCortesia(LineChartModel graficoLinhaCortesia) {
		this.graficoLinhaCortesia = graficoLinhaCortesia;
	}

	public void createLineModels() {		
		graficoLinhaClientes = new LineChartModel();
		ChartData graficoDadosClientes = new ChartData();
		
		graficoLinhaDescontos = new LineChartModel();
		ChartData graficoDadosDescontos = new ChartData();
		
		graficoLinhaTicketMedios = new LineChartModel();
		ChartData graficoDadosTicketMedios = new ChartData();

		LineChartDataSet dataSetClientes = new LineChartDataSet();
		LineChartDataSet dataSet2Clientes = new LineChartDataSet();
		LineChartDataSet dataSet3Clientes = new LineChartDataSet();
		LineChartDataSet dataSet4Clientes = new LineChartDataSet();
		
		LineChartDataSet dataSetDescontos = new LineChartDataSet();
		LineChartDataSet dataSet2Descontos = new LineChartDataSet();
		LineChartDataSet dataSet3Descontos = new LineChartDataSet();
		LineChartDataSet dataSet4Descontos = new LineChartDataSet();
		
		LineChartDataSet dataSetTicketMedio = new LineChartDataSet();
		LineChartDataSet dataSet2TicketMedio = new LineChartDataSet();
		LineChartDataSet dataSet3TicketMedio = new LineChartDataSet();
		LineChartDataSet dataSet4TicketMedio = new LineChartDataSet();
	
		List<Object> valoresClientes = new ArrayList<>();
		List<Object> valores2Clientes = new ArrayList<>();
		List<Object> valores3Clientes = new ArrayList<>();
		List<Object> valores4Clientes = new ArrayList<>();
		
		List<Object> valoresDescontos = new ArrayList<>();
		List<Object> valores2Descontos = new ArrayList<>();
		List<Object> valores3Descontos = new ArrayList<>();
		List<Object> valores4Descontos = new ArrayList<>();
		
		List<Object> valoresTicketMedio = new ArrayList<>();
		List<Object> valores2TicketMedio = new ArrayList<>();
		List<Object> valores3TicketMedio = new ArrayList<>();
		List<Object> valores4TicketMedio = new ArrayList<>();
      
		// CLIENTES!!!!
		// VALORES EM % FILIAL01
		for(IndicadoresCliente indicadoresCliente:this.indicadoresClienteDAO.listar12Ultimos()) {
			valoresClientes.add(indicadoresCliente.getFilial01());
		}
		dataSetClientes.setData(valoresClientes);
		dataSetClientes.setFill(false);
		dataSetClientes.setLabel("LOJA 67");
		dataSetClientes.setBorderColor("rgb(0, 83, 91)");
		dataSetClientes.setTension(0.3);
		graficoDadosClientes.addChartDataSet(dataSetClientes);

		// VALORES EM % FILIAL02
		for(IndicadoresCliente indicadoresCliente:this.indicadoresClienteDAO.listar12Ultimos()) {
			valores2Clientes.add(indicadoresCliente.getFilial02());
		}
		dataSet2Clientes.setData(valores2Clientes);
		dataSet2Clientes.setFill(false);
		dataSet2Clientes.setLabel("LOJA 68");
		dataSet2Clientes.setBorderColor("rgb(0, 138, 151)");
		dataSet2Clientes.setTension(0.3);
		graficoDadosClientes.addChartDataSet(dataSet2Clientes);

		// VALORES EM % FILIAL03
		for(IndicadoresCliente indicadoresCliente:this.indicadoresClienteDAO.listar12Ultimos()) {
			valores3Clientes.add(indicadoresCliente.getFilial03());
		}
		dataSet3Clientes.setData(valores3Clientes);
		dataSet3Clientes.setFill(false);
		dataSet3Clientes.setLabel("LOJA 70");
		dataSet3Clientes.setBorderColor("rgb(102, 185, 193)");
		dataSet3Clientes.setTension(0.3);
		graficoDadosClientes.addChartDataSet(dataSet3Clientes);
		       
		// VALORES EM % GRUPO
		for(IndicadoresCliente indicadoresCliente:this.indicadoresClienteDAO.listar12Ultimos()) {
			valores4Clientes.add(indicadoresCliente.getGrupo());
		}
		dataSet4Clientes.setData(valores4Clientes);
		dataSet4Clientes.setFill(false);
		dataSet4Clientes.setLabel("GRUPO");
		dataSet4Clientes.setBorderColor("rgb(204, 232, 234)");
		dataSet4Clientes.setTension(0.3);
		graficoDadosClientes.addChartDataSet(dataSet4Clientes);
       
		
		// DESCONTOS!!!!
		// VALORES EM % FILIAL01
		for(IndicadoresDesconto indicadoresDesconto:this.indicadoresDescontoDAO.listar12Ultimos()) {
			valoresDescontos.add(indicadoresDesconto.getFilial01());
		}
		dataSetDescontos.setData(valoresDescontos);
		dataSetDescontos.setFill(false);
		dataSetDescontos.setLabel("LOJA 67");
		dataSetDescontos.setBorderColor("rgb(0, 83, 91)");
		dataSetDescontos.setTension(0.3);
		graficoDadosDescontos.addChartDataSet(dataSetDescontos);

		// VALORES EM % FILIAL02
		for(IndicadoresDesconto indicadoresDesconto:this.indicadoresDescontoDAO.listar12Ultimos()) {
			valores2Descontos.add(indicadoresDesconto.getFilial02());
		}
		dataSet2Descontos.setData(valores2Descontos);
		dataSet2Descontos.setFill(false);
		dataSet2Descontos.setLabel("LOJA 68");
		dataSet2Descontos.setBorderColor("rgb(0, 138, 151)");
		dataSet2Descontos.setTension(0.3);
		graficoDadosDescontos.addChartDataSet(dataSet2Descontos);

		// VALORES EM % FILIAL03
		for(IndicadoresDesconto indicadoresDesconto:this.indicadoresDescontoDAO.listar12Ultimos()) {
			valores3Descontos.add(indicadoresDesconto.getFilial03());
		}
		dataSet3Descontos.setData(valores3Descontos);
		dataSet3Descontos.setFill(false);
		dataSet3Descontos.setLabel("LOJA 70");
		dataSet3Descontos.setBorderColor("rgb(102, 185, 193)");
		dataSet3Descontos.setTension(0.3);
		graficoDadosDescontos.addChartDataSet(dataSet3Descontos);
				       
		// VALORES EM % GRUPO
		for(IndicadoresDesconto indicadoresDesconto:this.indicadoresDescontoDAO.listar12Ultimos()) {
			valores4Descontos.add(indicadoresDesconto.getGrupo());
		}
		dataSet4Descontos.setData(valores4Descontos);
		dataSet4Descontos.setFill(false);
		dataSet4Descontos.setLabel("GRUPO");
		dataSet4Descontos.setBorderColor("rgb(204, 232, 234)");
		dataSet4Descontos.setTension(0.3);
		graficoDadosDescontos.addChartDataSet(dataSet4Descontos);
		 
		// TICKET MÉDIO!!!!
		// VALORES EM % FILIAL01
		for(IndicadoresTicketMedio indicadoresTicketMedio:this.indicadoresTicketMedioDAO.listar12Ultimos()) {
			valoresTicketMedio.add(indicadoresTicketMedio.getFilial01());
		}
		dataSetTicketMedio.setData(valoresTicketMedio);
		dataSetTicketMedio.setFill(false);
		dataSetTicketMedio.setLabel("LOJA 67");
		dataSetTicketMedio.setBorderColor("rgb(0, 83, 91)");
		dataSetTicketMedio.setTension(0.3);
		graficoDadosTicketMedios.addChartDataSet(dataSetTicketMedio);

		// VALORES EM % FILIAL02
		for(IndicadoresTicketMedio indicadoresTicketMedio:this.indicadoresTicketMedioDAO.listar12Ultimos()) {
			valores2TicketMedio.add(indicadoresTicketMedio.getFilial02());
		}
		dataSet2TicketMedio.setData(valores2TicketMedio);
		dataSet2TicketMedio.setFill(false);
		dataSet2TicketMedio.setLabel("LOJA 68");
		dataSet2TicketMedio.setBorderColor("rgb(0, 138, 151)");
		dataSet2TicketMedio.setTension(0.3);
		graficoDadosTicketMedios.addChartDataSet(dataSet2TicketMedio);

		// VALORES EM % FILIAL03
		for(IndicadoresTicketMedio indicadoresTicketMedio:this.indicadoresTicketMedioDAO.listar12Ultimos()) {
			valores3TicketMedio.add(indicadoresTicketMedio.getFilial03());
		}
		dataSet3TicketMedio.setData(valores3TicketMedio);
		dataSet3TicketMedio.setFill(false);
		dataSet3TicketMedio.setLabel("LOJA 70");
		dataSet3TicketMedio.setBorderColor("rgb(102, 185, 193)");
		dataSet3TicketMedio.setTension(0.3);
		graficoDadosTicketMedios.addChartDataSet(dataSet3TicketMedio);
						       
		// VALORES EM % GRUPO
		for(IndicadoresTicketMedio indicadoresTicketMedio:this.indicadoresTicketMedioDAO.listar12Ultimos()) {
			valores4TicketMedio.add(indicadoresTicketMedio.getGrupo());
		}
		dataSet4TicketMedio.setData(valores4TicketMedio);
		dataSet4TicketMedio.setFill(false);
		dataSet4TicketMedio.setLabel("GRUPO");
		dataSet4TicketMedio.setBorderColor("rgb(204, 232, 234)");
		dataSet4TicketMedio.setTension(0.3);
		graficoDadosTicketMedios.addChartDataSet(dataSet4TicketMedio);

		List<String> labelsClientes = new ArrayList<>();
		// LISTAGEM DOS MESES CLIENTES
		for(IndicadoresCliente indicadoresCliente:this.indicadoresClienteDAO.listar12Ultimos()) {
			labelsClientes.add(indicadoresCliente.getMes().substring(0,3) + "/" + indicadoresCliente.getAno().toString().substring(2,4));
		}
		graficoDadosClientes.setLabels(labelsClientes);
		
		List<String> labelsDescontos = new ArrayList<>();
		// LISTAGEM DOS MESES DESCONTOS
		for(IndicadoresDesconto indicadoresDesconto:this.indicadoresDescontoDAO.listar12Ultimos()) {
			labelsDescontos.add(indicadoresDesconto.getMes().substring(0,3) + "/" + indicadoresDesconto.getAno().toString().substring(2,4));
		}
		graficoDadosDescontos.setLabels(labelsDescontos);
		
		List<String> labelsTicketMedio = new ArrayList<>();
		// LISTAGEM DOS MESES TICKET MÉDIO
		for(IndicadoresTicketMedio indicadoresTicketMedio:this.indicadoresTicketMedioDAO.listar12Ultimos()) {
			labelsTicketMedio.add(indicadoresTicketMedio.getMes().substring(0,3) + "/" + indicadoresTicketMedio.getAno().toString().substring(2,4));
		}
		graficoDadosTicketMedios.setLabels(labelsTicketMedio);
            		
		LineChartOptions opcoesClientes = new LineChartOptions();

		opcoesClientes.setMaintainAspectRatio(false);
		Title tituloClientes = new Title();
		tituloClientes.setDisplay(true);
//     	  tituloClientes.setText("clientes");
		opcoesClientes.setTitle(tituloClientes);

//       Title subtitle = new Title();
//       subtitle.setDisplay(true);
//       subtitle.setText("Line Chart Subtitle");
//       options.setSubtitle(subtitle);

		graficoLinhaClientes.setOptions(opcoesClientes);
		graficoLinhaClientes.setData(graficoDadosClientes);
		
		
		LineChartOptions opcoesDescontos = new LineChartOptions();

		opcoesDescontos.setMaintainAspectRatio(false);
		Title tituloDescontos = new Title();
		tituloDescontos.setDisplay(true);
//     	  tituloDescontos.setText("descontos");
		opcoesDescontos.setTitle(tituloDescontos);

//       Title subtitle = new Title();
//       subtitle.setDisplay(true);
//       subtitle.setText("Line Chart Subtitle");
//       options.setSubtitle(subtitle);

		graficoLinhaDescontos.setOptions(opcoesDescontos);
		graficoLinhaDescontos.setData(graficoDadosDescontos);
		
		LineChartOptions opcoesTicketMedio = new LineChartOptions();

		opcoesTicketMedio.setMaintainAspectRatio(false);
		Title tituloTicketMedio = new Title();
		tituloTicketMedio.setDisplay(true);
//     	  tituloTicketMedio.setText("TicketMedioescontos");
		opcoesTicketMedio.setTitle(tituloTicketMedio);

//       Title subtitle = new Title();
//       subtitle.setDisplay(true);
//       subtitle.setText("Line Chart Subtitle");
//       options.setSubtitle(subtitle);

		graficoLinhaTicketMedios.setOptions(opcoesTicketMedio);
		graficoLinhaTicketMedios.setData(graficoDadosTicketMedios);
	
		Legend legendClientes = new Legend();
		legendClientes.setDisplay(true);
//        legendClientes.setPosition("right");

        LegendLabel legendLabelsClientes = new LegendLabel();
        legendLabelsClientes.setFontStyle("bold");
        legendLabelsClientes.setFontColor("#2980B9");
        legendLabelsClientes.setFontSize(9);
        legendClientes.setLabels(legendLabelsClientes);
        opcoesClientes.setLegend(legendClientes);
		
		
		Legend legendDescontos = new Legend();
		legendDescontos.setDisplay(true);
//        legendDescontos.setPosition("right");

        LegendLabel legendLabelsDescontos = new LegendLabel();
        legendLabelsDescontos.setFontStyle("bold");
        legendLabelsDescontos.setFontColor("#2980B9");
        legendLabelsDescontos.setFontSize(9);
        legendDescontos.setLabels(legendLabelsDescontos);
        opcoesDescontos.setLegend(legendDescontos);
        
        Legend legendTicketMedio = new Legend();
		legendTicketMedio.setDisplay(true);
//        legendTicketMedio.setPosition("right");

        LegendLabel legendLabelsTicketMedio = new LegendLabel();
        legendLabelsTicketMedio.setFontStyle("bold");
        legendLabelsTicketMedio.setFontColor("#2980B9");
        legendLabelsTicketMedio.setFontSize(9);
        legendTicketMedio.setLabels(legendLabelsTicketMedio);
        opcoesTicketMedio.setLegend(legendTicketMedio);

	}
	
	@SuppressWarnings("unchecked")
	public void createLinhaAnaliseGrupo() {
		graficoLinhaAnaliseGrupo = new LineChartModel();;     
		ChartData graficoDadosAnaliseGrupo = new ChartData();
		
        Integer indice = 0;
             
        listaAnaliseGrupo = analiseGrupoDAO.listar();          
        //INSTANCIANDO OBJETOS DINAMICAMENTE
        LineChartDataSet[] dataSet = new LineChartDataSet[listaAnaliseGrupo.size()];
        for (int i = 0; i < dataSet.length; ++i) {
        	dataSet[i] = new LineChartDataSet();
        }
        
        @SuppressWarnings("rawtypes")
		List<List> valoresAnaliseGrupo = new ArrayList<>(listaAnaliseGrupo.size());
        for (int i = 0; i < listaAnaliseGrupo.size(); ++i) {
        	valoresAnaliseGrupo.add(new ArrayList<String>());
        }    
  
        for(AnaliseGrupo analiseGrupo:this.analiseGrupoDAO.listar()) {  	
        	//COMPARA SE TEM VALOR ZERADO PARA NÃO APARECER NO GRÁFICO        	
        	if (analiseGrupo.getJaneiro().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getJaneiro());
        	}
        	
        	if (analiseGrupo.getFevereiro().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getFevereiro());
        	}
        	
        	if (analiseGrupo.getMarco().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getMarco());
        	}
        	
        	if (analiseGrupo.getAbril().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getAbril());
        	}
        	
        	if (analiseGrupo.getMaio().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getMaio());
        	}
        	
        	if (analiseGrupo.getJunho().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getJunho());
        	}
        	
        	if (analiseGrupo.getJulho().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getJulho());
        	}
        	
        	if (analiseGrupo.getAgosto().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getAgosto());
        	}
        	
        	if (analiseGrupo.getSetembro().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getSetembro());
        	}
        	
        	if (analiseGrupo.getOutubro().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getOutubro());
        	}
        	
        	if (analiseGrupo.getNovembro().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getNovembro());
        	}
        	
        	if (analiseGrupo.getDezembro().compareTo(new BigDecimal(0)) > 0) {
        		valoresAnaliseGrupo.get(indice).add(analiseGrupo.getDezembro());
        	}
        	
        	dataSet[indice].setData(valoresAnaliseGrupo.get(indice));
        	dataSet[indice].setFill(false);
        	dataSet[indice].setLabel(analiseGrupo.getAno().toString());
        	
        	switch (indice) {
	        	case 0:
	              dataSet[indice].setBorderColor("rgb(255, 205, 86)");
	              break;
	            case 1:
	            	dataSet[indice].setBorderColor("rgb(70,  207,  0)");
	              break;
	            case 2:
	            	dataSet[indice].setBorderColor("rgb(54, 162, 235)");
	              break;
	            case 3:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 4:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 5:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 6:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 7:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 8:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 9:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            default:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	          }

        	dataSet[indice].setTension(0.2);
            graficoDadosAnaliseGrupo.addChartDataSet(dataSet[indice]);     
            
            indice +=1;
     	}        
     
        List<String> labelsAnaliseGrupo = new ArrayList<>();
        labelsAnaliseGrupo.add("JANEIRO");
        labelsAnaliseGrupo.add("FEVEREIRO");
        labelsAnaliseGrupo.add("MARÇO");
        labelsAnaliseGrupo.add("ABRIL");
        labelsAnaliseGrupo.add("MAIO");
        labelsAnaliseGrupo.add("JUNHO");
        labelsAnaliseGrupo.add("JULHO");
        labelsAnaliseGrupo.add("AGOSTO");
        labelsAnaliseGrupo.add("SETEMBRO");
        labelsAnaliseGrupo.add("OUTUBRO");
        labelsAnaliseGrupo.add("NOVEMBRO");
        labelsAnaliseGrupo.add("DEZEMBRO");
        graficoDadosAnaliseGrupo.setLabels(labelsAnaliseGrupo);
        
        //Options
        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);
        Title title = new Title();
        title.setDisplay(true);
        title.setText("HISTÓRICO ANUAL - GRUPO");
        options.setTitle(title);

//        Title subtitle = new Title();
//        subtitle.setDisplay(true);
//        subtitle.setText("Line Chart Subtitle");
//        options.setSubtitle(subtitle);

        graficoLinhaAnaliseGrupo.setOptions(options);
        graficoLinhaAnaliseGrupo.setData(graficoDadosAnaliseGrupo);
        
    }
	
	@SuppressWarnings("unchecked")
	public void createLinhaAnaliseRepresentante() {
		graficoLinhaAnaliseRepresentante = new LineChartModel();;     
		ChartData graficoDadosAnaliseRepresentante = new ChartData();
		
        Integer indice = 0;
             
        listaAnaliseRepresentante = analiseRepresentanteDAO.listar();          
        //INSTANCIANDO OBJETOS DINAMICAMENTE
        LineChartDataSet[] dataSet = new LineChartDataSet[listaAnaliseRepresentante.size()];
        for (int i = 0; i < dataSet.length; ++i) {
        	dataSet[i] = new LineChartDataSet();
        }
        
        @SuppressWarnings("rawtypes")
		List<List> valoresAnaliseRepresentante = new ArrayList<>(listaAnaliseRepresentante.size());
        for (int i = 0; i < listaAnaliseRepresentante.size(); ++i) {
        	valoresAnaliseRepresentante.add(new ArrayList<String>());
        }    
  
        for(AnaliseRepresentante analiseRepresentante:this.analiseRepresentanteDAO.listar()) {  	
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getJaneiro());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getFevereiro());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getMarco());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getAbril());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getMaio());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getJunho());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getJulho());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getAgosto());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getSetembro());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getOutubro());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getNovembro());
        	valoresAnaliseRepresentante.get(indice).add(analiseRepresentante.getDezembro());
        	
        	dataSet[indice].setData(valoresAnaliseRepresentante.get(indice));
        	dataSet[indice].setFill(false);
        	dataSet[indice].setLabel(analiseRepresentante.getAno().toString());
        	
        	switch (indice) {
	        	case 0:
	              dataSet[indice].setBorderColor("rgb(255, 205, 86)");
	              break;
	            case 1:
	            	dataSet[indice].setBorderColor("rgb(70,  207,  0)");
	              break;
	            case 2:
	            	dataSet[indice].setBorderColor("rgb(54, 162, 235)");
	              break;
	            case 3:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 4:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 5:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 6:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 7:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 8:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 9:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            default:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	          }

        	dataSet[indice].setTension(0.2);
            graficoDadosAnaliseRepresentante.addChartDataSet(dataSet[indice]);     
            
            indice +=1;
     	}        
     
        List<String> labelsAnaliseRepresentante = new ArrayList<>();
        labelsAnaliseRepresentante.add("JANEIRO");
        labelsAnaliseRepresentante.add("FEVEREIRO");
        labelsAnaliseRepresentante.add("MARÇO");
        labelsAnaliseRepresentante.add("ABRIL");
        labelsAnaliseRepresentante.add("MAIO");
        labelsAnaliseRepresentante.add("JUNHO");
        labelsAnaliseRepresentante.add("JULHO");
        labelsAnaliseRepresentante.add("AGOSTO");
        labelsAnaliseRepresentante.add("SETEMBRO");
        labelsAnaliseRepresentante.add("OUTUBRO");
        labelsAnaliseRepresentante.add("NOVEMBRO");
        labelsAnaliseRepresentante.add("DEZEMBRO");
        graficoDadosAnaliseRepresentante.setLabels(labelsAnaliseRepresentante);
        
        //Options
        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);
        Title title = new Title();
        title.setDisplay(true);
        title.setText("HISTÓRICO ANUAL - REPRESENTANTE");
        options.setTitle(title);

//        Title subtitle = new Title();
//        subtitle.setDisplay(true);
//        subtitle.setText("Line Chart Subtitle");
//        options.setSubtitle(subtitle);

        graficoLinhaAnaliseRepresentante.setOptions(options);
        graficoLinhaAnaliseRepresentante.setData(graficoDadosAnaliseRepresentante);
        
    }
	
	public void createBarraAnaliseRepresentanteGrupo() {		
		graficoBarraAnaliseRepresentanteGrupo = new BarChartModel();
	    ChartData graficoDadosAnaliseRepresentanteGrupo = new ChartData();

	    BarChartDataSet dataSetVendas = new BarChartDataSet();
	    dataSetVendas.setLabel("VENDAS - REPRESENTANTE");

	    List<Number> valoresAnaliseRepresentanteGrupo = new ArrayList<>();
	    for(AnaliseRepresentanteGrupo analiseRepresentanteGrupo:this.analiseRepresentanteGrupoDAO.listar()) {
        	valoresAnaliseRepresentanteGrupo.add(analiseRepresentanteGrupo.getVendas());
		}
	    dataSetVendas.setData(valoresAnaliseRepresentanteGrupo);
	    	 
	    List<String> bgColor = new ArrayList<>();
	    bgColor.add("rgba(255, 99, 132, 0.2)");
	    bgColor.add("rgba(255, 159, 64, 0.2)");
	    bgColor.add("rgba(255, 205, 86, 0.2)");
	    bgColor.add("rgba(75, 192, 192, 0.2)");
	    bgColor.add("rgba(54, 162, 235, 0.2)");
	    bgColor.add("rgba(153, 102, 255, 0.2)");
	    bgColor.add("rgba(201, 203, 207, 0.2)");
	    dataSetVendas.setBackgroundColor(bgColor);

	    List<String> borderColor = new ArrayList<>();
	    borderColor.add("rgb(255, 99, 132)");
	    borderColor.add("rgb(255, 159, 64)");
	    borderColor.add("rgb(255, 205, 86)");
	    borderColor.add("rgb(75, 192, 192)");
	    borderColor.add("rgb(54, 162, 235)");
	    borderColor.add("rgb(153, 102, 255)");
	    borderColor.add("rgb(201, 203, 207)");
	    dataSetVendas.setBorderColor(borderColor);
	    dataSetVendas.setBorderWidth(1);

	    graficoDadosAnaliseRepresentanteGrupo.addChartDataSet(dataSetVendas);
	    
	    // LISTAGEM DOS MESES
	    List<String> labelsMeses = new ArrayList<>(); 
	    for(AnaliseRepresentanteGrupo analiseRepresentanteGrupo:this.analiseRepresentanteGrupoDAO.listar()) {
	    	labelsMeses.add(analiseRepresentanteGrupo.getMes());
		}

		graficoDadosAnaliseRepresentanteGrupo.setLabels(labelsMeses);
		graficoBarraAnaliseRepresentanteGrupo.setData(graficoDadosAnaliseRepresentanteGrupo);

	    //OPÇÕES
	    BarChartOptions opcoes = new BarChartOptions();
	    opcoes.setMaintainAspectRatio(false);
	    CartesianScales cScales = new CartesianScales();
	    CartesianLinearAxes linearAxes = new CartesianLinearAxes();
	    linearAxes.setOffset(true);
	    linearAxes.setBeginAtZero(true);
	    CartesianLinearTicks ticks = new CartesianLinearTicks();
	    linearAxes.setTicks(ticks);
	    cScales.addYAxesData(linearAxes);
	    opcoes.setScales(cScales);

	    Title titulo = new Title();
	    titulo.setDisplay(true);
	    titulo.setText("ANÁLISE GRUPO X REPRESENTANTE");
	    opcoes.setTitle(titulo);

	    Legend legenda = new Legend();
	    legenda.setDisplay(true);
	    legenda.setPosition("top");
	    LegendLabel legendaLabels = new LegendLabel();
	    legendaLabels.setFontStyle("italic");
	    legendaLabels.setFontColor("#2980B9");
	    legendaLabels.setFontSize(24);
	    legenda.setLabels(legendaLabels);
	    opcoes.setLegend(legenda);

	    //ANIMAÇÃO
	    Animation animacao = new Animation();
	    animacao.setDuration(0);
	    opcoes.setAnimation(animacao);

	    graficoBarraAnaliseRepresentanteGrupo.setOptions(opcoes);
	}
	
	@SuppressWarnings("unchecked")
	public void createLinhaCortesia() {
		graficoLinhaCortesia = new LineChartModel();;     
		ChartData graficoDadosCortesia = new ChartData();
		
        Integer indice = 0;
             
        listaCortesia = indicadoresCortesiaDAO.listar();          
        
        //INSTANCIANDO OBJETOS DINAMICAMENTE
        LineChartDataSet[] dataSet = new LineChartDataSet[listaCortesia.size()];
        for (int i = 0; i < dataSet.length; ++i) {
        	dataSet[i] = new LineChartDataSet();
        }
        
        @SuppressWarnings("rawtypes")
		List<List> valoresCortesia = new ArrayList<>(listaCortesia.size());
        for (int i = 0; i < listaCortesia.size(); ++i) {
        	valoresCortesia.add(new ArrayList<String>());
        }    
  
        for(IndicadoresCortesia indicadoresCortesia:this.indicadoresCortesiaDAO.listar()) {  	        	
        	//COMPARA SE TEM VALOR ZERADO PARA NÃO APARECER NO GRÁFICO        	
        	if (indicadoresCortesia.getJaneiro().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getJaneiro());
        	}
        	
        	if (indicadoresCortesia.getFevereiro().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getFevereiro());
        	}
        	
        	if (indicadoresCortesia.getMarco().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getMarco());
        	}
        	
        	if (indicadoresCortesia.getAbril().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getAbril());
        	}
        	
        	if (indicadoresCortesia.getMaio().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getMaio());
        	}
        	
        	if (indicadoresCortesia.getJunho().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getJunho());
        	}
        	
        	if (indicadoresCortesia.getJulho().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getJulho());
        	}
        	
        	if (indicadoresCortesia.getAgosto().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getAgosto());
        	}
        	
        	if (indicadoresCortesia.getSetembro().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getSetembro());
        	}
        	
        	if (indicadoresCortesia.getOutubro().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getOutubro());
        	}
        	
        	if (indicadoresCortesia.getNovembro().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getNovembro());
        	}
        	
        	if (indicadoresCortesia.getDezembro().compareTo(new BigDecimal(0)) > 0) {
        		valoresCortesia.get(indice).add(indicadoresCortesia.getDezembro());
        	}
        	
        	dataSet[indice].setData(valoresCortesia.get(indice));
        	dataSet[indice].setFill(false);
        	dataSet[indice].setLabel(indicadoresCortesia.getAno().toString());
        	
        	switch (indice) {
	        	case 0:
	              dataSet[indice].setBorderColor("rgb(255, 205, 86)");
	              break;
	            case 1:
	            	dataSet[indice].setBorderColor("rgb(70,  207,  0)");
	              break;
	            case 2:
	            	dataSet[indice].setBorderColor("rgb(54, 162, 235)");
	              break;
	            case 3:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 4:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 5:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 6:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 7:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 8:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            case 9:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	              break;
	            default:
	            	dataSet[indice].setBorderColor("rgb(75, 192, 192)");
	          }

        	dataSet[indice].setTension(0.2);
            graficoDadosCortesia.addChartDataSet(dataSet[indice]);     
            
            indice +=1;
     	}        
     
        List<String> labelsCortesia = new ArrayList<>();
        labelsCortesia.add("JANEIRO");
        labelsCortesia.add("FEVEREIRO");
        labelsCortesia.add("MARÇO");
        labelsCortesia.add("ABRIL");
        labelsCortesia.add("MAIO");
        labelsCortesia.add("JUNHO");
        labelsCortesia.add("JULHO");
        labelsCortesia.add("AGOSTO");
        labelsCortesia.add("SETEMBRO");
        labelsCortesia.add("OUTUBRO");
        labelsCortesia.add("NOVEMBRO");
        labelsCortesia.add("DEZEMBRO");
        graficoDadosCortesia.setLabels(labelsCortesia);
        
        //Options
        LineChartOptions options = new LineChartOptions();
        options.setMaintainAspectRatio(false);
        Title title = new Title();
        title.setDisplay(true);
        title.setText("HISTÓRICO ANUAL - CORTESIA");
        options.setTitle(title);

//        Title subtitle = new Title();
//        subtitle.setDisplay(true);
//        subtitle.setText("Line Chart Subtitle");
//        options.setSubtitle(subtitle);

        graficoLinhaCortesia.setOptions(options);
        graficoLinhaCortesia.setData(graficoDadosCortesia);
        
    }
}