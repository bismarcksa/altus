package br.com.aformula.grafico;


import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.donut.DonutChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import br.com.aformula.bean.DashboardBean;
import br.com.aformula.dao.FechamentoProducaoDAO;
import br.com.aformula.domain.FechamentoProducao;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Dashboard implements Serializable {

	DashboardBean dashboardBean = new DashboardBean();
	DashboardBean dashboardBean2 = new DashboardBean();
	
	FechamentoProducao fechamentoProducao = new FechamentoProducao();
	FechamentoProducaoDAO fechamentoProducaoDAO = new FechamentoProducaoDAO();
	
	private DonutChartModel modeloRosquina;
	private BarChartModel modeloBarraEmpilhada;
	private BarChartModel modeloBarraEmpilhadaContratoDia;
	private BarChartModel modeloBarraEmpilhadaProducaoDia;
	
	@PostConstruct
	public void init() {
		createDonutModel();
		createStackedBarModel();
		createBarModel();
		createBarModel2();
	}

	public DonutChartModel getModeloRosquina() {
		return modeloRosquina;
	}

	public void setModeloRosquina(DonutChartModel modeloRosquina) {
		this.modeloRosquina = modeloRosquina;
	}

	public BarChartModel getModeloBarraEmpilhada() {
		return modeloBarraEmpilhada;
	}

	public void setModeloBarraEmpilhada(BarChartModel modeloBarraEmpilhada) {
		this.modeloBarraEmpilhada = modeloBarraEmpilhada;
	}
	
	public BarChartModel getModeloBarraEmpilhadaContratoDia() {
		return modeloBarraEmpilhadaContratoDia;
	}

	public void setModeloBarraEmpilhadaContratoDia(BarChartModel modeloBarraEmpilhadaContratoDia) {
		this.modeloBarraEmpilhadaContratoDia = modeloBarraEmpilhadaContratoDia;
	}

	public BarChartModel getModeloBarraEmpilhadaProducaoDia() {
		return modeloBarraEmpilhadaProducaoDia;
	}

	public void setModeloBarraEmpilhadaProducaoDia(BarChartModel modeloBarraEmpilhadaProducaoDia) {
		this.modeloBarraEmpilhadaProducaoDia = modeloBarraEmpilhadaProducaoDia;
	}

	public void createDonutModel() {	
		dashboardBean.carregarTotalProducao();
		
		modeloRosquina = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        options.setMaintainAspectRatio(false);
        modeloRosquina.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(dashboardBean.getTotalContratoSache());
        values.add(dashboardBean.getTotalContratoCapsula());
        values.add(dashboardBean.getTotalContratoShake());
        values.add(dashboardBean.getTotalContratoEnvase());
        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(70,  207,  0)");
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("SACHÊ");
        labels.add("CÁPSULA");
        labels.add("SHAKE");
        labels.add("ENVASE");
        data.setLabels(labels);
        
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("right");

        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#ffffff");
//        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
        
//        Title title = new Title();
//	    title.setDisplay(true);
//	    title.setText("CONTRATOS - PRODUÇÃO TOTAL ATUAL");
//	    options.setTitle(title);
        
        modeloRosquina.setData(data);
    }
	
	
	public void createStackedBarModel() {	
		modeloBarraEmpilhada = new BarChartModel();
	    ChartData graficoDados = new ChartData();
	    
	    BarChartDataSet DataSet = new BarChartDataSet();
	    DataSet.setLabel("SACHÊ");
	    DataSet.setBackgroundColor("rgb(70,  207,  0)");
	    List<Number> dataVal = new ArrayList<>();  
	    for(FechamentoProducao fechamentoProducao:this.fechamentoProducaoDAO.listarMesFechado()) {
			dataVal.add(fechamentoProducao.getTotal_sache());
		}
	    DataSet.setData(dataVal);

	    BarChartDataSet DataSet2 = new BarChartDataSet();
	    DataSet2.setLabel("CÁPSULA");
	    DataSet2.setBackgroundColor("rgb(255, 99, 132)");
	    List<Number> dataVal2 = new ArrayList<>();
	    for(FechamentoProducao fechamentoProducao:this.fechamentoProducaoDAO.listarMesFechado()) {
			dataVal2.add(fechamentoProducao.getTotal_capsula());
		}
	    DataSet2.setData(dataVal2);

	    BarChartDataSet DataSet3 = new BarChartDataSet();
	    DataSet3.setLabel("SHAKE");
	    DataSet3.setBackgroundColor("rgb(54, 162, 235)");
	    List<Number> dataVal3 = new ArrayList<>();
	    for(FechamentoProducao fechamentoProducao:this.fechamentoProducaoDAO.listarMesFechado()) {
			dataVal3.add(fechamentoProducao.getTotal_shake());
		}
	    DataSet3.setData(dataVal3);
	    
	    BarChartDataSet DataSet4 = new BarChartDataSet();
	    DataSet4.setLabel("ENVASE");
	    DataSet4.setBackgroundColor("rgb(255, 205, 86)");
	    List<Number> dataVal4 = new ArrayList<>();
	    for(FechamentoProducao fechamentoProducao:this.fechamentoProducaoDAO.listarMesFechado()) {
			dataVal4.add(fechamentoProducao.getTotal_envase());
		}
	    DataSet4.setData(dataVal4);

	    graficoDados.addChartDataSet(DataSet);
	    graficoDados.addChartDataSet(DataSet2);
	    graficoDados.addChartDataSet(DataSet3);
	    graficoDados.addChartDataSet(DataSet4);

	    // LISTAGEM DOS MESES 
	    List<String> labelsMeses = new ArrayList<>();
	    for(FechamentoProducao fechamentoProducao:this.fechamentoProducaoDAO.listarMesFechado()) {
	 		labelsMeses.add(fechamentoProducao.getMes()+"/"+fechamentoProducao.getAno());
	 	}
	 	graficoDados.setLabels(labelsMeses);
	   
	    modeloBarraEmpilhada.setData(graficoDados);

	    //Options
	    BarChartOptions options = new BarChartOptions();
	    options.setMaintainAspectRatio(false);
	    CartesianScales cScales = new CartesianScales();
	    CartesianLinearAxes linearAxes = new CartesianLinearAxes();

	    linearAxes.setStacked(true);
	    linearAxes.setOffset(true);
	    linearAxes.setMin(0);
	    cScales.addXAxesData(linearAxes);
	    cScales.addYAxesData(linearAxes);
	    options.setScales(cScales);
	    
	    
	    Title title = new Title();
	    title.setDisplay(true);
//	    title.setText("Bar Chart - Stacked");
	    options.setTitle(title);
	    
	    Tooltip tooltip = new Tooltip();
	    tooltip.setMode("index");
	    tooltip.setIntersect(false);
	    options.setTooltip(tooltip);

	    Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
//        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

	    modeloBarraEmpilhada.setOptions(options);
	    
	}
	
	private void createBarModel() {
		dashboardBean.carregarTotalProducao();
	    
	    modeloBarraEmpilhadaContratoDia = new BarChartModel();
	    ChartData graficoDadosContratoDia = new ChartData();
	    
	    BarChartDataSet DataSetContratoDia = new BarChartDataSet();
	    DataSetContratoDia.setLabel("SACHÊ");
	    DataSetContratoDia.setBackgroundColor("rgb(0, 138, 151)");
	    List<Number> dataValContratoDia = new ArrayList<>();  

	    for(int i = 0; i < dashboardBean.getTotalContratoSacheDia().size(); i++) {
			dataValContratoDia.add(dashboardBean.getTotalContratoSacheDia().get(i).getTotalProducaoDia());
		}
	    DataSetContratoDia.setData(dataValContratoDia);
	    graficoDadosContratoDia.addChartDataSet(DataSetContratoDia);
	   
	    
	    BarChartDataSet DataSetContratoDia2 = new BarChartDataSet();
	    DataSetContratoDia2.setLabel("CÁPS ÓLEOSA");
	    DataSetContratoDia2.setBackgroundColor("rgb(0, 138, 151)");
	    List<Number> dataValContratoDia2 = new ArrayList<>();  
	    
	    for(int i = 0; i < dashboardBean.getTotalContratoCapsulaDia().size(); i++) {
			dataValContratoDia2.add(dashboardBean.getTotalContratoCapsulaDia().get(i).getTotalProducaoDia());
		}
	    DataSetContratoDia2.setData(dataValContratoDia2);
	    graficoDadosContratoDia.addChartDataSet(DataSetContratoDia2);
	    
	    
	    BarChartDataSet DataSetContratoDia3 = new BarChartDataSet();
	    DataSetContratoDia3.setLabel("SHAKE");
	    DataSetContratoDia3.setBackgroundColor("rgb(0, 138, 151)");
	    List<Number> dataValContratoDia3 = new ArrayList<>();  
	    
	    for(int i = 0; i < dashboardBean.getTotalContratoShakeDia().size(); i++) {
			dataValContratoDia3.add(dashboardBean.getTotalContratoShakeDia().get(i).getTotalProducaoDia());
		}
	    DataSetContratoDia3.setData(dataValContratoDia3);
	    graficoDadosContratoDia.addChartDataSet(DataSetContratoDia3);
	    
	    
	    BarChartDataSet DataSetContratoDia4 = new BarChartDataSet();
	    DataSetContratoDia4.setLabel("ENVASE");
	    DataSetContratoDia4.setBackgroundColor("rgb(0, 138, 151)");
	    List<Number> dataValContratoDia4 = new ArrayList<>();  
	    
	    for(int i = 0; i < dashboardBean.getTotalContratoEnvaseDia().size(); i++) {
			dataValContratoDia4.add(dashboardBean.getTotalContratoEnvaseDia().get(i).getTotalProducaoDia());
		}
	    DataSetContratoDia4.setData(dataValContratoDia4);
	    graficoDadosContratoDia.addChartDataSet(DataSetContratoDia4);

	    
	    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM"); 
	    // LISTAGEM DOS DIAS 
	    List<String> labelsDias = new ArrayList<>();  
	    for(int i = 0; i < dashboardBean.getTotalContratoSacheDia().size(); i++) {
	    	String dataFormatada = formatador.format(dashboardBean.getTotalContratoSacheDia().get(i).getData_dispensacao()); 
	    	labelsDias.add(dataFormatada);
		}
	   
	 	graficoDadosContratoDia.setLabels(labelsDias);
	   
	 	modeloBarraEmpilhadaContratoDia.setData(graficoDadosContratoDia);

	 	
	    //Options
	    BarChartOptions optionsContratoDia = new BarChartOptions();
	    optionsContratoDia.setMaintainAspectRatio(false);
	    CartesianScales cScalesContratoDia = new CartesianScales();
	    CartesianLinearAxes linearAxesContratoDia = new CartesianLinearAxes();

	    linearAxesContratoDia.setStacked(true);
	    linearAxesContratoDia.setOffset(true);
	    linearAxesContratoDia.setMin(0);
	    CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxesContratoDia.setTicks(ticks);
	    cScalesContratoDia.addXAxesData(linearAxesContratoDia);
	    cScalesContratoDia.addYAxesData(linearAxesContratoDia);
	    optionsContratoDia.setScales(cScalesContratoDia);
	    
	    
	    Title titleContratoDia = new Title();
	    titleContratoDia.setDisplay(true);
	    titleContratoDia.setText("CONTRATOS - PRODUÇÃO DIÁRIA");
	    optionsContratoDia.setTitle(titleContratoDia);
	    
	    Tooltip tooltipContratoDia = new Tooltip();
	    tooltipContratoDia.setMode("index");
	    tooltipContratoDia.setIntersect(false);
	    optionsContratoDia.setTooltip(tooltipContratoDia);

	    Legend legendContratoDia = new Legend();
        legendContratoDia.setDisplay(true);
        legendContratoDia.setPosition("right");
        
        LegendLabel legendLabelsContratoDia = new LegendLabel();
        legendLabelsContratoDia.setFontStyle("bold");
        legendLabelsContratoDia.setFontColor("#2980B9");
//        legendLabelsContratoDia.setFontSize(24);
        legendContratoDia.setLabels(legendLabelsContratoDia);
        optionsContratoDia.setLegend(legendContratoDia);

	    modeloBarraEmpilhadaContratoDia.setOptions(optionsContratoDia);
	       
	}
	
	private void createBarModel2() {  
		dashboardBean2.carregarTotalProducao();
		
		modeloBarraEmpilhadaProducaoDia = new BarChartModel();
	    ChartData graficoDadosProducaoDia = new ChartData();
	    
	    BarChartDataSet DataSetProducaoDia = new BarChartDataSet();
	    DataSetProducaoDia.setLabel("SACHÊ");
	    DataSetProducaoDia.setBackgroundColor("rgb(0, 138, 151)");
	    List<Number> dataValProducaoDia = new ArrayList<>();  

	    for(int i = 0; i < dashboardBean2.getTotalProducaoSacheDia().size(); i++) {
			dataValProducaoDia.add(dashboardBean2.getTotalProducaoSacheDia().get(i).getTotalProducaoDia());
		}
	    DataSetProducaoDia.setData(dataValProducaoDia);

	    graficoDadosProducaoDia.addChartDataSet(DataSetProducaoDia);
	       
	    BarChartDataSet DataSetProducaoDia2 = new BarChartDataSet();
	    DataSetProducaoDia2.setLabel("CÁPS ÓLEOSA");
	    DataSetProducaoDia2.setBackgroundColor("rgb(0, 138, 151)");
	    List<Number> dataValProducaoDia2 = new ArrayList<>();  
	    
	    for(int i = 0; i < dashboardBean2.getTotalProducaoCapsulaDia().size(); i++) {
			dataValProducaoDia2.add(dashboardBean2.getTotalProducaoCapsulaDia().get(i).getTotalProducaoDia());
		}
	    DataSetProducaoDia2.setData(dataValProducaoDia2);

	    graficoDadosProducaoDia.addChartDataSet(DataSetProducaoDia2);
	    
	    
	    SimpleDateFormat formatador2 = new SimpleDateFormat("dd/MM"); 
	    // LISTAGEM DOS DIAS 
	    List<String> labelsDias2 = new ArrayList<>();
	    for(int i = 0; i < dashboardBean2.getTotalProducaoCapsulaDia().size(); i++) {
			String dataFormatada2 = formatador2.format(dashboardBean2.getTotalProducaoCapsulaDia().get(i).getData_dispensacao()); 
			labelsDias2.add(dataFormatada2);
		}	    
	    
	    boolean contem = false; // verifica se existe um valor de x no vetor y
	    
	    for(int i = 0; i < dashboardBean2.getTotalProducaoSacheDia().size(); i++) {
	    	contem = false;
	    	for (int j = 0; j < labelsDias2.size(); j++) {
	    		if (formatador2.format(dashboardBean2.getTotalProducaoSacheDia().get(i).getData_dispensacao()).toString().equals(labelsDias2.get(j).toString())) {
	    			contem = true;
	    			break;
	    		}
	    	}
	    	
	    	if (!contem) {
	    		String dataFormatada2 = formatador2.format(dashboardBean2.getTotalProducaoSacheDia().get(i).getData_dispensacao()); 
				labelsDias2.add(dataFormatada2); 
	    	}
	    }
	    
	 	graficoDadosProducaoDia.setLabels(labelsDias2);
	   
	 	modeloBarraEmpilhadaProducaoDia.setData(graficoDadosProducaoDia);

	 	
	    //Options
	    BarChartOptions optionsProducaoDia = new BarChartOptions();
	    optionsProducaoDia.setMaintainAspectRatio(false);
	    CartesianScales cScalesProducaoDia = new CartesianScales();
	    CartesianLinearAxes linearAxesProducaoDia = new CartesianLinearAxes();

	    linearAxesProducaoDia.setStacked(true);
	    linearAxesProducaoDia.setOffset(true);
	    linearAxesProducaoDia.setMin(0);
	    CartesianLinearTicks ticks2 = new CartesianLinearTicks();
        linearAxesProducaoDia.setTicks(ticks2);
	    cScalesProducaoDia.addXAxesData(linearAxesProducaoDia);
	    cScalesProducaoDia.addYAxesData(linearAxesProducaoDia);
	    optionsProducaoDia.setScales(cScalesProducaoDia);
	    
	    
	    Title titleProducaoDia = new Title();
	    titleProducaoDia.setDisplay(true);
	    titleProducaoDia.setText("QUANTITATIVO - PRODUÇÃO DIÁRIA");
	    optionsProducaoDia.setTitle(titleProducaoDia);
	    
	    Tooltip tooltipProducaoDia = new Tooltip();
	    tooltipProducaoDia.setMode("index");
	    tooltipProducaoDia.setIntersect(false);
	    optionsProducaoDia.setTooltip(tooltipProducaoDia);

	    Legend legendProducaoDia = new Legend();
        legendProducaoDia.setDisplay(true);
        legendProducaoDia.setPosition("right");
        
        LegendLabel legendLabelsProducaoDia = new LegendLabel();
        legendLabelsProducaoDia.setFontStyle("bold");
        legendLabelsProducaoDia.setFontColor("#2980B9");
//        legendLabelsProducaoDia.setFontSize(24);
        legendProducaoDia.setLabels(legendLabelsProducaoDia);
        optionsProducaoDia.setLegend(legendProducaoDia);

	    modeloBarraEmpilhadaProducaoDia.setOptions(optionsProducaoDia);
	}

}


