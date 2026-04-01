package br.com.aformula.bean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.aformula.dao.FechamentoEntregaDAO;
import br.com.aformula.domain.FechamentoEntrega;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class FechamentoEntregaBean {

	public FechamentoEntrega fechamentoEntregaCadastro;
	private List<FechamentoEntrega> listaFechamentoEntregas;
	private List<FechamentoEntrega> listaFechamentoEntregasFiltrados;
	
	private String acao;
	private Long codigo;

	public FechamentoEntrega getFechamentoEntregaCadastro() {
		return fechamentoEntregaCadastro;
	}

	public void setFechamentoEntregaCadastro(FechamentoEntrega fechamentoEntregaCadastro) {
		this.fechamentoEntregaCadastro = fechamentoEntregaCadastro;
	}

	public List<FechamentoEntrega> getListaFechamentoEntregas() {
		return listaFechamentoEntregas;
	}

	public void setListaFechamentoEntregas(List<FechamentoEntrega> listaFechamentoEntregas) {
		this.listaFechamentoEntregas = listaFechamentoEntregas;
	}

	public List<FechamentoEntrega> getListaFechamentoEntregasFiltrados() {
		return listaFechamentoEntregasFiltrados;
	}

	public void setListaFechamentoEntregasFiltrados(List<FechamentoEntrega> listaFechamentoEntregasFiltrados) {
		this.listaFechamentoEntregasFiltrados = listaFechamentoEntregasFiltrados;
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
		fechamentoEntregaCadastro = new FechamentoEntrega();
	}
	
	public void salvar() {
		try {
			FechamentoEntregaDAO fechamentoEntregaDAO = new FechamentoEntregaDAO();	
			fechamentoEntregaDAO.salvar(fechamentoEntregaCadastro);
			
			fechamentoEntregaCadastro = new FechamentoEntrega();
//			novo();		
			FacesUtil.adicionarMsgInfo("Fechamento salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar fechamento:" + ex.getMessage());
		}
		
	}
	
	public void carregarPesquisa() {
		try {
			FechamentoEntregaDAO fechamentoEntregaDAO = new FechamentoEntregaDAO();
			listaFechamentoEntregas = fechamentoEntregaDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar fechamento:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				FechamentoEntregaDAO fechamentoEntregaDAO = new FechamentoEntregaDAO();
				fechamentoEntregaCadastro = fechamentoEntregaDAO.buscarPorCodigo(codigo);
			}else {
				fechamentoEntregaCadastro = new FechamentoEntrega();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados do fechamento: " + ex.getMessage());
		}
	}

	public void editar() {
		try {
			novo();
			
			FechamentoEntregaDAO fechamentoEntregaDAO = new FechamentoEntregaDAO();
			Long codigoFechamentoEntregaAberto = fechamentoEntregaDAO.fechamentoAberto();
			
			FechamentoEntrega fechamentoEntrega = fechamentoEntregaDAO.buscarPorCodigo(codigoFechamentoEntregaAberto);
			
			Date data_inicio_mes = null, data_fim_mes = null;
			Integer mesAberto = 0;
			Month mesTraducao = null;
			
			switch (fechamentoEntrega.getMes()) {
		    	case "JANEIRO":
		    		mesAberto = 1;
		    		mesTraducao = Month.JANUARY;
		    		break;
		    	case "FEVEREIRO":
		    		mesAberto = 2;
		    		mesTraducao = Month.FEBRUARY;
			        break;
		    	case "MARÇO":
		    		mesAberto = 3;
		    		mesTraducao = Month.MARCH;
			        break;
			    case "ABRIL":
			    	mesAberto = 4;
			    	mesTraducao = Month.APRIL;
			        break;
			    case "MAIO":
			    	mesAberto = 5;
			    	mesTraducao = Month.MAY;
		       		break;
			    case "JUNHO":
			    	mesAberto = 6;
			    	mesTraducao = Month.JUNE;
			    	break;
			    case "JULHO":
			    	mesAberto = 7;
			    	mesTraducao = Month.JULY;
			    	break;
			    case "AGOSTO":
			    	mesAberto = 8;
			    	mesTraducao = Month.AUGUST;
			    	break;
			    case "SETEMBRO":
			    	mesAberto = 9;
			    	mesTraducao = Month.SEPTEMBER;
			    	break;
			    case "OUTUBRO":
			    	mesAberto = 10;
			    	mesTraducao = Month.OCTOBER;
			    	break;
			    case "NOVEMBRO":
			    	mesAberto = 11;
			    	mesTraducao = Month.NOVEMBER;
			    	break;
			    case "DEZEMBRO":
			    	mesAberto = 12;
			    	mesTraducao = Month.DECEMBER;
			    	break;
		    }
		    
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    
		    data_inicio_mes = formatter.parse("01/"+ mesAberto +"/"+fechamentoEntrega.getAno());
		   
		    LocalDate date = LocalDate.of(fechamentoEntrega.getAno(), mesTraducao, 01);
		    int numeroDeDias = date.lengthOfMonth();
	
		    data_fim_mes = formatter.parse(numeroDeDias + "/"+ mesAberto +"/"+fechamentoEntrega.getAno());		 
		    
			fechamentoEntregaCadastro.setCodigo(fechamentoEntrega.getCodigo());
			fechamentoEntregaCadastro.setAno(fechamentoEntrega.getAno());
			fechamentoEntregaCadastro.setMes(fechamentoEntrega.getMes());
			
			int anoAtual = fechamentoEntrega.getAno();
			
			fechamentoEntregaCadastro.setTotal_entregas(fechamentoEntregaDAO.totalEntregasMes(data_inicio_mes, data_fim_mes));
			fechamentoEntregaCadastro.setTotal_recebidos(fechamentoEntregaDAO.totalRecebidosMes(data_inicio_mes, data_fim_mes));
			fechamentoEntregaCadastro.setTotal_taxas(fechamentoEntregaDAO.totalTaxasMes(data_inicio_mes, data_fim_mes));													
			fechamentoEntregaCadastro.setTotal_dav(fechamentoEntregaDAO.totalDAVMes(data_inicio_mes, data_fim_mes));
			fechamentoEntregaCadastro.setTicket_medio(fechamentoEntregaCadastro.getTotal_dav().doubleValue()/fechamentoEntregaCadastro.getTotal_entregas().doubleValue());
			
			fechamentoEntregaCadastro.setStatus("FECHADO");

			fechamentoEntregaDAO.editar(fechamentoEntregaCadastro);
			
			novo();
			
			FechamentoEntregaDAO fechamentoEntregaDAONovo = new FechamentoEntregaDAO();
			FechamentoEntrega fechamentoEntregaNovo = new FechamentoEntrega();
					
			String nomeMesAbertoProximo = null;
			int mesAbertoProximo = mesAberto + 1;
			
			if (mesAbertoProximo > 12) {
				mesAbertoProximo = 1;
				anoAtual = anoAtual + 1;
			}
			
			switch (mesAbertoProximo) {
		    	case 1:
		    		nomeMesAbertoProximo = "JANEIRO";
		    		break;
		    	case 2:
		    		nomeMesAbertoProximo = "FEVEREIRO";
			        break;
		    	case 3:
		    		nomeMesAbertoProximo = "MARÇO";
			        break;
			    case 4:
			    	nomeMesAbertoProximo = "ABRIL";
			        break;
			    case 5:
			    	nomeMesAbertoProximo = "MAIO";
		       		break;
			    case 6:
			    	nomeMesAbertoProximo = "JUNHO";
			    	break;
			    case 7:
			    	nomeMesAbertoProximo = "JULHO";
			    	break;
			    case 8:
			    	nomeMesAbertoProximo = "AGOSTO";
			    	break;
			    case 9:
			    	nomeMesAbertoProximo = "SETEMBRO";
			    	break;
			    case 10:
			    	nomeMesAbertoProximo = "OUTUBRO";
			    	break;
			    case 11:
			    	nomeMesAbertoProximo = "NOVEMBRO";
			    	break;
			    case 12:
			    	nomeMesAbertoProximo = "DEZEMBRO";
			    	break;
		    }
			
			fechamentoEntregaNovo.setAno(anoAtual);
			fechamentoEntregaNovo.setMes(nomeMesAbertoProximo);
			
			fechamentoEntregaNovo.setTotal_entregas(0L);
			fechamentoEntregaNovo.setTotal_recebidos(0D);
			fechamentoEntregaNovo.setTotal_taxas(0D);		
			fechamentoEntregaNovo.setTotal_dav(0D);
			fechamentoEntregaNovo.setTicket_medio(0D);
			
			fechamentoEntregaNovo.setStatus("ABERTO");

			fechamentoEntregaDAONovo.salvar(fechamentoEntregaNovo); 
			
			FacesUtil.adicionarMsgInfo("FECHAMENTO REALIZADO COM SUCESSO");
			FacesContext.getCurrentInstance().getExternalContext().redirect("dashboardEntrega.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("ERRO AO REALIZAR FECHAMENTO: " + ex.getMessage());
		}	
	}
	
}
