package br.com.aformula.grafico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;

import br.com.aformula.bean.GestaoPerformanceBean;
import br.com.aformula.dao.GestaoPerformanceFuncDAO;
import br.com.aformula.domain.GestaoPerformanceFunc;

@ManagedBean
@ViewScoped
public class GraficoPerformanceFunc implements Serializable {
	
	GestaoPerformanceFunc gestaoPerformanceFunc = new GestaoPerformanceFunc();
	GestaoPerformanceFuncDAO gestaoPerformanceFuncDAO = new GestaoPerformanceFuncDAO();
	
	private static final long serialVersionUID = 1L;
	private BarChartModel mixedModelPerformanceFunc;
	private BarChartModel modeloBarraEmpilhada;
	private LineChartModel modeloCartesianoPerformance;
	
	@ManagedProperty(value = "#{gestaoPerformanceBean}")
	private GestaoPerformanceBean gestaoPerformanceBean;
	
	@PostConstruct
	public void init() {
		 createMixedModel();
		 createStackedBarModel();
		 createCartesianLinerModel();
	}

	public BarChartModel getMixedModelPerformanceFunc() {
		return mixedModelPerformanceFunc;
	}

	public void setMixedModelPerformanceFunc(BarChartModel mixedModelPerformanceFunc) {
		this.mixedModelPerformanceFunc = mixedModelPerformanceFunc;
	}

	public GestaoPerformanceBean getGestaoPerformanceBean() {
		return gestaoPerformanceBean;
	}

	public void setGestaoPerformanceBean(GestaoPerformanceBean gestaoPerformanceBean) {
		this.gestaoPerformanceBean = gestaoPerformanceBean;
	}

	public BarChartModel getModeloBarraEmpilhada() {
		return modeloBarraEmpilhada;
	}

	public void setModeloBarraEmpilhada(BarChartModel modeloBarraEmpilhada) {
		this.modeloBarraEmpilhada = modeloBarraEmpilhada;
	}
	
	public LineChartModel getModeloCartesianoPerformance() {
		return modeloCartesianoPerformance;
	}

	public void setModeloCartesianoPerformance(LineChartModel modeloCartesianoPerformance) {
		this.modeloCartesianoPerformance = modeloCartesianoPerformance;
	}

	public void createMixedModel() {
		/* GRÁFICO PERFORMANCE */
		mixedModelPerformanceFunc = new BarChartModel();
        ChartData dataPerformanceFunc = new ChartData();

        BarChartDataSet dataSetPerformanceFunc = new BarChartDataSet();
        List<Number> valoresPerformanceFunc = new ArrayList<>();

        for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
			valoresPerformanceFunc.add(gestaoPerformanceFunc.getVenda());
		}
        dataSetPerformanceFunc.setData(valoresPerformanceFunc);
        dataSetPerformanceFunc.setLabel("VENDAS");
        dataSetPerformanceFunc.setBorderColor("rgb(255, 99, 132)");
        dataSetPerformanceFunc.setBackgroundColor("rgba(0, 138, 151, 0.2)");

        LineChartDataSet dataSetMetasFunc = new LineChartDataSet();
        List<Object> valoresMetasFunc = new ArrayList<>();
        
//        long totalMeta = 0;
        
        // VALORES EM METAS 
     	for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
     		valoresMetasFunc.add(gestaoPerformanceFunc.getMeta());
//     		totalMeta = totalMeta + gestaoPerformanceFunc.getMeta().longValue();
     	}
//     	gestaoPerformanceFunc.setTotalMeta(BigDecimal.valueOf(totalMeta));
     	
        dataSetMetasFunc.setData(valoresMetasFunc);
        dataSetMetasFunc.setLabel("META");
        dataSetMetasFunc.setFill(false);
        dataSetMetasFunc.setBorderColor("rgb(54, 162, 235)");

        dataPerformanceFunc.addChartDataSet(dataSetPerformanceFunc);
        dataPerformanceFunc.addChartDataSet(dataSetMetasFunc);

        List<String> labelsMetasFunc = new ArrayList<>();

        for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
        	labelsMetasFunc.add(gestaoPerformanceFunc.getMes());
		}
        dataPerformanceFunc.setLabels(labelsMetasFunc);

        mixedModelPerformanceFunc.setData(dataPerformanceFunc);

        //Options
        BarChartOptions optionsPerformanceFunc = new BarChartOptions();
        CartesianScales cScalesPerformanceFunc = new CartesianScales();
        CartesianLinearAxes linearAxesPerformanceFunc = new CartesianLinearAxes();
        linearAxesPerformanceFunc.setOffset(true);
        linearAxesPerformanceFunc.setBeginAtZero(true);
        CartesianLinearTicks ticksPerformanceFunc = new CartesianLinearTicks();
        linearAxesPerformanceFunc.setTicks(ticksPerformanceFunc);

        cScalesPerformanceFunc.addYAxesData(linearAxesPerformanceFunc);
        optionsPerformanceFunc.setScales(cScalesPerformanceFunc);
        mixedModelPerformanceFunc.setOptions(optionsPerformanceFunc);
    }

	public void createStackedBarModel() {	
		modeloBarraEmpilhada = new BarChartModel();
	    ChartData graficoDados = new ChartData();
	    
	    LineChartDataSet dataSetMetasFunc = new LineChartDataSet();
        List<Object> valoresMetasFunc = new ArrayList<>();
        
        // VALORES EM METAS 
     	for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
     		// % DA META É SEMPRE 100%
     		valoresMetasFunc.add(100);
     	}
     	
        dataSetMetasFunc.setData(valoresMetasFunc);
        dataSetMetasFunc.setLabel("META");
        dataSetMetasFunc.setFill(false);
        dataSetMetasFunc.setBorderColor("rgb(54, 162, 235)");  
        graficoDados.addChartDataSet(dataSetMetasFunc);
	    
	    
	    BarChartDataSet DataSet = new BarChartDataSet();
	    DataSet.setLabel("META %");
	    DataSet.setBackgroundColor("rgb(70,  207,  0)");
	    List<Number> dataVal = new ArrayList<>();  
	    
	    for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
			dataVal.add(gestaoPerformanceFunc.getMetaPercentual());
		}
	    DataSet.setData(dataVal);

	    BarChartDataSet DataSet2 = new BarChartDataSet();
	    DataSet2.setLabel("REJEIÇÃO %");
	    DataSet2.setBackgroundColor("rgb(255, 99, 132)");
	    List<Number> dataVal2 = new ArrayList<>();
	    for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
			dataVal2.add(gestaoPerformanceFunc.getRejeicao());
		}
	    DataSet2.setData(dataVal2);

	    graficoDados.addChartDataSet(DataSet);
	    graficoDados.addChartDataSet(DataSet2);
	    
	 // LISTAGEM DOS MESES 
	    List<String> labelsMeses = new ArrayList<>();
	    for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
	 		labelsMeses.add(gestaoPerformanceFunc.getMes());
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
	
	private void createCartesianLinerModel() {
		modeloCartesianoPerformance = new LineChartModel();
        ChartData graficoDados = new ChartData();

        LineChartDataSet dataSetMeta = new LineChartDataSet();
        List<Object> valoresMeta = new ArrayList<>(); 
        for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
        	valoresMeta.add(gestaoPerformanceFunc.getMeta());
		}
  
        dataSetMeta.setData(valoresMeta);
        dataSetMeta.setLabel("META");
        dataSetMeta.setYaxisID("left-y-axis");
        dataSetMeta.setBackgroundColor("rgb(54, 162, 235)");
        dataSetMeta.setFill(true);
        dataSetMeta.setTension(0.5);

        LineChartDataSet dataSetMetaPercentual = new LineChartDataSet();
        List<Object> valoresMetaPercentual = new ArrayList<>();
        for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
        	valoresMetaPercentual.add(gestaoPerformanceFunc.getVenda());
		}
        
        dataSetMetaPercentual.setData(valoresMetaPercentual);
        dataSetMetaPercentual.setLabel("ALCANÇADO");
        dataSetMetaPercentual.setYaxisID("right-y-axis");
        dataSetMetaPercentual.setBackgroundColor("rgb(255, 99, 132)");
        dataSetMetaPercentual.setFill(true);
        dataSetMetaPercentual.setTension(0.5);

        graficoDados.addChartDataSet(dataSetMeta);
        graficoDados.addChartDataSet(dataSetMetaPercentual);

        // LISTAGEM DOS MESES 
	    List<String> labelsMeses = new ArrayList<>();
	    for(GestaoPerformanceFunc gestaoPerformanceFunc:this.gestaoPerformanceFuncDAO.listar(gestaoPerformanceBean.gestaoPerformanceCadastro.getFuncionario().getCodigo(), gestaoPerformanceBean.getGestaoPerformanceCadastro().getAno())) {
	 		labelsMeses.add(gestaoPerformanceFunc.getMes());
	 	}
       
        graficoDados.setLabels(labelsMeses);
        modeloCartesianoPerformance.setData(graficoDados);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");
        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("right-y-axis");
        linearAxes2.setPosition("right");

        cScales.addYAxesData(linearAxes);
        cScales.addYAxesData(linearAxes2);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("PRODUTIVIDADE - META X %ALCANÇADO");
        options.setTitle(title);

        modeloCartesianoPerformance.setOptions(options);
		
	}
}