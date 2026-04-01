package br.com.aformula.bean;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import br.com.aformula.dao.ConfiguracoesDAO;
import br.com.aformula.dao.ConfiguracoesLogDAO;
import br.com.aformula.domain.Configuracoes;
import br.com.aformula.domain.ConfiguracoesLog;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@ViewScoped
public class ConfiguracoesBean {

	private Configuracoes configuracoesCadastro;
	private ConfiguracoesLog configuracoesLog;
	private List<Configuracoes> listaConfiguracoes;
	private String acao;
	private Long codigo;
	
	@ManagedProperty(value = "#{autenticacaoBean}")
	private AutenticacaoBean autenticacaoBean;
	
	public Configuracoes getConfiguracoesCadastro() {
		return configuracoesCadastro;
	}

	public void setConfiguracoesCadastro(Configuracoes configuracoesCadastro) {
		this.configuracoesCadastro = configuracoesCadastro;
	}

	public List<Configuracoes> getListaConfiguracoes() {
		return listaConfiguracoes;
	}

	public void setListaConfiguracoes(List<Configuracoes> listaConfiguracoes) {
		this.listaConfiguracoes = listaConfiguracoes;
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
	
	public ConfiguracoesLog getConfiguracoesLog() {
		return configuracoesLog;
	}

	public void setConfiguracoesLog(ConfiguracoesLog configuracoesLog) {
		this.configuracoesLog = configuracoesLog;
	}

	public void novo() {
		configuracoesCadastro = new Configuracoes();
	}
	
	public void carregarCadastro() {
		try {
			if (codigo != null) {
				ConfiguracoesDAO configuracoesDAO = new ConfiguracoesDAO();
				configuracoesCadastro = configuracoesDAO.buscarPorCodigo(codigo);
			}else {
				configuracoesCadastro = new Configuracoes();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao obter dados:" + ex.getMessage());
		}
	}
	
	public void editar() {
		try {
			ConfiguracoesDAO configuracoesDAO = new ConfiguracoesDAO();
			configuracoesDAO.editar(configuracoesCadastro);

			FacesUtil.adicionarMsgInfo("Configuracões salva com sucesso.");
			
			configuracoesLog = new ConfiguracoesLog();
			
			Date dataHoraAtual = new Date();
//			String dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataHoraAtual);
			
			configuracoesLog.setCodigo(3L);
			configuracoesLog.setUsuario(autenticacaoBean.getFuncionarioLogado().getCodigo());
			configuracoesLog.setData_alteracao(dataHoraAtual);
			configuracoesLog.setIp(InetAddress.getLocalHost().getHostAddress());
			configuracoesLog.setMaquina(InetAddress.getLocalHost().getHostName());
			configuracoesLog.setLimiteDiarioSache(configuracoesCadastro.getLimiteDiarioSache());
			configuracoesLog.setLimiteDiarioCapsula(configuracoesCadastro.getLimiteDiarioCapsula());
			configuracoesLog.setLimiteDiarioShake(configuracoesCadastro.getLimiteDiarioShake());
			configuracoesLog.setLimiteDiarioEnvase(configuracoesCadastro.getLimiteDiarioEnvase());
			configuracoesLog.setBloquearLancamentoProducao(configuracoesCadastro.getBloquearLancamentoProducao());
			configuracoesLog.setBloquearLancamentoProducao2(configuracoesCadastro.getBloquearLancamentoProducao2());
			configuracoesLog.setBloquearLancamentoProducao3(configuracoesCadastro.getBloquearLancamentoProducao3());
			configuracoesLog.setBloquearLancamentoProducao(configuracoesCadastro.getBloquearLancamentoProducao());
			
			configuracoesLog.setBloquearLancamentoProducaoItemSache(configuracoesCadastro.getBloquearLancamentoProducaoItemSache());
			configuracoesLog.setBloquearLancamentoProducaoItemCapsula(configuracoesCadastro.getBloquearLancamentoProducaoItemCapsula());
			configuracoesLog.setBloquearLancamentoProducaoItemShake(configuracoesCadastro.getBloquearLancamentoProducaoItemShake());
			configuracoesLog.setBloquearLancamentoProducaoItemEnvase(configuracoesCadastro.getBloquearLancamentoProducaoItemEnvase());
			
			configuracoesLog.setBloquearLancamentoProducao2ItemSache(configuracoesCadastro.getBloquearLancamentoProducao2ItemSache());
			configuracoesLog.setBloquearLancamentoProducao2ItemCapsula(configuracoesCadastro.getBloquearLancamentoProducao2ItemCapsula());
			configuracoesLog.setBloquearLancamentoProducao2ItemShake(configuracoesCadastro.getBloquearLancamentoProducao2ItemShake());
			configuracoesLog.setBloquearLancamentoProducao2ItemEnvase(configuracoesCadastro.getBloquearLancamentoProducao2ItemEnvase());
			
			configuracoesLog.setBloquearLancamentoProducao3ItemSache(configuracoesCadastro.getBloquearLancamentoProducao3ItemSache());
			configuracoesLog.setBloquearLancamentoProducao3ItemCapsula(configuracoesCadastro.getBloquearLancamentoProducao3ItemCapsula());
			configuracoesLog.setBloquearLancamentoProducao3ItemShake(configuracoesCadastro.getBloquearLancamentoProducao3ItemShake());
			configuracoesLog.setBloquearLancamentoProducao3ItemEnvase(configuracoesCadastro.getBloquearLancamentoProducao3ItemEnvase());
			configuracoesLog.setAnoBase(configuracoesCadastro.getAnoBase());
			
//			System.out.println("VER AQUI: " + configuracoesLog);
			
			ConfiguracoesLogDAO logConfiguracoesDAO = new ConfiguracoesLogDAO();
			logConfiguracoesDAO.salvar(configuracoesLog);		
			
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao editar configurações:" + ex.getMessage());
		}
		
	}

}
