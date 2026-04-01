package br.com.aformula.bean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.aformula.dao.FechamentoProducaoDAO;
import br.com.aformula.domain.FechamentoProducao;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class FechamentoProducaoBean {

	public FechamentoProducao fechamentoProducaoCadastro;
	private List<FechamentoProducao> listaFechamentoProducoes;
	private List<FechamentoProducao> listaFechamentoProducoesFiltrados;
	
	private String acao;
	private Long codigo;

	public FechamentoProducao getFechamentoProducaoCadastro() {
		return fechamentoProducaoCadastro;
	}

	public void setFechamentoProducaoCadastro(FechamentoProducao fechamentoProducaoCadastro) {
		this.fechamentoProducaoCadastro = fechamentoProducaoCadastro;
	}

	public List<FechamentoProducao> getListaFechamentoProducoes() {
		return listaFechamentoProducoes;
	}

	public void setListaFechamentoProducoes(List<FechamentoProducao> listaFechamentoProducoes) {
		this.listaFechamentoProducoes = listaFechamentoProducoes;
	}

	public List<FechamentoProducao> getListaFechamentoProducoesFiltrados() {
		return listaFechamentoProducoesFiltrados;
	}

	public void setListaFechamentoProducoesFiltrados(List<FechamentoProducao> listaFechamentoProducoesFiltrados) {
		this.listaFechamentoProducoesFiltrados = listaFechamentoProducoesFiltrados;
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
		fechamentoProducaoCadastro = new FechamentoProducao();
	}
	
	public void salvar() {
		try {
			FechamentoProducaoDAO fechamentoProducaoDAO = new FechamentoProducaoDAO();	
			fechamentoProducaoDAO.salvar(fechamentoProducaoCadastro);
			
			fechamentoProducaoCadastro = new FechamentoProducao();
//			novo();
			
			FacesUtil.adicionarMsgInfo("Fechamento salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar fechamento:" + ex.getMessage());
		}
		
	}
	
	public void carregarPesquisa() {
		try {
			FechamentoProducaoDAO fechamentoProducaoDAO = new FechamentoProducaoDAO();
			listaFechamentoProducoes = fechamentoProducaoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar fechamento:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				FechamentoProducaoDAO fechamentoProducaoDAO = new FechamentoProducaoDAO();
				fechamentoProducaoCadastro = fechamentoProducaoDAO.buscarPorCodigo(codigo);
			}else {
				fechamentoProducaoCadastro = new FechamentoProducao();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados do fechamento: " + ex.getMessage());
		}
	}

	public void editar() {
		try {
			novo();
			
			FechamentoProducaoDAO fechamentoProducaoDAO = new FechamentoProducaoDAO();
			Long codigoFechamentoAberto = fechamentoProducaoDAO.fechamentoAberto();
			
			FechamentoProducao fechamentoProducao = fechamentoProducaoDAO.buscarPorCodigo(codigoFechamentoAberto);
			
			Date data_inicio_mes = null, data_fim_mes = null;
			Integer mesAberto = 0;
			Month mesTraducao = null;
			
			switch (fechamentoProducao.getMes()) {
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
		    
		    data_inicio_mes = formatter.parse("01/"+ mesAberto +"/"+fechamentoProducao.getAno());
		   
		    LocalDate date = LocalDate.of(fechamentoProducao.getAno(), mesTraducao, 01);
		    int numeroDeDias = date.lengthOfMonth();
	
		    data_fim_mes = formatter.parse(numeroDeDias + "/"+ mesAberto +"/"+fechamentoProducao.getAno());		 
		    
			fechamentoProducaoCadastro.setCodigo(fechamentoProducao.getCodigo());
			fechamentoProducaoCadastro.setAno(fechamentoProducao.getAno());
			fechamentoProducaoCadastro.setMes(fechamentoProducao.getMes());
			
			int anoAtual = fechamentoProducao.getAno();
			
			fechamentoProducaoCadastro.setTotal_sache(fechamentoProducaoDAO.totalContratoMes("SACHÊ",data_inicio_mes, data_fim_mes));
			fechamentoProducaoCadastro.setTotal_capsula(fechamentoProducaoDAO.totalContratoMes("CÁPSULA ÓLEOSA",data_inicio_mes, data_fim_mes));
			fechamentoProducaoCadastro.setTotal_shake(fechamentoProducaoDAO.totalContratoMes("SHAKE",data_inicio_mes, data_fim_mes));
			fechamentoProducaoCadastro.setTotal_envase(fechamentoProducaoDAO.totalContratoMes("ENVASE",data_inicio_mes, data_fim_mes));
			
			Long totalContratosFechamento = (fechamentoProducaoCadastro.getTotal_sache() + fechamentoProducaoCadastro.getTotal_capsula() + fechamentoProducaoCadastro.getTotal_shake() + fechamentoProducaoCadastro.getTotal_envase());
			fechamentoProducaoCadastro.setTotal_contratos(totalContratosFechamento);
			
			fechamentoProducaoCadastro.setTotal_producao_sache(fechamentoProducaoDAO.totalProducaoMes("SACHÊ",data_inicio_mes, data_fim_mes));
			fechamentoProducaoCadastro.setTotal_producao_capsula(fechamentoProducaoDAO.totalProducaoMes("CÁPSULA ÓLEOSA",data_inicio_mes, data_fim_mes));
			
			fechamentoProducaoCadastro.setMedia_contrato_sache((double)Math.round(fechamentoProducaoDAO.mediaProdContratoMes("SACHÊ",data_inicio_mes, data_fim_mes)));
			fechamentoProducaoCadastro.setMedia_contrato_capsula((double)Math.round(fechamentoProducaoDAO.mediaProdContratoMes("CÁPSULA ÓLEOSA",data_inicio_mes, data_fim_mes)));
			
			fechamentoProducaoCadastro.setStatus("FECHADO");

			fechamentoProducaoDAO.editar(fechamentoProducaoCadastro);
			
			novo();
			
			FechamentoProducaoDAO fechamentoProducaoDAONovo = new FechamentoProducaoDAO();
			FechamentoProducao fechamentoProducaoNovo = new FechamentoProducao();
						
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
			
			fechamentoProducaoNovo.setAno(anoAtual);
			fechamentoProducaoNovo.setMes(nomeMesAbertoProximo);
			
			fechamentoProducaoNovo.setTotal_sache(0L);
			fechamentoProducaoNovo.setTotal_capsula(0L);
			fechamentoProducaoNovo.setTotal_shake(0L);
			fechamentoProducaoNovo.setTotal_envase(0L);
			
			fechamentoProducaoNovo.setTotal_contratos(0L);
			
			fechamentoProducaoNovo.setTotal_producao_sache(0L);
			fechamentoProducaoNovo.setTotal_producao_capsula(0L);
			
			fechamentoProducaoNovo.setMedia_contrato_sache(0D);
			fechamentoProducaoNovo.setMedia_contrato_capsula(0D);

			fechamentoProducaoNovo.setStatus("ABERTO");

			fechamentoProducaoDAONovo.salvar(fechamentoProducaoNovo);
			
			FacesUtil.adicionarMsgInfo("FECHAMENTO REALIZADO COM SUCESSO");
			FacesContext.getCurrentInstance().getExternalContext().redirect("dashboardProducao.xhtml");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("ERRO AO REALIZAR FECHAMENTO: " + ex.getMessage());
		}	
	}
	
}
