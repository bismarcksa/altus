package br.com.aformula.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.aformula.dao.BairroDAO;
import br.com.aformula.dao.CidadeDAO;
import br.com.aformula.dao.EntregaDAO;
import br.com.aformula.dao.EntregadorDAO;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.domain.Bairro;
import br.com.aformula.domain.Cidade;
import br.com.aformula.domain.Entrega;
import br.com.aformula.domain.Entregador;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class EntregaBean {

	public Entrega entregaCadastro;
	private List<Entrega> listaEntregas;
	private List<Entrega> listaEntregasFiltrados;
	private String acao;
	private Long codigo;
	private List<Funcionario> listaFuncionariosAtendente;
	private List<Entregador> listaEntregadores;
	private List<Bairro> listaBairrosEntrega;
	private List<Cidade> listaCidades;

    private List<Entrega> entregasSelecionadas;
  
	//VARIAVEL QUE DEFINE O DIA DE HOJE PARA NÃO PERMITIR GRAVAR UM DIA ANTERIOR AO DIA ATUAL
    private Date hoje;
    
    private Double valor_dav_alterado, valor_receber_alterado;
    
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	
	public Entrega getEntregaCadastro() {
		return entregaCadastro;
	}

	public void setEntregaCadastro(Entrega entregaCadastro) {
		this.entregaCadastro = entregaCadastro;
	}

	public List<Entrega> getListaEntregas() {
		return listaEntregas;
	}

	public void setListaEntregas(List<Entrega> listaEntregas) {
		this.listaEntregas = listaEntregas;
	}

	public List<Entrega> getListaEntregasFiltrados() {
		return listaEntregasFiltrados;
	}

	public void setListaEntregasFiltrados(List<Entrega> listaEntregasFiltrados) {
		this.listaEntregasFiltrados = listaEntregasFiltrados;
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

	public List<Entregador> getListaEntregadores() {
		return listaEntregadores;
	}

	public void setListaEntregadores(List<Entregador> listaEntregadores) {
		this.listaEntregadores = listaEntregadores;
	}

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public List<Bairro> getListaBairrosEntrega() {
		return listaBairrosEntrega;
	}

	public void setListaBairrosEntrega(List<Bairro> listaBairrosEntrega) {
		this.listaBairrosEntrega = listaBairrosEntrega;
	}

	public List<Funcionario> getListaFuncionariosAtendente() {
		return listaFuncionariosAtendente;
	}

	public void setListaFuncionariosAtendente(List<Funcionario> listaFuncionariosAtendente) {
		this.listaFuncionariosAtendente = listaFuncionariosAtendente;
	}
	
	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}
    
    public List<Entrega> getEntregasSelecionadas() {
		return entregasSelecionadas;
	}

	public void setEntregasSelecionadas(List<Entrega> entregasSelecionadas) {
		this.entregasSelecionadas = entregasSelecionadas;
	}

	public EntregaBean() {
        hoje = new Date(); // Inicializa com a data atual
    }

	public Date getHoje() {
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}	

	public void novo() {
		entregaCadastro = new Entrega();
	}
	
	public void salvar() {
		try {
			EntregaDAO entregaDAO = new EntregaDAO();				
			
			//DATA EM QUE FOI FEITO O LANÇAMENTO - COM O HORÁRIO ZERADO
			Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.HOUR_OF_DAY, 0);
		    cal.set(Calendar.MINUTE, 0);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 0);

		    Date dataSemHora = cal.getTime();
			entregaCadastro.setData_lancamento(dataSemHora);
			
			//SETA COM PENDENTE
			if (!entregaCadastro.getEntregador().getVinculo().toString().equals("CELETISTA")) {
				entregaCadastro.setStatus("BLOQUEADO");
			}else {
				entregaCadastro.setStatus("PENDENTE");
			}
			
			//SETA FUNCIONÁRIO QUE FEZ O REGISTRO
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			entregaCadastro.setUsuario(funcionario);			
			
			if(entregaCadastro.getEntregador().getVinculo().toString().equals("CELETISTA")) {
				entregaCadastro.setAutorizacao_entrega(true);
			}
			
			if ((entregaCadastro.getEntregador() == null)) {
				FacesUtil.adicionarMsgAlerta("ENTREGADOR DEVE SER PREENCHIDO.");
				return;
			}
			
			
			if (entregaCadastro.getValor_dav().floatValue() == 0) {
				FacesUtil.adicionarMsgAlerta("VALOR DO DAV NÃO PODE SER ZERADO");
				return;
			}
			
			if ((entregaCadastro.getEntregador().isTransportadora() == false) && (entregaCadastro.getBairro() == null)) {
				FacesUtil.adicionarMsgAlerta("BAIRRO DEVE SER PREENCHIDO");
				return;
			}
			
			
			if (entregaCadastro.getEntregador().isTransportadora() && (entregaCadastro.getCidade() == null)) {
				FacesUtil.adicionarMsgAlerta("TRANSPORTADORA DEVE TER CIDADE ");
				return;
			}
			
			if ((entregaCadastro.getEntregador().isTransportadora() == false) && (entregaCadastro.getCidade() != null)) {
				FacesUtil.adicionarMsgAlerta("ENTREGADOR NÃO PODE TER CIDADE");
				return;
			}
			
			if ((entregaCadastro.getEntregador().isTransportadora() == true) && (entregaCadastro.getBairro() != null)) {
				FacesUtil.adicionarMsgAlerta("TRANSPORTADORA NÃO PODE TER BAIRRO");
				return;
			}
					
			if (entregaCadastro.getValor_dav().floatValue() < entregaCadastro.getValor_receber().floatValue()) {
				FacesUtil.adicionarMsgAlerta("VALOR DO DAV NÃO PODE SER MENOR QUE O VALOR A RECEBER");
				return;
			}
			
			entregaDAO.salvar(entregaCadastro);			
			entregaCadastro = new Entrega();
			
			FacesUtil.adicionarMsgInfo("Entrega salva com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao salvar entrega:" + ex.getMessage());
		}	
	}
	
	public void carregarPesquisa() {
		try {
			EntregaDAO entregaDAO = new EntregaDAO();
			listaEntregas = entregaDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar entrega:" + ex.getMessage());
		}
	}
	
	public void carregarPesquisaEntregaExtra() {
		try {
			EntregaDAO entregaDAO = new EntregaDAO();
			listaEntregas = entregaDAO.listarEntregaExtra();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar entrega:" + ex.getMessage());
		}
	}
	
	public void carregarPesquisaEntregaFin() {
		try {
			EntregaDAO entregaDAO = new EntregaDAO();
			listaEntregas = entregaDAO.listarEntregaFin();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar entrega:" + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				EntregaDAO entregaDAO = new EntregaDAO();
				entregaCadastro = entregaDAO.buscarPorCodigo(codigo);
			}else {
				entregaCadastro = new Entrega();
			} 
			
			BairroDAO bairroDAO = new BairroDAO();
			listaBairrosEntrega = bairroDAO.listar();
			
			EntregadorDAO EntregadorDAO = new EntregadorDAO();
			listaEntregadores = EntregadorDAO.listar();
			
			CidadeDAO CidadeDAO = new CidadeDAO();
			listaCidades = CidadeDAO.listar();
			
			FuncionarioDAO funcAtendenteDAO = new FuncionarioDAO();
			listaFuncionariosAtendente = funcAtendenteDAO.listarAtendentes();
			
			valor_dav_alterado = entregaCadastro.getValor_dav();
			valor_receber_alterado = entregaCadastro.getValor_receber();
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da entrega:" + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			EntregaDAO entregaDAO = new EntregaDAO();
			entregaDAO.excluir(entregaCadastro);
			
			FacesUtil.adicionarMsgInfo("Entrega removida com sucesso");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover entrega:" + ex.getMessage());
		}		
	}
	
	public void editar() {
		try {
			EntregaDAO entregaDAO = new EntregaDAO();						
			
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario financeiro = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
			
			//VERIFICA SE FUNCIONARIO É FINANCEIRO
			if((financeiro.getFuncao().equals("FINANCEIRO") || financeiro.getFuncao().equals("GERENTE"))) {
				entregaCadastro.setFinanceiro(financeiro);
				
				Date DataFinanceiro = new Date();
				entregaCadastro.setData_financeiro(DataFinanceiro);
			}else {//VERIFICA SE FUNCIONARIO É LOGISTICA POR EXEMPLO, MESMA VALIDAÇÃO DO CADASTRAR
				if ((entregaCadastro.getEntregador() == null)) {
					FacesUtil.adicionarMsgAlerta("ENTREGADOR DEVE SER PREENCHIDO.");
					return;
				}
				
				
				if (entregaCadastro.getValor_dav().floatValue() == 0) {
					FacesUtil.adicionarMsgAlerta("VALOR DO DAV NÃO PODE SER ZERADO");
					return;
				}
				
				if ((entregaCadastro.getEntregador().isTransportadora() == false) && (entregaCadastro.getBairro() == null)) {
					FacesUtil.adicionarMsgAlerta("BAIRRO DEVE SER PREENCHIDO");
					return;
				}
				
				
				if (entregaCadastro.getEntregador().isTransportadora() && (entregaCadastro.getCidade() == null)) {
					FacesUtil.adicionarMsgAlerta("TRANSPORTADORA DEVE TER CIDADE ");
					return;
				}
				
				if ((entregaCadastro.getEntregador().isTransportadora() == false) && (entregaCadastro.getCidade() != null)) {
					FacesUtil.adicionarMsgAlerta("ENTREGADOR NÃO PODE TER CIDADE");
					return;
				}
				
				if ((entregaCadastro.getEntregador().isTransportadora() == true) && (entregaCadastro.getBairro() != null)) {
					FacesUtil.adicionarMsgAlerta("TRANSPORTADORA NÃO PODE TER BAIRRO");
					return;
				}
						
				if (entregaCadastro.getValor_dav().floatValue() < entregaCadastro.getValor_receber().floatValue()) {
					FacesUtil.adicionarMsgAlerta("VALOR DO DAV NÃO PODE SER MENOR QUE O VALOR A RECEBER");
					return;
				}				
			}

			if((entregaCadastro.getValor_dav().doubleValue() != valor_dav_alterado) && (entregaCadastro.getStatus().toString().equals("PENDENTE"))) {
				FacesUtil.adicionarMsgAlerta("STATUS DE DEVE SER ALTERADO QUANDO VALOR DAV É ALTERADO");
				return;
			}
				
			if((entregaCadastro.getValor_receber().doubleValue() != valor_receber_alterado) && (entregaCadastro.getStatus().toString().equals("PENDENTE"))){
				FacesUtil.adicionarMsgAlerta("STATUS DE DEVE SER ALTERADO, QUANDO VALOR A RECEBER É ALTERADO");
				return;
			}
				
			if (entregaCadastro.getValor_dav().doubleValue() < entregaCadastro.getValor_receber().doubleValue()) {
				FacesUtil.adicionarMsgAlerta("VALOR DO DAV NÃO PODE SER MENOR QUE O VALOR A RECEBER");
				return;
			}
				
			if(entregaCadastro.getValor_dav_alterado() == null) {
				entregaCadastro.setValor_dav_alterado(0d);
			}
				
			//SÓ GRAVA O VALOR ALTERADO SE FOR DIFERENTE DO NOVO VALOR
			if (!entregaCadastro.getValor_dav().equals(valor_dav_alterado)) {
				entregaCadastro.setValor_dav_alterado(valor_dav_alterado);
			}
				
			if(entregaCadastro.getValor_receber_alterado() == null) {
				entregaCadastro.setValor_receber_alterado(0d);
			}
				
			//SÓ GRAVA O VALOR ALTERADO SE FOR DIFERENTE DO NOVO VALOR
			if (!entregaCadastro.getValor_receber().equals(valor_receber_alterado)) {
				entregaCadastro.setValor_receber_alterado(valor_receber_alterado);
			}
			
			entregaDAO.editar(entregaCadastro);
			FacesUtil.adicionarMsgInfo("Entrega editada com sucesso");
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar entrega:" + ex.getMessage());
		}	
	}
	
	public void autorizarEntrega(Long codigo) {
		try {
		//BUSCA A LINHA NA TABELA 
		EntregaDAO entregaDAO = new EntregaDAO();
		entregaCadastro = entregaDAO.buscarPorCodigo(codigo);
		
		//SETA FUNCIONÁRIO QUE FEZ A AUTORIZAÇÃO
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario autorizador = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());				
		
		Date DataAutorizacao = new Date();
		
		entregaCadastro.setAutorizacao_entrega(true);
		entregaCadastro.setAutorizador(autorizador);
		entregaCadastro.setStatus("PENDENTE");
		entregaCadastro.setData_autorizacao(DataAutorizacao);
		
		entregaDAO.editar(entregaCadastro);
		FacesUtil.adicionarMsgInfo("Autorizado com sucesso.");
		FacesContext.getCurrentInstance().getExternalContext().redirect("entregaCoordenacao.xhtml");	
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao autorizar entrega:" + ex.getMessage());
		}			
	}	
		
	// Método para processar as entregas selecionadas
    public void processarRegistrosSelecionados() throws IOException {   	
    	
    	//VERIFICA SE NÃO TEM NENHUM FILTRO, OU SEJA, APENAS REGISTROS SELECIONADOS
    	if (listaEntregasFiltrados == null) {
            listaEntregasFiltrados = new ArrayList<>(entregasSelecionadas);
        }   	
    	
    	// Verifica se há registros selecionados e filtrados
        if (entregasSelecionadas != null && !entregasSelecionadas.isEmpty()) {

        	EntregaDAO entregaDAO = new EntregaDAO();
        	//VERIFICA SE FUNCIONARIO É FINANCEIRO
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario financeiro = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());
        	
        	
        	for (Entrega entrega : entregasSelecionadas) {
                // Processa apenas os registros que estão na lista filtrada
                if (listaEntregasFiltrados.contains(entrega)) {
                    
                	if(entrega.getStatus().toString().equals("PENDENTE")) {
	                	// Lógica de processamento para cada registro selecionado e filtrado 
	                     
	        			entrega.setFinanceiro(financeiro);
	        				
	        			Date DataFinanceiro = new Date();
	        			entrega.setData_financeiro(DataFinanceiro);
	        			
	        			entrega.setStatus("REALIZADA");
	       			
	        			entregaDAO.editar(entrega);
                	}
                }
            }
        	
        	FacesUtil.adicionarMsgInfo("PROCESSAMENTO DE ENTREGAS REALIZADO COM SUCESSO");
        } else {
        	FacesUtil.adicionarMsgInfo("NENHUM REGISTRO SELECIONADO");
        }
    	
    }
	   
}
