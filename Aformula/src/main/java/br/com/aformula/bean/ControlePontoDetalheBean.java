package br.com.aformula.bean;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.aformula.dao.ControlePontoDAO;
import br.com.aformula.dao.ControlePontoDetalheDAO;
import br.com.aformula.dao.OcorrenciaDAO;
import br.com.aformula.domain.ControlePonto;
import br.com.aformula.domain.ControlePontoDetalhe;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.domain.Ocorrencia;
import br.com.aformula.relatorio.Relatorio;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class ControlePontoDetalheBean {

	public ControlePontoDetalhe controlePontoDetalheCadastro;
	private List<ControlePontoDetalhe> listaControlePontoDetalhes;
	private List<ControlePontoDetalhe> listaControlePontoDetalhesFiltrados;
	
	private List<Funcionario> listaFuncionarios;
	private String competencia;
	private Long codigo;
	private Date data;
	private Integer totalBancoHorasMes = 0;
	private String totalBancoHorasMesTexto;
	private Integer bancoHoraGravadaAntes = 0;
	
	private List<Ocorrencia> listaOcorrencias;
	
	public ControlePontoDetalhe getControlePontoDetalheCadastro() {
		return controlePontoDetalheCadastro;
	}

	public void setControlePontoDetalheCadastro(ControlePontoDetalhe controlePontoDetalheCadastro) {
		this.controlePontoDetalheCadastro = controlePontoDetalheCadastro;
	}

	public List<ControlePontoDetalhe> getListaControlePontoDetalhes() {
		return listaControlePontoDetalhes;
	}

	public void setListaControlePontoDetalhes(List<ControlePontoDetalhe> listaControlePontoDetalhes) {
		this.listaControlePontoDetalhes = listaControlePontoDetalhes;
	}

	public List<ControlePontoDetalhe> getListaControlePontoDetalhesFiltrados() {
		return listaControlePontoDetalhesFiltrados;
	}

	public void setListaControlePontoDetalhesFiltrados(List<ControlePontoDetalhe> listaControlePontoDetalhesFiltrados) {
		this.listaControlePontoDetalhesFiltrados = listaControlePontoDetalhesFiltrados;
	}
	
	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
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

	public List<Ocorrencia> getListaOcorrencias() {
		return listaOcorrencias;
	}

	public void setListaOcorrencias(List<Ocorrencia> listaOcorrencias) {
		this.listaOcorrencias = listaOcorrencias;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Integer getTotalBancoHorasMes() {
		return totalBancoHorasMes;
	}

	public void setTotalBancoHorasMes(Integer totalBancoHorasMes) {
		this.totalBancoHorasMes = totalBancoHorasMes;
	}

	public String getTotalBancoHorasMesTexto() {
		return totalBancoHorasMesTexto;
	}

	public void setTotalBancoHorasMesTexto(String totalBancoHorasMesTexto) {
		this.totalBancoHorasMesTexto = totalBancoHorasMesTexto;
	}

	public void novo() {
		controlePontoDetalheCadastro = new ControlePontoDetalhe();
	}
	
	public void salvar() {
		try {
			ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();			
			controlePontoDetalheDAO.salvar(controlePontoDetalheCadastro);	
			controlePontoDetalheCadastro = new ControlePontoDetalhe();
			
			FacesUtil.adicionarMsgInfo("Controle de Ponto salvo com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar Controle de Ponto:" + ex.getMessage());
		}
	}	
	
	public void carregarPesquisa() {
		try {						
			
//			System.out.println("VEJA: ");
			ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();
			listaControlePontoDetalhes = controlePontoDetalheDAO.listar(competencia, codigo);	
			
			for (ControlePontoDetalhe detalhe : listaControlePontoDetalhes) {
				totalBancoHorasMes = totalBancoHorasMes + detalhe.getBancoHora();			
			}
			
//			System.out.println("TOTAL BANCO HORA: " + totalBancoHorasMes);
			
			int horas = totalBancoHorasMes / 60;
		    int minutos = totalBancoHorasMes % 60;
		    
		    totalBancoHorasMesTexto = String.format("%02d:%02d", horas, minutos);
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar Controle de Ponto Detalhado:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {			
			if (competencia != null && codigo != null && data != null) {
				ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();
				controlePontoDetalheCadastro = controlePontoDetalheDAO.buscarPorCodigo(competencia, codigo, data);			
			}else {
				controlePontoDetalheCadastro = new ControlePontoDetalhe();
			} 	
			
			OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
			listaOcorrencias = ocorrenciaDAO.listar();			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados do Controle de Ponto:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();
			controlePontoDetalheDAO.excluir(controlePontoDetalheCadastro);
			
			FacesUtil.adicionarMsgInfo("Controle de Ponto removido com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover Controle de Ponto:" + ex.getMessage());
		}
		
	}
	
	public void editar() {
		try {
			boolean horaEntradaSaidaErrada1 = false, horaEntradaSaidaErrada2 = false, horaEntradaSaidaErrada3 = false, horaEntradaSaidaErrada4 = false, horaEntradaSaidaErrada5 = false;
			long minutosDiaTrabalho = 480; //DIA DE 8 HORAS
			long minutosDiaTrabalhoSeisHoras = 360; //DIA DE 6 HORAS
			long saldoMinutos = 0L;
			long diferencaMin = 0L;
			long valorHora, valorMinuto;
			Integer bancoHoraGravadaAntes = 0;
			boolean jornada6horas = false;
			LocalDateTime dataHoraEntrada1 = null, dataHoraSaida1 = null, dataHoraEntrada2 = null, dataHoraSaida2 = null, dataHoraEntrada3 = null, dataHoraSaida3 = null;		
			LocalDateTime entrada01, entrada02,  entrada03,  saida01,  saida02,  saida03, entrada01_alterada, entrada02_alterada, entrada03_alterada, saida01_alterada, saida02_alterada, saida03_alterada;
			
			SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");				
			DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");	
			
			ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();												
			
			ControlePontoDetalhe controlePontoDetalheBusca = controlePontoDetalheDAO.buscarPorCodigo(controlePontoDetalheCadastro.getControlePontoDetalheID().getCompetencia(), controlePontoDetalheCadastro.getControlePontoDetalheID().getFuncionario().getCodigo(), controlePontoDetalheCadastro.getControlePontoDetalheID().getData());						
			
			bancoHoraGravadaAntes = controlePontoDetalheBusca.getBancoHora();
			
			if(controlePontoDetalheBusca.getEntrada1() != null) {
				entrada01 = LocalDateTime.parse(formatador.format(controlePontoDetalheBusca.getEntrada1()), f);
			}else {
				entrada01 = null;
			}
			
			if(controlePontoDetalheBusca.getEntrada2() != null) {
				entrada02 = LocalDateTime.parse(formatador.format(controlePontoDetalheBusca.getEntrada2()), f);
			}else {
				entrada02 = null;
			}
			
			if(controlePontoDetalheBusca.getEntrada3() != null) {
				entrada03 = LocalDateTime.parse(formatador.format(controlePontoDetalheBusca.getEntrada3()), f);
			}else {
				entrada03 = null;
			}
			
			if(controlePontoDetalheBusca.getSaida1() != null) {
				saida01 = LocalDateTime.parse(formatador.format(controlePontoDetalheBusca.getSaida1()), f);
			}else {
				saida01 = null;
			}
			
			if(controlePontoDetalheBusca.getSaida2() != null) {
				saida02 = LocalDateTime.parse(formatador.format(controlePontoDetalheBusca.getSaida2()), f);
			}else {
				saida02 = null;
			}
			
			if(controlePontoDetalheBusca.getSaida3() != null) {
				saida03 = LocalDateTime.parse(formatador.format(controlePontoDetalheBusca.getSaida3()), f);
			}else {
				saida03 = null;
			}
			
			//VALIDA ENTRADA1 MAIOR QUE SAIDA1
			if((controlePontoDetalheCadastro.getEntrada1() != null) && (controlePontoDetalheCadastro.getSaida1() != null)) {
				dataHoraEntrada1 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada1()), f);
				dataHoraSaida1 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida1()), f);

				diferencaMin = diferencaMin + Duration.between(dataHoraEntrada1, dataHoraSaida1).toMinutes();
				if(Duration.between(dataHoraEntrada1, dataHoraSaida1).toMinutes() < 0) {
					horaEntradaSaidaErrada1 = true;
				}else if (Duration.between(dataHoraEntrada1, dataHoraSaida1).toMinutes() >= 360) {
					//PARA JORNADAS DE 6 HORAS SEGUIDAS VAI CONTABILIZAR COMO DIA NORMAL E O QUE PASSAR É HORA EXTRA
					// E O QUE FALTA DE 6 HORAS VAI CONTAR COMO UM DIA DE 8 HORAS...
					jornada6horas = true;
				}			
			}
			
			//VALIDA ENTRADA2 MAIOR QUE SAIDA2
			if((controlePontoDetalheCadastro.getEntrada2() != null) && (controlePontoDetalheCadastro.getSaida2() != null)) {
				dataHoraEntrada2 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada2()), f);
				dataHoraSaida2 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida2()), f);

				diferencaMin = diferencaMin + Duration.between(dataHoraEntrada2, dataHoraSaida2).toMinutes();
				if(Duration.between(dataHoraEntrada2, dataHoraSaida2).toMinutes() < 0) {
					horaEntradaSaidaErrada2 = true;
				}
			}
			
			//VALIDA ENTRADA3 MAIOR QUE SAIDA3
			if((controlePontoDetalheCadastro.getEntrada3() != null) && (controlePontoDetalheCadastro.getSaida3() != null)) {
				dataHoraEntrada3 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada3()), f);
				dataHoraSaida3 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida3()), f);

				diferencaMin = diferencaMin + Duration.between(dataHoraEntrada3, dataHoraSaida3).toMinutes();
				if(Duration.between(dataHoraEntrada3, dataHoraSaida3).toMinutes() < 0) {
					horaEntradaSaidaErrada3 = true;
				}
			}
			
			//VALIDA ENTRADA2 MENOR QUE SAIDA1 - NÃO ENTRA A VARIAVEL diferencaMin 
			if((controlePontoDetalheCadastro.getSaida1() != null) && (controlePontoDetalheCadastro.getEntrada2() != null)) {
				dataHoraSaida1 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida1()), f);
				dataHoraEntrada2 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada2()), f);
				
				if(Duration.between(dataHoraSaida1, dataHoraEntrada2).toMinutes() < 0) {
					horaEntradaSaidaErrada4 = true;
				}
			}
			
			//VALIDA ENTRADA2 MENOR QUE SAIDA1 - NÃO ENTRA A VARIAVEL diferencaMin 
			if((controlePontoDetalheCadastro.getSaida2() != null) && (controlePontoDetalheCadastro.getEntrada3() != null)) {
				dataHoraSaida2 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida2()), f);
				dataHoraEntrada3 = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada3()), f);

				if(Duration.between(dataHoraSaida2, dataHoraEntrada3).toMinutes() < 0) {
					horaEntradaSaidaErrada5 = true;
				}
			}		

			//VALIDANDO O SÁBADO
			if(!controlePontoDetalheCadastro.getDia().toString().equals("Sábado")) {
				if(jornada6horas == false) {
					saldoMinutos = diferencaMin - minutosDiaTrabalho;// VAI CONSIDERAR 480 MINUTOS O DIA QUE NÃO É SABADO
				}else {
					saldoMinutos = diferencaMin - minutosDiaTrabalhoSeisHoras;// VAI CONSIDERAR 360 MINUTOS O DIA QUE NÃO É SABADO
				}	
			}else {
				saldoMinutos = diferencaMin - (minutosDiaTrabalho/2); //VAI CONSIDERAR 240 MINUTOS NO SABADO
			}
			
			valorHora = diferencaMin / 60;
			valorMinuto = diferencaMin % 60;
			
			//SE TUDO ESTIVER SEM VALOR, ZERA O SALDO EM MINUTOS
			if ((controlePontoDetalheCadastro.getEntrada1() == null) && (controlePontoDetalheCadastro.getEntrada2() == null) && (controlePontoDetalheCadastro.getEntrada2() == null) && 
				(controlePontoDetalheCadastro.getSaida1() == null) && (controlePontoDetalheCadastro.getSaida2() == null) && (controlePontoDetalheCadastro.getSaida3() == null)) {
				saldoMinutos = 0;
			}
						
			String horasTrabalhadas = "01/01/1970/" + String.format("%02d", valorHora) + "/" + String.format("%02d", valorMinuto);
			String[] dataSeparada = horasTrabalhadas.split("/");
			LocalDateTime hoje = LocalDateTime.of(Integer.parseInt(dataSeparada[2]), Integer.parseInt(dataSeparada[1]), Integer.parseInt(dataSeparada[0]), Integer.parseInt(dataSeparada[3]), Integer.parseInt(dataSeparada[4]));	
			
			Date date1 = Date.from(hoje.atZone(ZoneId.systemDefault()).toInstant());
			
			controlePontoDetalheCadastro.setHoras_trabalhadas(date1);
			
			controlePontoDetalheCadastro.setBancoHora((int) saldoMinutos);			
			
			if((horaEntradaSaidaErrada1 == false) && (horaEntradaSaidaErrada2 == false) && (horaEntradaSaidaErrada3 == false) && (horaEntradaSaidaErrada4 == false) && (horaEntradaSaidaErrada5 == false)) {				
											
				if(controlePontoDetalheCadastro.getEntrada1() != null) {
					entrada01_alterada = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada1()), f);
				}else {
					entrada01_alterada = null;
				}
				
				if(controlePontoDetalheCadastro.getEntrada2() != null) {
					entrada02_alterada = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada2()), f);
				}else {
					entrada02_alterada = null;
				}
				
				if(controlePontoDetalheCadastro.getEntrada3() != null) {
					entrada03_alterada = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getEntrada3()), f);
				}else {
					entrada03_alterada = null;
				}
				
				if(controlePontoDetalheCadastro.getSaida1() != null) {
					saida01_alterada = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida1()), f);
				}else {
					saida01_alterada = null;
				}
				
				if(controlePontoDetalheCadastro.getSaida2() != null) {
					saida02_alterada = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida2()), f);
				}else {
					saida02_alterada = null;
				}
				
				if(controlePontoDetalheCadastro.getSaida3() != null) {
					saida03_alterada = LocalDateTime.parse(formatador.format(controlePontoDetalheCadastro.getSaida3()), f);
				}else {
					saida03_alterada = null;
				}
				
				// SE A DATA1 E DATA1_ALTERADA FOREM IGUAIS, DÁ TRUE E SE FOREM NULAS, DÁ TRUE, SE UMA DELAS FOR NULL OU SE FOREM DIFERENTES, DÁ FALSE
				if (Objects.equals(entrada01, entrada01_alterada) == false || Objects.equals(entrada02, entrada02_alterada) == false || Objects.equals(entrada03, entrada03_alterada) == false || Objects.equals(saida01, saida01_alterada) == false || Objects.equals(saida02, saida02_alterada) == false || Objects.equals(saida03, saida03_alterada) == false) {

					//EDITA OS DETALHES
					controlePontoDetalheDAO.editar(controlePontoDetalheCadastro);
					
					//AQUI VAI RECAREGAR A PESQUISA JÁ COM OS VALORES EDITADOS, POR EXEMPLO O BANCO DE HORAS
					carregarPesquisa();
					
					//EDITA A CAPA DO PONTO E SETA A HORA DA CAPA SOMANDO TODOS AS HORAS DO DETALHE
					ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
					ControlePonto controlePonto = controlePontoDAO.buscarPorCodigo(controlePontoDetalheCadastro.getControlePontoDetalheID().getCompetencia(), controlePontoDetalheCadastro.getControlePontoDetalheID().getFuncionario().getCodigo());						
					
					controlePonto.setBancoHora(controlePonto.getBancoHora() + controlePontoDetalheCadastro.getBancoHora() + (-bancoHoraGravadaAntes));	
												
					controlePontoDAO.editar(controlePonto);
							
					FacesUtil.adicionarMsgInfo("Controle de Ponto editado com sucesso.");
				}
				
			}else {
				if ((horaEntradaSaidaErrada4 == true) || (horaEntradaSaidaErrada5 == true)) {
					FacesUtil.adicionarMsgErro("HORA DE ENTRADA NÃO PODE SER MENOR QUE SAÍDA ANTERIOR.");
				}else {
					FacesUtil.adicionarMsgErro("HORA DE SAÍDA NÃO PODE SER MENOR QUE ENTRADA.");
				}		
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Controle de Ponto." + ex.getMessage());
		}
		
	}
	
	
	public void validarControlePonto() throws IOException {
		Integer totalBancoHoras = 0;
		String diaDaSemana;
		
		ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();
		
		for (ControlePontoDetalhe controlePontoDetalhe : listaControlePontoDetalhes) { 	    
			//PEGA O DIA DA SEMANA
			diaDaSemana = controlePontoDetalhe.getDia().toString();
			
			//SE FOR DOMINGO, PASSA PARA O PRÓXIMO DIA
			if(diaDaSemana.equals("Domingo")) {
				continue;
			}
			
			if(controlePontoDetalhe.getOcorrencia() != null) {
				if(controlePontoDetalhe.getOcorrencia().getQuantidadeHoras().equals(8)) {
					continue;
				}
				
				if((diaDaSemana.equals("Sábado")) && (controlePontoDetalhe.getOcorrencia().getQuantidadeHoras().equals(4))) {
					continue;
				}
				
				//ESSE CASO PEGA PARA OS DIAS DE 6HORAS QUE TEM FOLGA, EXEMPLO FOI O MICARETA EM 02/05/2025
				if((controlePontoDetalhe.getEntrada1() == null) &&(controlePontoDetalhe.getOcorrencia().getQuantidadeHoras().equals(6))) {
					continue;
				}
			}			

			try {  						
				if((!(controlePontoDetalhe.getEntrada1() == null) && (controlePontoDetalhe.getSaida1() == null)) || 
				   (!(controlePontoDetalhe.getEntrada2() == null) && (controlePontoDetalhe.getSaida2() == null)) || 
				   (!(controlePontoDetalhe.getEntrada3() == null) && (controlePontoDetalhe.getSaida3() == null))) {
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String dataFormatada = sdf.format(controlePontoDetalhe.getControlePontoDetalheID().getData());
					
					FacesUtil.adicionarMsgErro("PONTO NÃO VALIDADO - PENDÊNCIA DE DATA: " + dataFormatada);					
				}else {	
					// SE FOR SÁBADO CONTABILIZA -240 MINUTOS
					if(diaDaSemana.equals("Sábado")) {
						if((controlePontoDetalhe.getEntrada1() == null) && (controlePontoDetalhe.getEntrada2() == null) && (controlePontoDetalhe.getEntrada3() == null)) {
							controlePontoDetalhe.setBancoHora(-240);	
							totalBancoHoras = totalBancoHoras + 240;
							controlePontoDetalheDAO.editar(controlePontoDetalhe);
						}
					}else {
						// SE FOR QUALQUER OUTRO DIA CONTABILIZA -480 MINUTOS
						if((controlePontoDetalhe.getEntrada1() == null) && (controlePontoDetalhe.getEntrada2() == null) && (controlePontoDetalhe.getEntrada3() == null)) {
							controlePontoDetalhe.setBancoHora(-480);
							totalBancoHoras = totalBancoHoras + 480;
							controlePontoDetalheDAO.editar(controlePontoDetalhe);
						}
					}					
				}
			}catch (Exception ex) {
				ex.printStackTrace();
				FacesUtil.adicionarMsgErro("Erro ao salvar Controle de Ponto:" + ex.getMessage());
			}
		}
		
        //EDITA A CAPA DO PONTO
		ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
		ControlePonto controlePonto = controlePontoDAO.buscarPorCodigo(competencia, codigo);
		controlePonto.setBancoHora(controlePonto.getBancoHora() - totalBancoHoras);
		controlePonto.setStatus("FECHADO");	
		
		controlePontoDAO.editar(controlePonto);
		FacesUtil.adicionarMsgInfo("CONTROLE DE PONTO VALIDADO COM SUCESSO");	
		FacesContext.getCurrentInstance().getExternalContext().redirect("controlePonto.xhtml");
	}
	
	public void gerarRelatorioControlePonto() {
	    Relatorio relatorio = new Relatorio();    
	    relatorio.getRelatorio(listaControlePontoDetalhes, codigo, totalBancoHorasMesTexto);
	}
	
	public void limparDados(String competencia, Long codigo, Date data) {
		try {					
			ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();											
			controlePontoDetalheCadastro = controlePontoDetalheDAO.buscarPorCodigo(competencia, codigo, data);			
			
			//EDITA A CAPA DO PONTO
			ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
			ControlePonto controlePonto = controlePontoDAO.buscarPorCodigo(competencia, codigo);
						
			controlePontoDetalheCadastro.setEntrada1(null);
			controlePontoDetalheCadastro.setSaida1(null);
			controlePontoDetalheCadastro.setEntrada2(null);
			controlePontoDetalheCadastro.setSaida2(null);
			controlePontoDetalheCadastro.setEntrada3(null);
			controlePontoDetalheCadastro.setSaida3(null);	
			
			 // Cria uma instância de Calendar para o dia atual
	        Calendar calendar = Calendar.getInstance();
	        
	        // Reseta a hora, minuto, segundo e milissegundo para garantir 00:00:00
	        calendar.set(Calendar.HOUR_OF_DAY, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
	        
	        // Obtém a data com hora zero (00:00:00)
	        Date horaZero = calendar.getTime();
	        
	        // Atribui a data com hora zero ao campo horas_trabalhadas
	        controlePontoDetalheCadastro.setHoras_trabalhadas(horaZero);	        
	        
	        // SE TIVER OCORRÊNCIA, ESTÁ FAZNEDO CERTO, DEBITA SE A OCORRENCIA FOR DE DEBITAR E NÃO FAZ NADA SE FOR DE ABONAR
			if(controlePontoDetalheCadastro.getOcorrencia() != null) {
				controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora()));
			}else {//SE FOR UM DIA QUALQUER TRABALHADO, SEM OCORRÊNCIA, AÍ VAI SOMAR OU SUBTRAIR O BANCO DE HORAS DO DIA, NA CADA
				controlePonto.setBancoHora(controlePonto.getBancoHora() - (controlePontoDetalheCadastro.getBancoHora()));
			}
				
			controlePontoDetalheCadastro.setBancoHora(0);
			controlePontoDetalheCadastro.setOcorrencia(null);
			
			controlePontoDetalheDAO.editar(controlePontoDetalheCadastro);				
			controlePontoDAO.editar(controlePonto);			
			
			FacesUtil.adicionarMsgInfo("Controle de Ponto editado com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Controle de Ponto." + ex.getMessage());
		}		
	}
	
	public void registrarOcorrencia() {				
		
		if (controlePontoDetalheCadastro.getOcorrencia() == null) {
			FacesUtil.adicionarMsgAlerta("OCORRÊNCIA DEVE SER SELECIONADA");
			return;
		}
	
		ControlePontoDetalheDAO controlePontoDetalheDAO = new ControlePontoDetalheDAO();																									
		ControlePontoDetalhe controlePontoDetalheBusca = controlePontoDetalheDAO.buscarPorCodigo(competencia, codigo, data);
		
		//EDITA A CAPA DO PONTO
		ControlePontoDAO controlePontoDAO = new ControlePontoDAO();
		ControlePonto controlePonto = controlePontoDAO.buscarPorCodigo(competencia, codigo);
		
		bancoHoraGravadaAntes = controlePontoDetalheBusca.getBancoHora();

		try {		
			if(controlePontoDetalheCadastro.getOcorrencia().getAbonarDescontar().equals("ABONAR")) {
//				System.out.println("ABONAR");				
				if(controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(8)) {					
					if(controlePontoDetalheCadastro.getDia().equals("Sábado")) {						
						FacesUtil.adicionarMsgAlerta("OCORRÊNCIA COM HORAS MAIOR QUE O DIA");
						return;
					}
					
			        Calendar calendar = Calendar.getInstance();
			        calendar.set(Calendar.HOUR_OF_DAY, 0);
			        calendar.set(Calendar.MINUTE, 0);
			        calendar.set(Calendar.SECOND, 0);
			        calendar.set(Calendar.MILLISECOND, 0);
			        Date horaZero = calendar.getTime();
		
			        controlePontoDetalheCadastro.setHoras_trabalhadas(horaZero);
			        
			        controlePontoDetalheCadastro.setEntrada1(null);
			        controlePontoDetalheCadastro.setEntrada2(null);
			        controlePontoDetalheCadastro.setEntrada3(null);
			        controlePontoDetalheCadastro.setSaida1(null);
			        controlePontoDetalheCadastro.setSaida2(null);
			        controlePontoDetalheCadastro.setSaida3(null);		           
			       
			        controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora())); 
			        controlePontoDetalheCadastro.setBancoHora(0);
				}else {
					if(controlePontoDetalheCadastro.getDia().equals("Sábado")) {						
						if(controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() > 4) {						
							FacesUtil.adicionarMsgAlerta("OCORRÊNCIA COM HORAS MAIOR QUE O DIA");
							return;
						}	
						
						// SE O SÁBADO JÁ TIVER COM AS 4 HORAS COMPLETAS OU MAIS (BANCO DE HORA POSITIVO), NÃO PERMITE ABONO
						if ((controlePontoDetalheCadastro.getBancoHora() > 0) && (controlePontoDetalheCadastro.getOcorrencia().getGeraHoraExtra() == false)){
							FacesUtil.adicionarMsgAlerta("ABONO NÃO PERMITIDO, DIA COM HORÁRIO EXCEDENTE.");
							return;
						}
										
						if(controlePontoDetalheCadastro.getOcorrencia().getGeraHoraExtra() == false) {
							//SE O QUE PRECISA SER ABONA FOR MENOR QUE A HORA DA OCORRENCIA, ABATE TODO A DIFERENCA DO QUE FALTA, ZERANDO A FALTA DO FUNCIONÁRIO
							if (-(controlePontoDetalheCadastro.getBancoHora()) <= (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras()*60)) {
								controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora()));
								controlePontoDetalheCadastro.setBancoHora(0);						
							}else {										
								controlePontoDetalheCadastro.setBancoHora(controlePontoDetalheCadastro.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));
								controlePonto.setBancoHora(controlePonto.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));								
							}
						}else {								
							controlePontoDetalheCadastro.setBancoHora(controlePontoDetalheCadastro.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));
							controlePonto.setBancoHora(controlePonto.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));								
						}								
					}else {
						//QUANDO ENTRA AQUI, O ABONO JÁ TEM MENOS DE 8 HORAS ENTÃO OBRIGATORIAMENTE DEVE TER ALGUM HORARIO DE ENTRADA, VAMOS VALIDAR ISSO
						if(controlePontoDetalheCadastro.getEntrada1() == null) {
							FacesUtil.adicionarMsgAlerta("OBRIGATÓRIO REGISTRO DE ENTRADA E SAÍDA");
							return;
						}
						
						// SE UM DIA NORMAL JÁ TIVER COM AS 8 HORAS COMPLETAS OU MAIS (BANCO DE HORA POSITIVO OU ZERADO), NÃO PERMITE ABONO
						if ((controlePontoDetalheCadastro.getBancoHora() > 0) && (controlePontoDetalheCadastro.getOcorrencia().getGeraHoraExtra() == false)){
							FacesUtil.adicionarMsgAlerta("ABONO NÃO PERMITIDO, DIA COM HORÁRIO COMPLETO OU EXCEDENTE.");
							return;
						}
						
						if (controlePontoDetalheCadastro.getOcorrencia().getGeraHoraExtra() == false) {
							//SE O QUE PRECISA SER ABONA FOR MENOR QUE A HORA DA OCORRENCIA, ABATE TODO A DIFERENCA DO QUE FALTA, ZERANDO A FALTA DO FUNCIONÁRIO	
							if (-(controlePontoDetalheCadastro.getBancoHora()) <= (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras()* 60)) {
								controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora()));
								controlePontoDetalheCadastro.setBancoHora(0);
							}else {									
								controlePontoDetalheCadastro.setBancoHora(controlePontoDetalheCadastro.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));
								controlePonto.setBancoHora(controlePonto.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));
							}
						}else {							
							controlePontoDetalheCadastro.setBancoHora(controlePontoDetalheCadastro.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));
							controlePonto.setBancoHora(controlePonto.getBancoHora() + (controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras() * 60));
						}											
					}
				}				
			}else {
//				System.out.println("DESCONTAR");
				// UMA OBSERVAÇÃO FEITA É QUE PARA DESCONTO SÓ PODE SER O DIA TODO POIS SE NÃO FOR, DEVE TER ALGUM LANÇAMENTO E SE TIVER ALGUM LANÇAMENTO, ESSE DESCONTO JÁ FAZ AUTOMATICAMENTE
				
				if((!controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(8)) && (!controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(6)) && (!controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(4))) {						
					FacesUtil.adicionarMsgAlerta("OCORRÊNCIA DE DESCONTO DEVE TER 4, 6 OU 8 HORAS");
					return;
				}
				
				Calendar calendar = Calendar.getInstance();
		        calendar.set(Calendar.HOUR_OF_DAY, 0);
		        calendar.set(Calendar.MINUTE, 0);
		        calendar.set(Calendar.SECOND, 0);
		        calendar.set(Calendar.MILLISECOND, 0);
		        Date horaZero = calendar.getTime();
	
		        controlePontoDetalheCadastro.setHoras_trabalhadas(horaZero);
		        
		        controlePontoDetalheCadastro.setEntrada1(null);
		        controlePontoDetalheCadastro.setEntrada2(null);
		        controlePontoDetalheCadastro.setEntrada3(null);
		        controlePontoDetalheCadastro.setSaida1(null);
		        controlePontoDetalheCadastro.setSaida2(null);
		        controlePontoDetalheCadastro.setSaida3(null);	
				
				if(controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(8)) {				
					if(controlePontoDetalheCadastro.getDia().equals("Sábado")) {						
						FacesUtil.adicionarMsgAlerta("OCORRÊNCIA COM HORAS MAIOR QUE O DIA");
						return;
					}
					
			        controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora() -480));        		
			        controlePontoDetalheCadastro.setBancoHora(-480);
			
				}else if(controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(6)) {	
					
					controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora() -360));        		
			        controlePontoDetalheCadastro.setBancoHora(-360);	           
				
				}else {//AQUI VAI SER O DIA DE SÁBADO OU OCORRENCIA DE 4 HORAS
					if(!controlePontoDetalheCadastro.getOcorrencia().getQuantidadeHoras().equals(4)) {						
						FacesUtil.adicionarMsgAlerta("OCORRÊNCIA NÃO TEM A QUANTIDADE DE HORAS DO DIA");
						return;
					}
					
					if(!controlePontoDetalheCadastro.getDia().equals("Sábado")) {						
						FacesUtil.adicionarMsgAlerta("OCORRÊNCIA DE DESCONTO DEVE TER A QUANTIDADE DE HORAS DO DIA");
						return;
					}					           
			       
			        controlePonto.setBancoHora(controlePonto.getBancoHora() + (-controlePontoDetalheCadastro.getBancoHora() -240));        		
			        controlePontoDetalheCadastro.setBancoHora(-240);
				}
			}
			
			controlePontoDetalheDAO.editar(controlePontoDetalheCadastro);				
			controlePontoDAO.editar(controlePonto);
	         
			FacesUtil.adicionarMsgInfo("Controle de Ponto editado com sucesso.");			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar Controle de Ponto." + ex.getMessage());
		}		
	}
	
	
	public void validarHora() {		
	    if (controlePontoDetalheCadastro.getEntrada1() != null) {        
	    	Date hora = controlePontoDetalheCadastro.getEntrada1();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(hora);
	        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        int minute = calendar.get(Calendar.MINUTE);

	        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Hora inválida.");
	            FacesContext.getCurrentInstance().addMessage("", msg);
//	            System.out.println("MENSAGEM: " + msg);
	        }
	    }
	    
	    if (controlePontoDetalheCadastro.getEntrada2() != null) {        
	    	Date hora = controlePontoDetalheCadastro.getEntrada2();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(hora);
	        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        int minute = calendar.get(Calendar.MINUTE);

	        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Hora inválida.");
	            FacesContext.getCurrentInstance().addMessage("", msg);
//	            System.out.println("MENSAGEM: " + msg);
	        }
	    }
	    
	    if (controlePontoDetalheCadastro.getEntrada3() != null) {        
	    	Date hora = controlePontoDetalheCadastro.getEntrada3();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(hora);
	        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        int minute = calendar.get(Calendar.MINUTE);

	        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Hora inválida.");
	            FacesContext.getCurrentInstance().addMessage("", msg);
//	            System.out.println("MENSAGEM: " + msg);
	        }
	    }
	    
	    if (controlePontoDetalheCadastro.getSaida1() != null) {        
	    	Date hora = controlePontoDetalheCadastro.getSaida1();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(hora);
	        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        int minute = calendar.get(Calendar.MINUTE);

	        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Hora inválida.");
	            FacesContext.getCurrentInstance().addMessage("", msg);
//	            System.out.println("MENSAGEM: " + msg);
	        }
	    }
	    
	    if (controlePontoDetalheCadastro.getSaida2() != null) {        
	    	Date hora = controlePontoDetalheCadastro.getSaida2();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(hora);
	        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        int minute = calendar.get(Calendar.MINUTE);

	        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Hora inválida.");
	            FacesContext.getCurrentInstance().addMessage("", msg);
//	            System.out.println("MENSAGEM: " + msg);
	        }
	    }
	    
	    if (controlePontoDetalheCadastro.getSaida3() != null) {        
	    	Date hora = controlePontoDetalheCadastro.getSaida3();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(hora);
	        int hour = calendar.get(Calendar.HOUR_OF_DAY);
	        int minute = calendar.get(Calendar.MINUTE);

	        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Hora inválida.");
	            FacesContext.getCurrentInstance().addMessage("", msg);
//	            System.out.println("MENSAGEM: " + msg);
	        }
	    }
	}
	
}
