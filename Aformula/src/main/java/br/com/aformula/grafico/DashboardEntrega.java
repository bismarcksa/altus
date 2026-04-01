package br.com.aformula.grafico;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import br.com.aformula.bean.DashboardEntregaBean;
import br.com.aformula.dao.DashboardEntregaDAO;
import br.com.aformula.domain.Entrega;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DashboardEntrega implements Serializable {

	DashboardEntregaBean dashboardEntregaBean = new DashboardEntregaBean();
	DashboardEntregaDAO dashboardEntregaDAO = new DashboardEntregaDAO();
	
	private DonutChartModel modeloRosquina;
	private BarChartModel modeloBarraEmpilhadaEntregaDia;	
	private List<Entrega> listagemNomeEntregadores;
	private List<Entrega> totalEntregaDia = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		createDonutModel();
		createBarModel();
	}

	public DonutChartModel getModeloRosquina() {
		return modeloRosquina;
	}

	public void setModeloRosquina(DonutChartModel modeloRosquina) {
		this.modeloRosquina = modeloRosquina;
	}

	public BarChartModel getModeloBarraEmpilhadaEntregaDia() {
		return modeloBarraEmpilhadaEntregaDia;
	}

	public void setModeloBarraEmpilhadaEntregaDia(BarChartModel modeloBarraEmpilhadaEntregaDia) {
		this.modeloBarraEmpilhadaEntregaDia = modeloBarraEmpilhadaEntregaDia;
	}

	public List<Entrega> getListagemNomeEntregadores() {
		return listagemNomeEntregadores;
	}

	public void setListagemNomeEntregadores(List<Entrega> listagemNomeEntregadores) {
		this.listagemNomeEntregadores = listagemNomeEntregadores;
	}

	public List<Entrega> getTotalEntregaDia() {
		return totalEntregaDia;
	}

	public void setTotalEntregaDia(List<Entrega> totalEntregaDia) {
		this.totalEntregaDia = totalEntregaDia;
	}

	public void createDonutModel() {	
		Date data_inicial_mes, data_final_mes;

		Calendar c = Calendar.getInstance();

	    //PRIMEIRO DIA DO MÊS ATUAL
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
	    data_inicial_mes = c.getTime();

	    //ÚLTIMO DIA DO MÊS ATUAL
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	    data_final_mes = c.getTime();	
		
		modeloRosquina = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartOptions options = new DonutChartOptions();
        options.setMaintainAspectRatio(false);
        modeloRosquina.setOptions(options);

        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        for(Entrega entrega:this.dashboardEntregaDAO.listarTotalEntregaEntregador(data_inicial_mes, data_final_mes)) {
			values.add(entrega.getTotalEntregaEntregador());
		}

        dataSet.setData(values);

        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(70,  207,  0)");
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        for(Entrega entrega:this.dashboardEntregaDAO.listarTotalEntregaEntregador(data_inicial_mes, data_final_mes)) {
			labels.add(entrega.getNomeEntregador());
		}
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
        
        modeloRosquina.setData(data);
    }
	
	private void createBarModel() {
		dashboardEntregaBean.carregarTotalEntrega();
	    
		modeloBarraEmpilhadaEntregaDia = new BarChartModel();
	    ChartData graficoDadosEntregaDia = new ChartData();
	    	    
	    //CRIA A LISTA COM OS NOMES DO ENTREGADORES ATIVOS
	    Date data_inicial_mes, data_final_mes;

		Calendar c = Calendar.getInstance();

	    //PRIMEIRO DIA DO MÊS ATUAL
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
	    data_inicial_mes = c.getTime();
	    //ÚLTIMO DIA DO MÊS ATUAL
	    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	    data_final_mes = c.getTime();
	    listagemNomeEntregadores = dashboardEntregaDAO.listarTotalEntregaEntregador(data_inicial_mes, data_final_mes);
	    
	    // CRIA A LISTAGEM DE DATASETS
	    List<BarChartDataSet> dataSets = new ArrayList<>(); 
	    for (int i = 0; i < listagemNomeEntregadores.size(); i++) {
	        BarChartDataSet dataSet = new BarChartDataSet();
	        dataSet.setLabel(listagemNomeEntregadores.get(i).getNomeEntregador());
	        
	


	        if(i == 0){
	        	dataSet.setBackgroundColor("rgb(70,  207,  0)");// vai ter mudar as cores depois em cada volta
	        }else if(i == 1) {
	        	dataSet.setBackgroundColor("rgb(255, 99, 132)");
	        }else if(i == 2) {
	        	dataSet.setBackgroundColor("rgb(54, 162, 235)");
	        }else if(i >= 3) {
	        	dataSet.setBackgroundColor("rgb(255, 205, 86)");
	        }

	        List<Number> dataValEntregaDia = new ArrayList<>();
	        
	        totalEntregaDia = dashboardEntregaDAO.totalEntregaDia(listagemNomeEntregadores.get(i).getCodigoEntregador().intValue(), data_inicial_mes, data_final_mes);		    
	        
	        for(int x = 0; x < totalEntregaDia.size(); x++) {
				dataValEntregaDia.add(totalEntregaDia.get(x).getTotalEntregaEntregador());
			}
		    dataSet.setData(dataValEntregaDia);
		    graficoDadosEntregaDia.addChartDataSet(dataSet);
		    
	        dataSets.add(dataSet);
	    }
	       
	    // LISTAGEM DOS DIAS 
	    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM"); 
	    
	    List<String> labelsDias = new ArrayList<>();  
	    for(int i = 0; i < dashboardEntregaBean.getTotalEntregaDia().size(); i++) {
	    	String dataFormatada = formatador.format(dashboardEntregaBean.getTotalEntregaDia().get(i).getData_lancamento()); 
	    	
	    	//SÓ ADICIONA SE NÃO EXISTIR NA LISTA
	    	if (!labelsDias.contains(dataFormatada)) {
	    		labelsDias.add(dataFormatada);
	    	} 
		}
	   
	 	graficoDadosEntregaDia.setLabels(labelsDias);
	   
	 	modeloBarraEmpilhadaEntregaDia.setData(graficoDadosEntregaDia);

	    //Options
	    BarChartOptions optionsEntregaDia = new BarChartOptions();
	    optionsEntregaDia.setMaintainAspectRatio(false);
	    CartesianScales cScalesEntregaDia = new CartesianScales();
	    CartesianLinearAxes linearAxesEntregaDia = new CartesianLinearAxes();

	    linearAxesEntregaDia.setStacked(true);
	    linearAxesEntregaDia.setOffset(true);
	    linearAxesEntregaDia.setMin(0);
	    CartesianLinearTicks ticks = new CartesianLinearTicks();
        linearAxesEntregaDia.setTicks(ticks);
	    cScalesEntregaDia.addXAxesData(linearAxesEntregaDia);
	    cScalesEntregaDia.addYAxesData(linearAxesEntregaDia);
	    optionsEntregaDia.setScales(cScalesEntregaDia);
	    
	    
	    Title titleEntregaDia = new Title();
	    titleEntregaDia.setDisplay(true);
	    titleEntregaDia.setText("ENTREGA DIÁRIA");
	    optionsEntregaDia.setTitle(titleEntregaDia);
	    
	    Tooltip tooltipEntregaDia = new Tooltip();
	    tooltipEntregaDia.setMode("index");
	    tooltipEntregaDia.setIntersect(false);
	    optionsEntregaDia.setTooltip(tooltipEntregaDia);

	    Legend legendEntregaDia = new Legend();
        legendEntregaDia.setDisplay(true);
        legendEntregaDia.setPosition("right");
        
        LegendLabel legendLabelsEntregaDia = new LegendLabel();
        legendLabelsEntregaDia.setFontStyle("bold");
        legendLabelsEntregaDia.setFontColor("#2980B9");
//        legendLabelsContratoDia.setFontSize(24);
        legendEntregaDia.setLabels(legendLabelsEntregaDia);
        optionsEntregaDia.setLegend(legendEntregaDia);

	    modeloBarraEmpilhadaEntregaDia.setOptions(optionsEntregaDia);
	       
	}
	

}


