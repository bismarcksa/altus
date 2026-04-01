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
import br.com.aformula.dao.MetasGrupoDAO;
import br.com.aformula.dao.MetasLoja67DAO;
import br.com.aformula.dao.MetasLoja68DAO;
import br.com.aformula.dao.MetasLoja70DAO;
import br.com.aformula.domain.MetasGrupo;
import br.com.aformula.domain.MetasLoja67;
import br.com.aformula.domain.MetasLoja68;
import br.com.aformula.domain.MetasLoja70;

@ManagedBean
@ViewScoped
public class GraficoMetas implements Serializable {
	
	MetasLoja67 metasLoja67 = new MetasLoja67();
	MetasLoja67DAO metasLoja67DAO = new MetasLoja67DAO();
	
	MetasLoja68 metasLoja68 = new MetasLoja68();
	MetasLoja68DAO metasLoja68DAO = new MetasLoja68DAO();
	
	MetasLoja70 metasLoja70 = new MetasLoja70();
	MetasLoja70DAO metasLoja70DAO = new MetasLoja70DAO();
	
	MetasGrupo metasGrupo = new MetasGrupo();
	MetasGrupoDAO metasGrupoDAO = new MetasGrupoDAO();
	
	private static final long serialVersionUID = 1L;
	private BarChartModel mixedModelLoja67;
	private BarChartModel mixedModelLoja68;
	private BarChartModel mixedModelLoja70;
	private BarChartModel mixedModelGrupo;
	
	
	@PostConstruct
	public void init() {
		 createMixedModel();
	}
	
	public BarChartModel getMixedModelLoja67() {
		return mixedModelLoja67;
	}

	public void setMixedModelLoja67(BarChartModel mixedModelLoja67) {
		this.mixedModelLoja67 = mixedModelLoja67;
	}

	public BarChartModel getMixedModelLoja68() {
		return mixedModelLoja68;
	}

	public void setMixedModelLoja68(BarChartModel mixedModelLoja68) {
		this.mixedModelLoja68 = mixedModelLoja68;
	}

	public BarChartModel getMixedModelLoja70() {
		return mixedModelLoja70;
	}

	public void setMixedModelLoja70(BarChartModel mixedModelLoja70) {
		this.mixedModelLoja70 = mixedModelLoja70;
	}

	public BarChartModel getMixedModelGrupo() {
		return mixedModelGrupo;
	}

	public void setMixedModelGrupo(BarChartModel mixedModelGrupo) {
		this.mixedModelGrupo = mixedModelGrupo;
	}

	public void createMixedModel() {
		/* GRÁFICO LOJA 67 */
		mixedModelLoja67 = new BarChartModel();
        ChartData dataLoja67 = new ChartData();

        BarChartDataSet dataSetVendas67 = new BarChartDataSet();
        List<Number> valoresVendas67 = new ArrayList<>();
        
        for(MetasLoja67 metasLoja67:this.metasLoja67DAO.listar()) {
			valoresVendas67.add(metasLoja67.getVendas());
		}
        dataSetVendas67.setData(valoresVendas67);
        dataSetVendas67.setLabel("VENDAS");
        dataSetVendas67.setBorderColor("rgb(255, 99, 132)");
        dataSetVendas67.setBackgroundColor("rgba(0, 138, 151, 0.2)");

        LineChartDataSet dataSetMetas67 = new LineChartDataSet();
        List<Object> valoresMetas67 = new ArrayList<>();
        
        long totalMeta = 0;
        
        // VALORES EM METAS 67
     	for(MetasLoja67 metasLoja67:this.metasLoja67DAO.listar()) {
     		valoresMetas67.add(metasLoja67.getMeta());
     		totalMeta = totalMeta + metasLoja67.getMeta().longValue();
     	}
     	metasLoja67.setTotalMeta(BigDecimal.valueOf(totalMeta));
     	
        dataSetMetas67.setData(valoresMetas67);
        dataSetMetas67.setLabel("META");
        dataSetMetas67.setFill(false);
        dataSetMetas67.setBorderColor("rgb(54, 162, 235)");

        dataLoja67.addChartDataSet(dataSetVendas67);
        dataLoja67.addChartDataSet(dataSetMetas67);

        List<String> labelsMetas67 = new ArrayList<>();

        for(MetasLoja67 metasLoja67:this.metasLoja67DAO.listar()) {
        	labelsMetas67.add(metasLoja67.getMes());
		}
        dataLoja67.setLabels(labelsMetas67);

        mixedModelLoja67.setData(dataLoja67);

        //Options
        BarChartOptions optionsLoja67 = new BarChartOptions();
        CartesianScales cScalesLoja67 = new CartesianScales();
        CartesianLinearAxes linearAxesLoja67 = new CartesianLinearAxes();
        linearAxesLoja67.setOffset(true);
        linearAxesLoja67.setBeginAtZero(true);
        CartesianLinearTicks ticksLoja67 = new CartesianLinearTicks();
        linearAxesLoja67.setTicks(ticksLoja67);

        cScalesLoja67.addYAxesData(linearAxesLoja67);
        optionsLoja67.setScales(cScalesLoja67);
        mixedModelLoja67.setOptions(optionsLoja67);
        
        /* GRÁFICO LOJA 68 */
        mixedModelLoja68 = new BarChartModel();
        ChartData dataLoja68 = new ChartData();

        BarChartDataSet dataSetVendas68 = new BarChartDataSet();
        List<Number> valoresVendas68 = new ArrayList<>();
        
        for(MetasLoja68 metasLoja68:this.metasLoja68DAO.listar()) {
			valoresVendas68.add(metasLoja68.getVendas());
		}
        dataSetVendas68.setData(valoresVendas68);
        dataSetVendas68.setLabel("VENDAS");
        dataSetVendas68.setBorderColor("rgb(255, 99, 132)");
        dataSetVendas68.setBackgroundColor("rgba(0, 138, 151, 0.2)");

        LineChartDataSet dataSetMetas68 = new LineChartDataSet();
        List<Object> valoresMetas68 = new ArrayList<>();
        
        // VALORES EM METAS 68
     	for(MetasLoja68 metasLoja68:this.metasLoja68DAO.listar()) {
     		valoresMetas68.add(metasLoja68.getMeta());
     	}
        dataSetMetas68.setData(valoresMetas68);
        dataSetMetas68.setLabel("META");
        dataSetMetas68.setFill(false);
        dataSetMetas68.setBorderColor("rgb(54, 162, 235)");

        dataLoja68.addChartDataSet(dataSetVendas68);
        dataLoja68.addChartDataSet(dataSetMetas68);

        List<String> labelsMetas68 = new ArrayList<>();

        for(MetasLoja68 metasLoja68:this.metasLoja68DAO.listar()) {
        	labelsMetas68.add(metasLoja68.getMes());
		}
        dataLoja68.setLabels(labelsMetas68);

        mixedModelLoja68.setData(dataLoja68);

        //Options
        BarChartOptions optionsLoja68 = new BarChartOptions();
        CartesianScales cScalesLoja68 = new CartesianScales();
        CartesianLinearAxes linearAxesLoja68 = new CartesianLinearAxes();
        linearAxesLoja68.setOffset(true);
        linearAxesLoja68.setBeginAtZero(true);
        CartesianLinearTicks ticksLoja68 = new CartesianLinearTicks();
        linearAxesLoja68.setTicks(ticksLoja68);

        cScalesLoja68.addYAxesData(linearAxesLoja68);
        optionsLoja68.setScales(cScalesLoja68);
        mixedModelLoja67.setOptions(optionsLoja68);

        
        /* GRÁFICO LOJA 70 */
        mixedModelLoja70 = new BarChartModel();
        ChartData dataLoja70 = new ChartData();

        BarChartDataSet dataSetVendas70 = new BarChartDataSet();
        List<Number> valoresVendas70 = new ArrayList<>();
        
        for(MetasLoja70 metasLoja70:this.metasLoja70DAO.listar()) {
			valoresVendas70.add(metasLoja70.getVendas());
		}
        dataSetVendas70.setData(valoresVendas70);
        dataSetVendas70.setLabel("VENDAS");
        dataSetVendas70.setBorderColor("rgb(255, 99, 132)");
        dataSetVendas70.setBackgroundColor("rgba(0, 138, 151, 0.2)");

        LineChartDataSet dataSetMetas70 = new LineChartDataSet();
        List<Object> valoresMetas70 = new ArrayList<>();
        
        // VALORES EM METAS 70
     	for(MetasLoja70 metasLoja70:this.metasLoja70DAO.listar()) {
     		valoresMetas70.add(metasLoja70.getMeta());
     	}
        dataSetMetas70.setData(valoresMetas70);
        dataSetMetas70.setLabel("META");
        dataSetMetas70.setFill(false);
        dataSetMetas70.setBorderColor("rgb(54, 162, 235)");

        dataLoja70.addChartDataSet(dataSetVendas70);
        dataLoja70.addChartDataSet(dataSetMetas70);

        List<String> labelsMetas70 = new ArrayList<>();

        for(MetasLoja70 metasLoja70:this.metasLoja70DAO.listar()) {
        	labelsMetas70.add(metasLoja70.getMes());
		}
        dataLoja70.setLabels(labelsMetas70);

        mixedModelLoja70.setData(dataLoja70);

        //Options
        BarChartOptions optionsLoja70 = new BarChartOptions();
        CartesianScales cScalesLoja70 = new CartesianScales();
        CartesianLinearAxes linearAxesLoja70 = new CartesianLinearAxes();
        linearAxesLoja70.setOffset(true);
        linearAxesLoja70.setBeginAtZero(true);
        CartesianLinearTicks ticksLoja70 = new CartesianLinearTicks();
        linearAxesLoja70.setTicks(ticksLoja70);

        cScalesLoja70.addYAxesData(linearAxesLoja70);
        optionsLoja70.setScales(cScalesLoja70);
        mixedModelLoja70.setOptions(optionsLoja70);

        /* GRÁFICO GRUPO */
        mixedModelGrupo = new BarChartModel();
        ChartData dataGrupo = new ChartData();

        BarChartDataSet dataSetVendasGrupo = new BarChartDataSet();
        List<Number> valoresVendasGrupo = new ArrayList<>();
        
        for(MetasGrupo metasGrupo:this.metasGrupoDAO.listar()) {
			valoresVendasGrupo.add(metasGrupo.getVendas());
		}
        dataSetVendasGrupo.setData(valoresVendasGrupo);
        dataSetVendasGrupo.setLabel("VENDAS");
        dataSetVendasGrupo.setBorderColor("rgb(255, 99, 132)");
        dataSetVendasGrupo.setBackgroundColor("rgba(0, 138, 151, 0.2)");

        LineChartDataSet dataSetGrupo = new LineChartDataSet();
        List<Object> valoresGrupo = new ArrayList<>();
        
        // VALORES EM METAS GRUPO
     	for(MetasGrupo metasGrupo:this.metasGrupoDAO.listar()) {
     		valoresGrupo.add(metasGrupo.getMeta());
     	}
        dataSetGrupo.setData(valoresGrupo);
        dataSetGrupo.setLabel("META");
        dataSetGrupo.setFill(false);
        dataSetGrupo.setBorderColor("rgb(54, 162, 235)");

        dataGrupo.addChartDataSet(dataSetVendasGrupo);
        dataGrupo.addChartDataSet(dataSetGrupo);

        List<String> labelsGrupo = new ArrayList<>();

        for(MetasGrupo metasGrupo:this.metasGrupoDAO.listar()) {
        	labelsGrupo.add(metasGrupo.getMes());
		}
        dataGrupo.setLabels(labelsGrupo);

        mixedModelGrupo.setData(dataGrupo);

        //Options
        BarChartOptions optionsGrupo = new BarChartOptions();
        CartesianScales cScalesGrupo = new CartesianScales();
        CartesianLinearAxes linearAxesGrupo = new CartesianLinearAxes();
        linearAxesGrupo.setOffset(true);
        linearAxesGrupo.setBeginAtZero(true);
        CartesianLinearTicks ticksGrupo = new CartesianLinearTicks();
        linearAxesGrupo.setTicks(ticksGrupo);

        cScalesGrupo.addYAxesData(linearAxesGrupo);
        optionsGrupo.setScales(cScalesGrupo);
        mixedModelGrupo.setOptions(optionsGrupo);

    }
}