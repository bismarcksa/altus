package br.com.aformula.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import br.com.aformula.dao.ProducaoDAO;
import br.com.aformula.dao.ProducaoPCPDAO;
import br.com.aformula.domain.Producao;
import br.com.aformula.domain.ProducaoPCP;
import br.com.aformula.util.FacesUtil;
import br.com.aformula.dao.ConfiguracoesDAO;
import br.com.aformula.dao.FilialDAO;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.domain.Configuracoes;
import br.com.aformula.domain.Filial;
import br.com.aformula.domain.Funcionario;


@ManagedBean
@ViewScoped
public class ProducaoBean{

	private Producao producaoCadastro;
	private ProducaoPCP producaoPCPCadastro;
	private List<Producao> listaProducoes;
	private List<Producao> listaProducoesConferencia;
	private List<Producao> listaProducoesUsuario;
	private List<Producao> listaProducoesFiltrados;
	
	private String acao;
	private Long codigo;
	private Integer quantidadePesquisa;
	
	private Integer dav_antes_alterar;
	private String identificador_antes_alterar;
	private String status_antes_alterar;
	
	private boolean capsulaDobrada;
	private boolean capsulaTriplicada;
	private boolean capsulaQuadruplicada;
	
	private List<Filial> listaFiliais;
	
	//VARIAVEL QUE DEFINE O DIA DE HOJE PARA NÃO PERMITIR GRAVAR UM DIA ANTERIOR AO DIA ATUAL
    private Date hoje;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;

	public Producao getProducaoCadastro() {	
		return producaoCadastro;
	}

	public void setProducaoCadastro(Producao producaoCadastro) {
		this.producaoCadastro = producaoCadastro;
	}

	public List<Producao> getListaProducoes() {
		return listaProducoes;
	}

	public void setListaProducoes(List<Producao> listaProducoes) {
		this.listaProducoes = listaProducoes;
	}
	
	public List<Producao> getListaProducoesUsuario() {
		return listaProducoesUsuario;
	}

	public void setListaProducoesUsuario(List<Producao> listaProducoesUsuario) {
		this.listaProducoesUsuario = listaProducoesUsuario;
	}

	public List<Producao> getListaProducoesConferencia() {
		return listaProducoesConferencia;
	}

	public void setListaProducoesConferencia(List<Producao> listaProducoesConferencia) {
		this.listaProducoesConferencia = listaProducoesConferencia;
	}

	public Date getHoje() {
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}
	
	public ProducaoBean() {
        hoje = new Date(); // Inicializa com a data atual
    }

	public ProducaoPCP getProducaoPCPCadastro() {
		return producaoPCPCadastro;
	}

	public void setProducaoPCPCadastro(ProducaoPCP producaoPCPCadastro) {
		this.producaoPCPCadastro = producaoPCPCadastro;
	}

	public List<Producao> getListaProducoesFiltrados() {
		if ((quantidadePesquisa == null) && (listaProducoesFiltrados != null)) {
			quantidadePesquisa = listaProducoesFiltrados.stream().mapToInt(Producao::getQuantidade).sum();
        }
		
		if (quantidadePesquisa == null) {
			return listaProducoes;
		}else {
			return listaProducoesFiltrados;
		}
	}

	public void setListaProducoesFiltrados(List<Producao> listaProducoesFiltrados) {
		this.listaProducoesFiltrados = listaProducoesFiltrados;
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

	public AutenticacaoBean getAutenticacaoBean() {
		return autenticacaoBean;
	}

	public void setAutenticacaoBean(AutenticacaoBean autenticacaoBean) {
		this.autenticacaoBean = autenticacaoBean;
	}

	public Integer getQuantidadePesquisa() {
		if (listaProducoesFiltrados != null) {
			quantidadePesquisa = listaProducoesFiltrados.stream().mapToInt(Producao::getQuantidade).sum();
        }
		return quantidadePesquisa;
	}

	public void setQuantidadePesquisa(Integer quantidadePesquisa) {
		this.quantidadePesquisa = quantidadePesquisa;
	}

	public boolean isCapsulaDobrada() {
		return capsulaDobrada;
	}

	public void setCapsulaDobrada(boolean capsulaDobrada) {
		this.capsulaDobrada = capsulaDobrada;
	}
	
	public boolean isCapsulaTriplicada() {
		return capsulaTriplicada;
	}

	public void setCapsulaTriplicada(boolean capsulaTriplicada) {
		this.capsulaTriplicada = capsulaTriplicada;
	}

	public boolean isCapsulaQuadruplicada() {
		return capsulaQuadruplicada;
	}

	public void setCapsulaQuadruplicada(boolean capsulaQuadruplicada) {
		this.capsulaQuadruplicada = capsulaQuadruplicada;
	}

	public List<Filial> getListaFiliais() {
		return listaFiliais;
	}

	public void setListaFiliais(List<Filial> listaFiliais) {
		this.listaFiliais = listaFiliais;
	}

	public void novo() {
		producaoCadastro = new Producao();
		producaoPCPCadastro = new ProducaoPCP();
	}
	
	public void salvar() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			ProducaoPCPDAO producaoPCPDAO = new ProducaoPCPDAO();
			
			ConfiguracoesDAO configuracoesDAO = new ConfiguracoesDAO();
			Configuracoes configuracoes = configuracoesDAO.buscarPorCodigo(1L);

//			if (producaoCadastro == null) {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());
			
//			producaoCadastro = new Producao();
			producaoCadastro.setData_solicitacao(new Date());
			producaoCadastro.setStatus("PENDENTE");
			producaoCadastro.setFuncionario(funcionario);
//			}
			
			Long  limiteDiario = 0L;		
			limiteDiario = producaoDAO.checarLimeteDiario(producaoCadastro.getData_dispensacao(), producaoCadastro.getTipo());	
			Long quantidade;
			
			if (producaoCadastro.getQuantidade() <= 0) {
				FacesUtil.adicionarMsgErro("QUANTIDADE DEVE SER MAIOR QUE ZERO");
				return;
			}
			
			//VALIDA AQUI SE A DATA DE DISPENSAÇÃO NÃO ESTÁ CADASTRADA NO CONFIGURAÇÕES
			if ((!producaoCadastro.getData_dispensacao().equals(configuracoes.getBloquearLancamentoProducao())) && (!producaoCadastro.getData_dispensacao().equals(configuracoes.getBloquearLancamentoProducao2())) && (!producaoCadastro.getData_dispensacao().equals(configuracoes.getBloquearLancamentoProducao3()))) {
				if (producaoCadastro.getTipo().equals("SACHÊ")) {
					if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioSache()) {
						FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
					}else {							
						quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
						if (quantidade == 0) {
							producaoDAO.salvar(producaoCadastro);
								
							producaoPCPCadastro.setDav(producaoCadastro.getDav());
							producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
							producaoPCPCadastro.setData_lancamento(new Date());
							producaoPCPCadastro.setStatus("PENDENTE");
							producaoPCPCadastro.setFuncionario(funcionario);
								
							producaoPCPDAO.salvar(producaoPCPCadastro);
								
							FacesUtil.adicionarMsgInfo("SACHÊ salvo com sucesso.");
							FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
						}else {
							FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
						}
					}
				}else if (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA")) {
					if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioCapsula()) {			
						FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
					}else {
						quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
						if (quantidade == 0) {
							producaoDAO.salvar(producaoCadastro);	
								
							producaoPCPCadastro.setDav(producaoCadastro.getDav());
							producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
							producaoPCPCadastro.setData_lancamento(new Date());
							producaoPCPCadastro.setStatus("PENDENTE");
							producaoPCPCadastro.setFuncionario(funcionario);
								
							producaoPCPDAO.salvar(producaoPCPCadastro);
								
							FacesUtil.adicionarMsgInfo("CÁPSULA ÓLEOSA salva com sucesso.");
							FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
						}else {
							FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
						}
					}		
				}else if (producaoCadastro.getTipo().equals("SHAKE")) {
					if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioShake()) {			
						FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
					}else {
						quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
						if (quantidade == 0) {
							producaoDAO.salvar(producaoCadastro);
								
							producaoPCPCadastro.setDav(producaoCadastro.getDav());
							producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
							producaoPCPCadastro.setData_lancamento(new Date());
							producaoPCPCadastro.setStatus("PENDENTE");
							producaoPCPCadastro.setFuncionario(funcionario);
								
							producaoPCPDAO.salvar(producaoPCPCadastro);
								
							FacesUtil.adicionarMsgInfo("SHAKE salvo com sucesso.");
							FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
						}else {
							FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
						}
					}		
				}else {
					if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioEnvase()) {			
						FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
					}else {
						quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
						if (quantidade == 0) {
							producaoDAO.salvar(producaoCadastro);	
								
							producaoPCPCadastro.setDav(producaoCadastro.getDav());
							producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
							producaoPCPCadastro.setData_lancamento(new Date());
							producaoPCPCadastro.setStatus("PENDENTE");
							producaoPCPCadastro.setFuncionario(funcionario);
								
							producaoPCPDAO.salvar(producaoPCPCadastro);
								
							FacesUtil.adicionarMsgInfo("ENVASE salvo com sucesso.");
							FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
						}else {
							FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
						}							
					}		
				}				
			}else { // VALIDA SE A DATA ESTÁ CADASTRA NO CONFIGURAÇÕES
				if ((producaoCadastro.getData_dispensacao().equals(configuracoes.getBloquearLancamentoProducao()))) {
					if((configuracoes.getBloquearLancamentoProducaoItemSache() == true) && (producaoCadastro.getTipo().equals("SACHÊ"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducaoItemCapsula() == true) && (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA"))) {
						FacesUtil.adicionarMsgAlerta("DATA DBLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducaoItemShake() == true) && (producaoCadastro.getTipo().equals("SHAKE"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducaoItemEnvase() == true) && (producaoCadastro.getTipo().equals("ENVASE"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else {
						if (producaoCadastro.getTipo().equals("SACHÊ")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioSache()) {
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("SACHÊ salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}
						}else if (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioCapsula()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("CÁPSULA ÓLEOSA salva com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}else if (producaoCadastro.getTipo().equals("SHAKE")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioShake()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("SHAKE salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}else {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioEnvase()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("ENVASE salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}
					}
				}else if ((producaoCadastro.getData_dispensacao().equals(configuracoes.getBloquearLancamentoProducao2()))) {
					if((configuracoes.getBloquearLancamentoProducao2ItemSache() == true) && (producaoCadastro.getTipo().equals("SACHÊ"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducao2ItemCapsula() == true) && (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducao2ItemShake() == true) && (producaoCadastro.getTipo().equals("SHAKE"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducao2ItemEnvase() == true) && (producaoCadastro.getTipo().equals("ENVASE"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else {
						if (producaoCadastro.getTipo().equals("SACHÊ")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioSache()) {
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("SACHÊ salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}
						}else if (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioCapsula()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("CÁPSULA ÓLEOSA salva com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}else if (producaoCadastro.getTipo().equals("SHAKE")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioShake()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("SHAKE salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}else {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioEnvase()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("ENVASE salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}
					}
				}else if ((producaoCadastro.getData_dispensacao().equals(configuracoes.getBloquearLancamentoProducao3()))) {
					if((configuracoes.getBloquearLancamentoProducao3ItemSache() == true) && (producaoCadastro.getTipo().equals("SACHÊ"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducao3ItemCapsula() == true) && (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducao3ItemShake() == true) && (producaoCadastro.getTipo().equals("SHAKE"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else if((configuracoes.getBloquearLancamentoProducao3ItemEnvase() == true) && (producaoCadastro.getTipo().equals("ENVASE"))) {
						FacesUtil.adicionarMsgAlerta("DATA BLOQUEADA PARA INCLUSÃO DE: " + producaoCadastro.getTipo());
					}else {
						if (producaoCadastro.getTipo().equals("SACHÊ")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioSache()) {
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("SACHÊ salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}
						}else if (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioCapsula()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("CÁPSULA ÓLEOSA salva com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}else if (producaoCadastro.getTipo().equals("SHAKE")) {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioShake()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("SHAKE salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}else {
							if (limiteDiario + producaoCadastro.getQuantidade() > configuracoes.getLimiteDiarioEnvase()) {			
								FacesUtil.adicionarMsgAlerta("LIMITE DE " + getProducaoCadastro().getTipo().toString() + " EXCEDIDO PARA O DIA " + new SimpleDateFormat("dd/MM/yyyy").format(producaoCadastro.getData_dispensacao()));
							}else {
								quantidade = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());
								if (quantidade == 0) {
									producaoDAO.salvar(producaoCadastro);	
									
									producaoPCPCadastro.setDav(producaoCadastro.getDav());
									producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador());
									producaoPCPCadastro.setData_lancamento(new Date());
									producaoPCPCadastro.setStatus("PENDENTE");
									producaoPCPCadastro.setFuncionario(funcionario);
									
									producaoPCPDAO.salvar(producaoPCPCadastro);
									
									FacesUtil.adicionarMsgInfo("ENVASE salvo com sucesso.");
									FacesContext.getCurrentInstance().getExternalContext().redirect("producaoPesquisa.xhtml");
								}else {
									FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
								}
							}		
						}
					}
				}				
			}
			
			producaoCadastro = new Producao();
			producaoPCPCadastro = new ProducaoPCP();
		}catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().toString().substring(42, 55).equals("duplicate key")) {
				FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV JÁ CADASTRADO.");
			}else {
				FacesUtil.adicionarMsgErro("Erro ao salvar: " + ex.getMessage());
			}
		}
	}
	
	//MÉTODO PARA CARREGAR TODAS AS SOLICTAÇÕES, PÁGINA DE COORDENAÇÃO PESQUISA
	public void carregarPesquisa() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			listaProducoes = producaoDAO.listar();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção: " + ex.getMessage());
		}
	}
	
	//MÉTODO PARA CARREGAR TODAS AS SOLICTAÇÕES PENDENTES PARA CONFERÊNCIA, PÁGINA DE COORDENAÇÃO CONFERENCIA
	public void carregarPesquisaConferencia() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			listaProducoesConferencia = producaoDAO.listarConferencia();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção: " + ex.getMessage());
		}
	}
	
	//MÉTODO PARA CARREGAR APENAS AS SOLICTAÇÕES DO USUÁRIO LOGADO, PÁGINA DE SOLICITAR PRODUÇÃO
	public void carregarPesquisaUsuario() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			listaProducoesUsuario = producaoDAO.listarProducaoUsuario(autenticacaoBean.getFuncionarioLogado().getCodigo());
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção: " + ex.getMessage());
		}
	}
	
	//METODO PARA CARREGAR TODAS AS PRODUÇÕES PENDENTES E EM PRODUÇÃO
	public void carregarPesquisaPendente() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			listaProducoes = producaoDAO.listarPendenteProducao();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção: " + ex.getMessage());
		}
	}
	
	public void carregarCadastro() {
		try {			
			if (codigo != null) {	
				ProducaoDAO producaoDAO = new ProducaoDAO();
				producaoCadastro = producaoDAO.buscarPorCodigo(codigo);
				
				//carrega valor iniciar de capsula dobrada ou não e outras variaveis
				if (!(producaoCadastro == null)) {
					dav_antes_alterar = producaoCadastro.getDav();
					identificador_antes_alterar = producaoCadastro.getIdentificador();
					status_antes_alterar = producaoCadastro.getStatus();
					
					capsulaDobrada = producaoCadastro.isCapsula_dobrada();
					capsulaTriplicada = producaoCadastro.isCapsula_triplicada();
					capsulaQuadruplicada = producaoCadastro.isCapsula_quadruplicada();
				}	
			}else {
				producaoCadastro = new Producao();
				producaoPCPCadastro = new ProducaoPCP();
			} 
			
			FilialDAO filialDAO = new FilialDAO();
			listaFiliais = filialDAO.listarAtivo();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados da produção: " + ex.getMessage());
		}
	}
	
	public void excluir() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			producaoDAO.excluir(producaoCadastro);	
			
			ProducaoPCPDAO producaoPCPDAO = new ProducaoPCPDAO();			
			producaoPCPCadastro = producaoPCPDAO.buscarPorCodigo(producaoCadastro.getFuncionario().getCodigo(), producaoCadastro.getDav(), producaoCadastro.getIdentificador());		
			
			if (producaoPCPDAO != null) {
				producaoPCPDAO.excluir(producaoPCPCadastro);
			}
			
			FacesUtil.adicionarMsgInfo("Produção removida com sucesso.");
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao remover produção: " + ex.getMessage());
		}	
	}
	
	public void editar() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			ProducaoPCPDAO producaoPCPDAO = new ProducaoPCPDAO();		
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(autenticacaoBean.getFuncionarioLogado().getCodigo());
			
			producaoPCPCadastro = new ProducaoPCP();
			
			Long quantidadeEditar;	
			
			//DATA DE HOJE
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
			Date dataHoje = new Date();
			String dataFormatada = dateFormat.format(dataHoje);
			Date date = dateFormat.parse(dataFormatada);		
			
			if((!producaoCadastro.getData_dispensacao().equals(dataHoje)) && (producaoCadastro.getStatus().equals("DISPENSADO"))) {
				producaoCadastro.setData_dispensacao2(date);
			}
			
			if((producaoCadastro.getStatus().equals("PENDENTE")) || (producaoCadastro.getStatus().equals("PRODUÇÃO"))) {
				producaoCadastro.setData_dispensacao2(null);
			}
					
			if (producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA")) {
				//VERIFICA SE FOI ALTERADO O CAMPO DE CAPS DOBRADA, SE NÃO FOI ALTERADA, NEM ENTRA AQUI		
				if ((capsulaDobrada != producaoCadastro.isCapsula_dobrada())){
					if(capsulaDobrada == false) {
						producaoCadastro.setQuantidade(producaoCadastro.getQuantidade()*2);
					}else {
						producaoCadastro.setQuantidade(producaoCadastro.getQuantidade()/2);
					}	
				}
				
				//VERIFICA SE FOI ALTERADO O CAMPO DE CAPS TRIPLICADA, SE NÃO FOI ALTERADA, NEM ENTRA AQUI		
				if ((capsulaTriplicada != producaoCadastro.isCapsula_triplicada())){
					if(capsulaTriplicada == false) {
						producaoCadastro.setQuantidade(producaoCadastro.getQuantidade()*3);
					}else {
						producaoCadastro.setQuantidade(producaoCadastro.getQuantidade()/3);
					}	
				}
				
				//VERIFICA SE FOI ALTERADO O CAMPO DE CAPS QUADRUPLICADA, SE NÃO FOI ALTERADA, NEM ENTRA AQUI		
				if ((capsulaQuadruplicada != producaoCadastro.isCapsula_quadruplicada())){
					if(capsulaQuadruplicada == false) {
						producaoCadastro.setQuantidade(producaoCadastro.getQuantidade()*4);
					}else {
						producaoCadastro.setQuantidade(producaoCadastro.getQuantidade()/4);
					}	
				}
			}
			
			if ((producaoCadastro.isCapsula_dobrada() == true) && !(producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA"))){				
				FacesUtil.adicionarMsgAlerta("CÁPS. DOBRADA não é compatível com o TIPO escolhido.");
			}else if ((producaoCadastro.isCapsula_triplicada() == true) && !(producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA"))){				
				FacesUtil.adicionarMsgAlerta("CÁPS. TRIPLICADA não é compatível com o TIPO escolhido.");	
			}else if ((producaoCadastro.isCapsula_quadruplicada() == true) && !(producaoCadastro.getTipo().equals("CÁPSULA ÓLEOSA"))){				
				FacesUtil.adicionarMsgAlerta("CÁPS. QUADRUPLICADA não é compatível com o TIPO escolhido.");
			}else if ((producaoCadastro.isCapsula_dobrada() == true) && (producaoCadastro.isCapsula_triplicada() == true)){				
				FacesUtil.adicionarMsgAlerta("CÁPS. DOBRADA E TRIPLICADA NÃO PODEM ESTAR MARCADAS.");
			}else if ((producaoCadastro.isCapsula_dobrada() == true) && (producaoCadastro.isCapsula_quadruplicada() == true)){				
				FacesUtil.adicionarMsgAlerta("CÁPS. DOBRADA E QUADRUPLICADA NÃO PODEM ESTAR MARCADAS.");
			}else if ((producaoCadastro.isCapsula_triplicada() == true) && (producaoCadastro.isCapsula_quadruplicada() == true)){				
				FacesUtil.adicionarMsgAlerta("CÁPS. TRIPLICADA E QUADRUPLICADA NÃO PODEM ESTAR MARCADAS.");
			}else {
				quantidadeEditar = producaoDAO.checarDav(producaoCadastro.getDav(), producaoCadastro.getIdentificador().toUpperCase());							
				if (quantidadeEditar == 0) {
					producaoDAO.editar(producaoCadastro);	
					// NO CASO DE ATENDENTE GERA PCP QUANDO GRAVA OU QUANDO ALTERA, MAS O AOTERAR É SÓ PRA EDITAR
					if(!producaoCadastro.getStatus().toString().equals(status_antes_alterar)) {
						producaoPCPCadastro.setStatus(producaoCadastro.getStatus().toString());
						producaoPCPCadastro.setData_lancamento(new Date());
						producaoPCPCadastro.setFuncionario(funcionario);
						producaoPCPCadastro.setDav(producaoCadastro.getDav());
						producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador().toUpperCase());
						producaoPCPDAO.salvar(producaoPCPCadastro);
					}else {
						producaoPCPCadastro = producaoPCPDAO.buscarPorCodigo(producaoCadastro.getFuncionario().getCodigo(), dav_antes_alterar, identificador_antes_alterar);
						
						producaoPCPCadastro.setDav(producaoCadastro.getDav());
						producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador().toUpperCase());					
						
						producaoPCPDAO.editar(producaoPCPCadastro);
					}
					
					FacesUtil.adicionarMsgInfo("Produção alterada com sucesso.");
				}else if ((quantidadeEditar == 1) && (autenticacaoBean.getFuncionarioLogado().getFuncao().equals("COORDENADOR DE LABORATÓRIO") || autenticacaoBean.getFuncionarioLogado().getLogin().equals("WILLIANE"))) {				
					producaoDAO.editar(producaoCadastro);

					// SÓ VAI GERAR PCP SE O STATUS FOI ALTERADO NO CASO DE NÃO SER ATENDENTE
					if(!producaoCadastro.getStatus().toString().equals(status_antes_alterar)) {
						producaoPCPCadastro.setStatus(producaoCadastro.getStatus().toString());
						producaoPCPCadastro.setData_lancamento(new Date());
						producaoPCPCadastro.setFuncionario(funcionario);
						producaoPCPCadastro.setDav(producaoCadastro.getDav());
						producaoPCPCadastro.setIdentificador(producaoCadastro.getIdentificador().toUpperCase());
						producaoPCPDAO.salvar(producaoPCPCadastro);
					}

					FacesUtil.adicionarMsgInfo("Produção alterada com sucesso.");
				}else{
					FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV e IDENTIFICADOR já cadastrados!");
				}				
			}	
		}catch (Exception ex) {
			ex.printStackTrace();
			if (ex.getCause().toString().substring(42, 55).equals("duplicate key")) {
				FacesUtil.adicionarMsgErro("SOLICITAÇÃO NÃO REALIZADA - DAV JÁ CADASTRADO.");
			}else {
				FacesUtil.adicionarMsgErro("Erro ao editar: " + ex.getMessage());
			}
		}
	}
	
	//METÓDO PARA CARREGAR TODAS AS SOLICTAÇÕES AGREGADAS DE PENDENTES E EM PRODUÇÃO PARA O FARMACÊUTICO
	public void carregarPendenteAgregadoData() {
		try {
			ProducaoDAO producaoDAO = new ProducaoDAO();
			listaProducoes = producaoDAO.listarPendenteAgregadoData();
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao listar produção: " + ex.getMessage());
		}
	}
	
	//TOTAL DA QUANTIDADE DE ITENS FILTRADOS
	public void totalPesquisado() {
		try {	
			if (quantidadePesquisa != null) {
				FacesUtil.adicionarMsgInfo("TOTAL: " + quantidadePesquisa.toString());
				quantidadePesquisa = null;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
