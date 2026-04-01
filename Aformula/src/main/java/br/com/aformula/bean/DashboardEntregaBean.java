package br.com.aformula.bean;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import br.com.aformula.dao.DashboardEntregaDAO;
import br.com.aformula.domain.Entrega;
import br.com.aformula.domain.FechamentoProducao;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class DashboardEntregaBean {
	
	public FechamentoProducao fechamentoProducaoCadastro;
	
	private Long totalEntregasMes;
	private Double totalRecebidosMes;
	private Double totalDAVMes;
	private Double totalTaxasMes;
	private Double totalTicketMedioMes;
	private List<Entrega> listaTotalEntregaEntregador;
	private List<Entrega> totalEntregaDia = new ArrayList<>();
	private List<Entrega> listagemNomeEntregadores;
	
	public FechamentoProducao getFechamentoProducaoCadastro() {
		return fechamentoProducaoCadastro;
	}

	public void setFechamentoProducaoCadastro(FechamentoProducao fechamentoProducaoCadastro) {
		this.fechamentoProducaoCadastro = fechamentoProducaoCadastro;
	}

	public Long getTotalEntregasMes() {
		return totalEntregasMes;
	}

	public void setTotalEntregasMes(Long totalEntregasMes) {
		this.totalEntregasMes = totalEntregasMes;
	}

	public Double getTotalRecebidosMes() {
		return totalRecebidosMes;
	}

	public void setTotalRecebidosMes(Double totalRecebidosMes) {
		this.totalRecebidosMes = totalRecebidosMes;
	}

	public Double getTotalTaxasMes() {
		return totalTaxasMes;
	}

	public void setTotalTaxasMes(Double totalTaxasMes) {
		this.totalTaxasMes = totalTaxasMes;
	}
	
	public List<Entrega> getListaTotalEntregaEntregador() {
		return listaTotalEntregaEntregador;
	}

	public void setListaTotalEntregaEntregador(List<Entrega> listaTotalEntregaEntregador) {
		this.listaTotalEntregaEntregador = listaTotalEntregaEntregador;
	}

	public List<Entrega> getTotalEntregaDia() {
		return totalEntregaDia;
	}

	public void setTotalEntregaDia(List<Entrega> totalEntregaDia) {
		this.totalEntregaDia = totalEntregaDia;
	}

	public List<Entrega> getListagemNomeEntregadores() {
		return listagemNomeEntregadores;
	}

	public void setListagemNomeEntregadores(List<Entrega> listagemNomeEntregadores) {
		this.listagemNomeEntregadores = listagemNomeEntregadores;
	}

	public Double getTotalDAVMes() {
		return totalDAVMes;
	}

	public void setTotalDAVMes(Double totalDAVMes) {
		this.totalDAVMes = totalDAVMes;
	}

	public Double getTotalTicketMedioMes() {
		return totalTicketMedioMes;
	}

	public void setTotalTicketMedioMes(Double totalTicketMedioMes) {
		this.totalTicketMedioMes = totalTicketMedioMes;
	}

	public void novo() {
		fechamentoProducaoCadastro = new FechamentoProducao();
	}

	public void carregarTotalEntrega() {
		try {
			DashboardEntregaDAO dashboardEntregaDAO = new DashboardEntregaDAO();
			Date data_inicial_mes, data_final_mes;

			Calendar c = Calendar.getInstance();
//		    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

		    //PRIMEIRO DIA DO MÊS ATUAL
		    c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		    data_inicial_mes = c.getTime();

		    //ÚLTIMO DIA DO MÊS ATUAL
		    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		    data_final_mes = c.getTime();	   
		    
			totalEntregasMes = dashboardEntregaDAO.totalEntregaMes(data_inicial_mes, data_final_mes);
			
			totalRecebidosMes = dashboardEntregaDAO.totalRecebidosMes(data_inicial_mes, data_final_mes);
			
			totalDAVMes = dashboardEntregaDAO.totalDAVMes(data_inicial_mes, data_final_mes);
			
			totalTaxasMes = dashboardEntregaDAO.totalTaxasMes(data_inicial_mes, data_final_mes);	
			 	
			totalTicketMedioMes = totalDAVMes/totalEntregasMes;
			
			//CRIA A LISTA COM OS CÓDIGOS DOS ENTREGADORES ATIVOS - GRÁFICO
		    listagemNomeEntregadores = (List<Entrega>) dashboardEntregaDAO.listarTotalEntregaEntregador(data_inicial_mes, data_final_mes);		    
		    for (int i = 0; i < listagemNomeEntregadores.size(); i++) {	    		    	
		    	totalEntregaDia.addAll(dashboardEntregaDAO.totalEntregaDia(listagemNomeEntregadores.get(i).getCodigoEntregador().intValue(), data_inicial_mes, data_final_mes));
		    }	
		    
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao recuperar totais: " + ex.getMessage());
		}
	}	
}