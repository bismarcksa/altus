package br.com.aformula.bean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.ControlePontoDAO;
import br.com.aformula.dao.ControlePontoDetalheDAO;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.domain.ControlePonto;
import br.com.aformula.domain.ControlePontoDetalhe;
import br.com.aformula.domain.ControlePontoDetalheID;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class ControlePontoBean {

	public ControlePonto controlePontoCadastro;
	private List<ControlePonto> listaControlePontos;
	private List<ControlePonto> listaControlePontosCoord;
	private List<ControlePonto> listaControlePontosUsuario;
	private List<ControlePonto> listaControlePontosFiltrados;	
	private List<ControlePonto> listarFechado;
	private List<Funcionario> listaFuncionarios;
	private ControlePontoDetalhe controlePontoDetalhe;
	private ControlePontoDetalheID controlePontoDetalheID;	
	private List<ControlePontoDetalhe> listaControlePontoDetalhes;	
	private String competencia, status;
	private Long codigo, codigoFilial;
	private Integer ano;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	public ControlePonto getControlePontoCadastro() {
		return controlePontoCadastro;
	}

	public void setControlePontoCadastro(ControlePonto controlePontoCadastro) {
		this.controlePontoCadastro = controlePontoCadastro;
	}

	public List<ControlePonto> getListaControlePontos() {
		return listaControlePontos;
	}

	public void setListaControlePontos(List<ControlePonto> listaControlePontos) {
		this.listaControlePontos = listaControlePontos;
	}

	public List<ControlePonto> getListaControlePontosFiltrados() {
		return listaControlePontosFiltrados;
	}

	public void setListaControlePontosFiltrados(List<ControlePonto> listaControlePontosFiltrados) {
		this.listaControlePontosFiltrados = listaControlePontosFiltrados;
	}
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public ControlePontoDetalhe getControlePontoDetalhe() {
		return controlePontoDetalhe;
	}

	public void setControlePontoDetalhe(ControlePontoDetalhe controlePontoDetalhe) {
		this.controlePontoDetalhe = controlePontoDetalhe;
	}

	public ControlePontoDetalheID getControlePontoDetalheID() {
		return controlePontoDetalheID;
	}

	public void setControlePontoDetalheID(ControlePontoDetalheID controlePontoDetalheID) {
		this.controlePontoDetalheID = controlePontoDetalheID;
	}

	public String getCompetencia() {
		return competencia;
	}

	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Long getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(Long codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<ControlePonto> getListaControlePontosUsuario() {
		return listaControlePontosUsuario;
	}

	public void setListaControlePontosUsuario(List<ControlePonto> listaControlePontosUsuario) {
		this.listaControlePontosUsuario = listaControlePontosUsuario;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	public List<ControlePontoDetalhe> getListaControlePontoDetalhes() {
		return listaControlePontoDetalhes;
	}

	public void setListaControlePontoDetalhes(List<ControlePontoDetalhe> listaControlePontoDetalhes) {
		this.listaControlePontoDetalhes = listaControlePontoDetalhes;
	}

	public List<ControlePonto> getListaControlePontosCoord() {
		return listaControlePontosCoord;
	}

	public void setListaControlePontosCoord(List<ControlePonto> listaControlePontosCoord) {
		this.listaControlePontosCoord = listaControlePontosCoord;
	}

	public List<ControlePonto> getListarFechado() {
		return listarFechado;
	}

	public void setListarFechado(List<ControlePonto> listarFechado) {
		this.listarFechado = listarFechado;
	}

	public void novo() {
		controlePontoCadastro = new ControlePonto();
		
//		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
//		Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());
		
		Date date = new Date(); // INSTÂNCIA DATA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		ano = calendar.get(Calendar.YEAR);
		int mes = calendar.get(Calendar.MONTH);
		//VARIAVEL FUNCAO NÃO UTILIZADA, PODE APAGAR
//		funcao = funcionario.getFuncao();
		competencia = String.format("%02d", (mes + 1))+"/" + Integer.toString(ano);
	}
	
	public void salvar() {
		try {
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
			
			controlePontoCadastro.setStatus("ABERTO");
			
			String mes;
			switch (controlePontoCadastro.getMes()) {
		    	case "JANEIRO":
		    		mes = "01";
		    		break;
		    	case "FEVEREIRO":
		    		mes = "02";
		    		break;
		    	case "MARÇO":
		    		mes = "03";
		    		break;
		    	case "ABRIL":
		    		mes = "04";
		    		break;
			    case "MAIO":
			    	mes = "05";
			    	break;
			    case "JUNHO":
			    	mes = "06";
			    	break;
			    case "JULHO":
			    	mes = "07";
			    	break;
			    case "AGOSTO":
			    	mes = "08";
			    	break;
			    case "SETEMBRO":
			    	mes = "09";
			    	break;
			    case "OUTUBRO":
			    	mes = "10";
			    	break;
			    case "NOVEMBRO":
			    	mes = "11";
			    	break;
			    default:
			    	mes = "12";
			}
			
			controlePontoCadastro.setCompetencia(mes + "/" + controlePontoCadastro.getAno().toString());
			controlePontoCadastro.setBancoHora(0);

			controlePontoDAO.salvar(controlePontoCadastro);	
			
			controlePontoDetalhe = new ControlePontoDetalhe();
			
			Calendar datas = new GregorianCalendar();
	        datas.set(Calendar.MONTH, Integer.parseInt(mes)-1);//2
	        int quantidadeDias = datas.getActualMaximum (Calendar.DAY_OF_MONTH);
			
	        String dataMes;	       	     
	        for (int x = 1; x <= quantidadeDias; x++) {
	        	
	        	dataMes = (x + "/"+ mes + "/" + controlePontoCadastro.getAno());	        	
	        	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	        	Date dataFormatada = formato.parse(dataMes);      
	        	
	        	controlePontoDetalheID = new ControlePontoDetalheID();
	        	controlePontoDetalheID.setCompetencia(controlePontoCadastro.getCompetencia());
	        	controlePontoDetalheID.setFuncionario(controlePontoCadastro.getFuncionario());
	        	controlePontoDetalheID.setData(dataFormatada);
	        	        	
	        	//GRAVA FOLHA DETALHADA
	        	controlePontoDetalhe.setControlePontoCodigo(controlePontoCadastro.getCodigo());
	        	controlePontoDetalhe.setControlePontoDetalheID(controlePontoDetalheID);	  
	        	
	        	String horasTrabalhadas = "01/01/1970/" + String.format("%02d", "00") + "/" + String.format("%02d", "00");
				
	        	String[] dataSeparada = horasTrabalhadas.split("/");
				LocalDateTime hoje = LocalDateTime.of(Integer.parseInt(dataSeparada[2]), Integer.parseInt(dataSeparada[1]), Integer.parseInt(dataSeparada[0]), Integer.parseInt(dataSeparada[3]), Integer.parseInt(dataSeparada[4]));	
				
	        	Date date1 = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());
	        	
	        	controlePontoDetalhe.setHoras_trabalhadas(date1);	        	
	        	controlePontoDetalhe.setDia(pegaDiaSemana(dataMes));
	        	
	        	ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();	
	        	controlePontoDetalheDAO.salvar(controlePontoDetalhe);
	        }	     		
			
			controlePontoCadastro = new ControlePonto();
			controlePontoDetalhe = new ControlePontoDetalhe();
			controlePontoDetalheID = new ControlePontoDetalheID();
			
			FacesUtil.adicionarMsgInfo("Controle de Ponto salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Controle de Ponto:" + ex.getMessage());
		}
	}
	
	//RETORNO O DIA DA SEMANA EX.: 07/03/2024
	public static String pegaDiaSemana(String data){ 
	    String diaSemana = "---";
	    GregorianCalendar gc = new GregorianCalendar();
	    try {
	        gc.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(data));
	        switch (gc.get(Calendar.DAY_OF_WEEK)) {
	            case Calendar.SUNDAY:
	            	diaSemana = "Domingo";
	                break;
	            case Calendar.MONDAY:
	            	diaSemana = "Segunda-feira";
	                break;
	            case Calendar.TUESDAY:
	            	diaSemana = "Terça-feira";
	            break;
	            case Calendar.WEDNESDAY:
	            	diaSemana = "Quarta-feira";
	                break;
	            case Calendar.THURSDAY:
	            	diaSemana = "Quinta-feira";
	                break;
	            case Calendar.FRIDAY:
	            	diaSemana = "Sexta-feira";
	                break;
	            case Calendar.SATURDAY:
	            	diaSemana = "Sábado";
	        }
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return diaSemana;
	}
	
	//DEVE SER LISTADO PARA QUEM FAZ O LANÇAMENTO DE PONTO
	public void carregarPesquisa() {
		try {
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
			listaControlePontos = controlePontoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Controle de Ponto:" + ex.getMessage());
		}
	}
	
	//LISTA SÓ O PONTO DA SUA EQUIPE
	public void carregarPesquisaSupervisor() {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());
		
		try {
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
			listaControlePontos = controlePontoDAO.listarPorSupervisor(funcionario.getCodigo());
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Controle de Ponto:" + ex.getMessage());
		}
	}
	
	//MÉTODO PARA CARREGAR APENAS OS PONTOS DO USUÁRIO LOGADO
	public void carregarPesquisaUsuario() {
		try {
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
			listaControlePontosUsuario = controlePontoDAO.listarControlePontoUsuario(autenticacaoBean.getFuncionarioLogado().getCodigo());
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção: " + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {
			if ((competencia != null) && (codigo != null)) {							
				ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
				controlePontoCadastro = controlePontoDAO.buscarPorCodigo(competencia, codigo);
			}else {
				controlePontoCadastro = new ControlePonto();
			} 
			
			//LISTA TODOS OS FUNCIONARIOS ATIVOS
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();	
			listaFuncionarios = funcionarioDAO.listarTodosAtivo();
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados do Controle de Ponto:" + ex.getMessage());
		}
	}
	
	public void excluir(String competencia, Long codigoFuncionario) {
		try {		
//			if(controlePontoCadastro.getStatus().equals("ABERTO")) {
				ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();			
				listaControlePontoDetalhes = controlePontoDetalheDAO.listar(competencia, codigoFuncionario);
				
				//EXCLUINDO DETALHES
				for (ControlePontoDetalhe controlePontoDetalheExcluir : listaControlePontoDetalhes) { 					
					controlePontoDetalheDAO.excluir(controlePontoDetalheExcluir);
				}
									
				ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
				controlePontoCadastro = controlePontoDAO.buscarPorCodigo(competencia, codigoFuncionario);
				
				//EXCLUINDO CAPA
				controlePontoDAO.excluir(controlePontoCadastro);
				
				FacesUtil.adicionarMsgInfo("Controle de Ponto removido com sucesso.");
				
//			}else {
//				FacesUtil.adicionarMsgAlerta("CONTROLE DE PONTO NÃO PODE SER REMOVIDO.");
//			}			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Controle de Ponto:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();		
			controlePontoDAO.editar(controlePontoCadastro);

			FacesUtil.adicionarMsgInfo("Controle de Ponto editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Controle de Ponto:" + ex.getMessage());
		}	
	}
	
	//GERA O CONTROLE DE PONTOS DE TODOS OS FUNCIONÁRIOS QUE NÃO SÃO LIDERANÇA PARA A COMPETÊNCIA ATUAL.
	public void gerarPontoGeral(String competencia) {
	
		Boolean exibirUmaVez = true;
		Integer bancoHorasUltimaCompetencia = 0;
		for (Funcionario func : listaFuncionarios) { 
			try {
				ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
				
				//CHECA SE EXISTE JÁ PONTO, CASO EXISTE, NÃO CRIA, CONTINUA, CASO NÃO EXISTA, AÍ PASSA E VAI CRIAR
				if(controlePontoDAO.checaSeExisteControlePonto(competencia, func.getCodigo())) {
					continue;
		    	}
		    						
				if(func.isLideranca()) {
					continue;
				}
				
				controlePontoCadastro.setStatus("ABERTO");

				switch (competencia.substring(0,2)) {
			    	case "01":
			    		controlePontoCadastro.setMes("JANEIRO");
			    		break;
			    	case "02":
			    		controlePontoCadastro.setMes("FEVEREIRO");
			    		break;
			    	case "03":
			    		controlePontoCadastro.setMes("MARÇO");
			    		break;
			    	case "04":
			    		controlePontoCadastro.setMes("ABRIL");
			    		break;
				    case "05":
				    	controlePontoCadastro.setMes("MAIO");
				    	break;
				    case "06":
				    	controlePontoCadastro.setMes("JUNHO");
				    	break;
				    case "07":
				    	controlePontoCadastro.setMes("JULHO");
				    	break;
				    case "08":
				    	controlePontoCadastro.setMes("AGOSTO");
				    	break;
				    case "09":
				    	controlePontoCadastro.setMes("SETEMBRO");
				    	break;
				    case "10":
				    	controlePontoCadastro.setMes("OUTUBRO");
				    	break;
				    case "11":
				    	controlePontoCadastro.setMes("NOVEMBRO");
				    	break;
				    default:
				    	controlePontoCadastro.setMes("DEZEMBRO");
				}
				
				controlePontoCadastro.setCompetencia(competencia);				
				
				ControlePontoDAO controlePontoBusca = new ControlePontoDAO();
				bancoHorasUltimaCompetencia = controlePontoBusca.PegaBancoHoraUltimaCompetencia(func.getCodigo());
				
				controlePontoCadastro.setBancoHora(bancoHorasUltimaCompetencia);				
				controlePontoCadastro.setAno(ano);
				controlePontoCadastro.setFuncionario(func);
				
				controlePontoDAO.salvar(controlePontoCadastro);	
				
				controlePontoDetalhe = new ControlePontoDetalhe();
				
				Calendar datas = new GregorianCalendar();
		        datas.set(Calendar.MONTH, Integer.parseInt(competencia.substring(1,2))-1);//2
		        int quantidadeDias = datas.getActualMaximum (Calendar.DAY_OF_MONTH);
				
//		        FeriadoDAO feriadoDAO = new FeriadoDAO();		        
		        
		        String dataMes;	       	     
		        for (int x = 1; x <= quantidadeDias; x++) {
		        	
		        	dataMes = (x + "/"+ competencia.substring(0,2) + "/" + controlePontoCadastro.getAno());	        		        	
		        	
		        	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		        	Date dataFormatada = formato.parse(dataMes);      
		        	
		        	controlePontoDetalheID = new ControlePontoDetalheID();
		        	controlePontoDetalheID.setCompetencia(controlePontoCadastro.getCompetencia());
		        	controlePontoDetalheID.setFuncionario(func);
		        	controlePontoDetalheID.setData(dataFormatada);
		        		
		        	//GRAVA FOLHA DETALHADA
		        	controlePontoDetalhe.setControlePontoCodigo(controlePontoCadastro.getCodigo());
		        	controlePontoDetalhe.setControlePontoDetalheID(controlePontoDetalheID);	     
		        	
		        	String horasTrabalhadas = "01/01/1970/00/00";
					
		        	String[] dataSeparada = horasTrabalhadas.split("/");
					LocalDateTime hoje = LocalDateTime.of(Integer.parseInt(dataSeparada[2]), Integer.parseInt(dataSeparada[1]), Integer.parseInt(dataSeparada[0]), Integer.parseInt(dataSeparada[3]), Integer.parseInt(dataSeparada[4]));	
					
		        	Date date1 = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());
		        	
		        	controlePontoDetalhe.setHoras_trabalhadas(date1);
		        			     
		        	controlePontoDetalhe.setDia(pegaDiaSemana(dataMes));
		        	controlePontoDetalhe.setBancoHora(0);
		        	
		        	
		        	//VERIFICA SE O DIA É FERIADO PARA GRAVAR COM FERIADO		        
/*		        	Feriado feriado = feriadoDAO.buscarPorData(dataFormatada);		      		        	
		        	if (feriado == null) {
		        		controlePontoDetalhe.setFeriado(false);
		        		controlePontoDetalhe.setOcorrencia(null);
		        	}else {
		        		controlePontoDetalhe.setFeriado(true);
		        		controlePontoDetalhe.setOcorrencia("FERIADO");
		        		
		        	}*/

		        	ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();	
		        	controlePontoDetalheDAO.salvar(controlePontoDetalhe);
		        }	     		
				
				controlePontoCadastro = new ControlePonto();
				controlePontoDetalhe = new ControlePontoDetalhe();
				controlePontoDetalheID = new ControlePontoDetalheID();
				
				if(exibirUmaVez == true) {
					exibirUmaVez = false;
					FacesUtil.adicionarMsgInfo("Controle de Ponto salvo com sucesso");
				}
			}catch (Exception ex) {
				exibirUmaVez = false;
				System.out.println("ERRO: " + ex);
				ex.printStackTrace();
				
				FacesUtil.adicionarMsgErro("Erro ao salvar Controle de Ponto:" + ex.getMessage());
			}
		}
	}
	
	public String exibeBancoHoras(Long codigoUsuario) {
		
	    ControlePontoDAO controlePontoDAO = new ControlePontoDAO();	        
	    String totalBancoHoras = controlePontoDAO.verificaFechamentoPontoBancoHora(codigoUsuario);
	        
	    return totalBancoHoras;    
	}
	
	//LISTA ULTIMOS 5 PONTOS FECHADOS
	public void listarFechado(Long codigoFuncionario) {
		try {
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();		
			listarFechado = controlePontoDAO.listarFechado(codigoFuncionario);			
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar controle de Ponto fechado:" + ex.getMessage());
		}	
	}
}
