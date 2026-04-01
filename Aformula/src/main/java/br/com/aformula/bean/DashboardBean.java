package br.com.aformula.bean;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import br.com.aformula.dao.DashboardDAO;
import br.com.aformula.domain.FechamentoProducao;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.Producao;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class DashboardBean {
	
	public FechamentoProducao fechamentoProducaoCadastro;
	
	private Long totalProducaoSache;
	private Long totalProducaoCapsula;
	private Long totalProducaoShake;
	private Long totalProducaoEnvase;
	
	private Long totalContratoSache;
	private Long totalContratoCapsula;
	private Long totalContratoShake;
	private Long totalContratoEnvase;
	private Long totalContrato;
	
	private Double mediaProdContratoSache;
	private Double mediaProdContratoCapsula;
	private Double mediaProdContratoShake;
	private Double mediaProdContratoEnvase;
	
	private List<Producao> totalContratoSacheDia;
	private List<Producao> totalContratoCapsulaDia;
	private List<Producao> totalContratoShakeDia;
	private List<Producao> totalContratoEnvaseDia;
	
	private List<Funcionario> rankingMes;
	
	private List<Producao> totalProducaoSacheDia;
	private List<Producao> totalProducaoCapsulaDia;
	
	public Long getTotalProducaoSache() {
		return totalProducaoSache;
	}

	public void setTotalProducaoSache(Long totalProducaoSache) {
		this.totalProducaoSache = totalProducaoSache;
	}

	public Long getTotalProducaoCapsula() {
		return totalProducaoCapsula;
	}

	public void setTotalProducaoCapsula(Long totalProducaoCapsula) {
		this.totalProducaoCapsula = totalProducaoCapsula;
	}

	public Long getTotalProducaoShake() {
		return totalProducaoShake;
	}

	public void setTotalProducaoShake(Long totalProducaoShake) {
		this.totalProducaoShake = totalProducaoShake;
	}
	
	public Long getTotalContratoSache() {
		return totalContratoSache;
	}

	public void setTotalContratoSache(Long totalContratoSache) {
		this.totalContratoSache = totalContratoSache;
	}

	public Long getTotalContratoCapsula() {
		return totalContratoCapsula;
	}

	public void setTotalContratoCapsula(Long totalContratoCapsula) {
		this.totalContratoCapsula = totalContratoCapsula;
	}

	public Long getTotalContratoShake() {
		return totalContratoShake;
	}

	public void setTotalContratoShake(Long totalContratoShake) {
		this.totalContratoShake = totalContratoShake;
	}
	
	public Long getTotalContratoEnvase() {
		return totalContratoEnvase;
	}

	public void setTotalContratoEnvase(Long totalContratoEnvase) {
		this.totalContratoEnvase = totalContratoEnvase;
	}

	public Long getTotalContrato() {
		return totalContrato;
	}

	public void setTotalContrato(Long totalContrato) {
		this.totalContrato = totalContrato;
	}
	
	public Double getMediaProdContratoSache() {
		return mediaProdContratoSache;
	}

	public void setMediaProdContratoSache(Double mediaProdContratoSache) {
		this.mediaProdContratoSache = mediaProdContratoSache;
	}

	public Double getMediaProdContratoCapsula() {
		return mediaProdContratoCapsula;
	}

	public void setMediaProdContratoCapsula(Double mediaProdContratoCapsula) {
		this.mediaProdContratoCapsula = mediaProdContratoCapsula;
	}

	public Double getMediaProdContratoShake() {
		return mediaProdContratoShake;
	}

	public void setMediaProdContratoShake(Double mediaProdContratoShake) {
		this.mediaProdContratoShake = mediaProdContratoShake;
	}

	public Long getTotalProducaoEnvase() {
		return totalProducaoEnvase;
	}

	public void setTotalProducaoEnvase(Long totalProducaoEnvase) {
		this.totalProducaoEnvase = totalProducaoEnvase;
	}

	public Double getMediaProdContratoEnvase() {
		return mediaProdContratoEnvase;
	}

	public void setMediaProdContratoEnvase(Double mediaProdContratoEnvase) {
		this.mediaProdContratoEnvase = mediaProdContratoEnvase;
	}

	public List<Producao> getTotalContratoSacheDia() {
		return totalContratoSacheDia;
	}

	public void setTotalContratoSacheDia(List<Producao> totalContratoSacheDia) {
		this.totalContratoSacheDia = totalContratoSacheDia;
	}

	public List<Producao> getTotalContratoCapsulaDia() {
		return totalContratoCapsulaDia;
	}

	public void setTotalContratoCapsulaDia(List<Producao> totalContratoCapsulaDia) {
		this.totalContratoCapsulaDia = totalContratoCapsulaDia;
	}

	public List<Producao> getTotalContratoShakeDia() {
		return totalContratoShakeDia;
	}

	public void setTotalContratoShakeDia(List<Producao> totalContratoShakeDia) {
		this.totalContratoShakeDia = totalContratoShakeDia;
	}

	public List<Producao> getTotalContratoEnvaseDia() {
		return totalContratoEnvaseDia;
	}

	public void setTotalContratoEnvaseDia(List<Producao> totalContratoEnvaseDia) {
		this.totalContratoEnvaseDia = totalContratoEnvaseDia;
	}
	
	public List<Funcionario> getRankingMes() {
		return rankingMes;
	}

	public void setRankingMes(List<Funcionario> rankingMes) {
		this.rankingMes = rankingMes;
	}

	public List<Producao> getTotalProducaoSacheDia() {
		return totalProducaoSacheDia;
	}

	public void setTotalProducaoSacheDia(List<Producao> totalProducaoSacheDia) {
		this.totalProducaoSacheDia = totalProducaoSacheDia;
	}

	public List<Producao> getTotalProducaoCapsulaDia() {
		return totalProducaoCapsulaDia;
	}

	public void setTotalProducaoCapsulaDia(List<Producao> totalProducaoCapsulaDia) {
		this.totalProducaoCapsulaDia = totalProducaoCapsulaDia;
	}

	public void novo() {
		fechamentoProducaoCadastro = new FechamentoProducao();
	}

	public void carregarTotalProducao() {
		try {
			DashboardDAO dashboardDAO = new DashboardDAO();
			Date data_inicial_mes, data_final_mes;

			Calendar c = Calendar.getInstance();
//		    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

		    //PRIMEIRO DIA DO MÊS ATUAL
		    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		    data_inicial_mes = c.getTime();

		    //ÚLTIMO DIA DO MÊS ATUAL
		    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		    data_final_mes = c.getTime();

			totalProducaoSache = dashboardDAO.totalProducaoMes("SACHÊ",data_inicial_mes, data_final_mes);
			totalProducaoCapsula = dashboardDAO.totalProducaoMes("CÁPSULA ÓLEOSA", data_inicial_mes, data_final_mes);
			totalProducaoShake = dashboardDAO.totalProducaoMes("SHAKE", data_inicial_mes, data_final_mes);
			totalProducaoEnvase = dashboardDAO.totalProducaoMes("ENVASE", data_inicial_mes, data_final_mes);
			
			totalContratoSache = dashboardDAO.totalContratoMes("SACHÊ",data_inicial_mes, data_final_mes);
			totalContratoCapsula = dashboardDAO.totalContratoMes("CÁPSULA ÓLEOSA", data_inicial_mes, data_final_mes);
			totalContratoShake = dashboardDAO.totalContratoMes("SHAKE", data_inicial_mes, data_final_mes);
			totalContratoEnvase = dashboardDAO.totalContratoMes("ENVASE", data_inicial_mes, data_final_mes);
			totalContrato = (totalContratoSache + totalContratoCapsula + totalContratoShake + totalContratoEnvase);			
			
			mediaProdContratoSache = (double)Math.round(dashboardDAO.mediaProdContratoMes("SACHÊ", data_inicial_mes, data_final_mes));
			mediaProdContratoCapsula = (double)Math.round(dashboardDAO.mediaProdContratoMes("CÁPSULA ÓLEOSA", data_inicial_mes, data_final_mes));
			mediaProdContratoShake = (double)Math.round(dashboardDAO.mediaProdContratoMes("SHAKE", data_inicial_mes, data_final_mes));
			mediaProdContratoEnvase = (double)Math.round(dashboardDAO.mediaProdContratoMes("ENVASE", data_inicial_mes, data_final_mes));
								
			totalContratoSacheDia = dashboardDAO.totalContratoDia("SACHÊ",data_inicial_mes, data_final_mes);
			totalContratoCapsulaDia = dashboardDAO.totalContratoDia("CÁPSULA ÓLEOSA", data_inicial_mes, data_final_mes);
			totalContratoShakeDia = dashboardDAO.totalContratoDia("SHAKE", data_inicial_mes, data_final_mes);
			totalContratoEnvaseDia = dashboardDAO.totalContratoDia("ENVASE", data_inicial_mes, data_final_mes);
			
			totalProducaoSacheDia = dashboardDAO.totalProducaoDia("SACHÊ",data_inicial_mes, data_final_mes);
			totalProducaoCapsulaDia = dashboardDAO.totalProducaoDia("CÁPSULA ÓLEOSA",data_inicial_mes, data_final_mes);
			
			rankingMes =  dashboardDAO.rankingMes(data_inicial_mes, data_final_mes);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}	
}