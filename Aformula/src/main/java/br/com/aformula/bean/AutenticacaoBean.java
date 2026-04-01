package br.com.aformula.bean;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;
import br.com.aformula.dao.FechamentoEntregaDAO;
import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.dao.MensagemContatoDAO;
import br.com.aformula.domain.Funcionario;
import br.com.aformula.util.FacesUtil;

@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Funcionario funcionarioLogado;

	public Funcionario getFuncionarioLogado() {
		if(funcionarioLogado == null) {
			funcionarioLogado = new Funcionario();
		}
		return funcionarioLogado;
	}

	public void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}

	public String autenticar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioLogado = funcionarioDAO.autenticar(funcionarioLogado.getLogin(), DigestUtils.md5Hex(funcionarioLogado.getSenha()));
					
			if (funcionarioLogado == null) {
				FacesUtil.adicionarMsgErro("Login ou Senha incorretos.");
				return null;
			}else if (funcionarioLogado.isStatus() == false) {
				FacesUtil.adicionarMsgErro("Usuário inativo.");
				return null;
			}
			else {
//				System.out.println("PASSA AQUI");
				SessaoListener.adicionarUsuario(funcionarioLogado.getLogin());
//				System.out.println("SAI AQUI");
				FacesUtil.adicionarMsgInfo("Funcionário autenticado com sucesso.");
				return "/pages/principal.xhtml?faces-redirect=true";
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			FacesUtil.adicionarMsgErro("Erro ao autenticar no sistema:" + ex.getMessage());
			return null;
		}	
	}
	
	public String sair() {	
		if (funcionarioLogado != null) {
	        SessaoListener.removerUsuario(funcionarioLogado.getLogin());
	    }
		
	    funcionarioLogado = null;
	    return "/pages/autenticacao.xhtml?faces-redirect=true";
	}
	
	public void sairPorFechamento() {
	    SessaoListener.removerUsuario(funcionarioLogado.getLogin());
	    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public void exibeNotificacao() {
		Long totalNaoLidas;
		MensagemContatoDAO mensagemContatoDAO = new MensagemContatoDAO();
		totalNaoLidas = mensagemContatoDAO.somaMensagensNaoLida(funcionarioLogado.getCodigo());
		
		if (totalNaoLidas != null && totalNaoLidas > 0) {
			FacesUtil.adicionarMsgAlerta("EXISTE(M) MENSAGEM(NS) NÃO LIDA(S)");
		}
		
	    if (funcionarioLogado.getFuncao().equals("FINANCEIRO")) {
	    	String anoMes;
	    	
	        //OBTENDO A DATA ATUAL DE HOJE - FORMATO 2025-05-01
	        LocalDate dataHoje = LocalDate.now();

	        // Configurar o dia como 1, mantendo o mês e o ano atuais
	        LocalDate dataAtual = LocalDate.of(dataHoje.getYear(), dataHoje.getMonth(), 1);
	        
	        //PEGANDO A DATA DO FECHAMENTO EM ABERTO
	        FechamentoEntregaDAO fechamentoEntregaDAO = new FechamentoEntregaDAO();	        
	        anoMes = fechamentoEntregaDAO.verificaFechamentoAberto();
	        
	        if (anoMes != null && !anoMes.isEmpty()) {
	            try {
	                //CONVERTENDO anoMes (ex: "02/2025") PARA LocalDate
	                LocalDate dataFechamentoAberto = LocalDate.parse("01/" + anoMes, DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "BR")));
	                
	                // COMPARANDO AS DATAS
	                if (dataAtual.isAfter(dataFechamentoAberto)) {
	                    //ENTRA AQUI SE A DATA ATUAL (MES E ANO) É MAIOR QUE A DO MÊS DE FECHAMENTO EM ABERTO
	                    FacesUtil.adicionarMsgAlerta("EXISTE FECHAMENTO DE MÊS PENDENTE A SER REALIZADO - " + anoMes);
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	                FacesUtil.adicionarMsgErro("Erro ao comparar os meses: " + e.getMessage());
	            }
	        } else {
	            System.out.println("anoMes não está válido.");
	        } 
	    }
	    
	    
	}
	
	public void atualizarHeartbeat() {
	    if (funcionarioLogado != null && funcionarioLogado.getLogin() != null) {
//	        System.out.println("Heartbeat recebido do usuário: " + funcionarioLogado.getLogin());
	        UsuariosOnlineManager.adicionarOuAtualizarUsuario(funcionarioLogado.getLogin());
	    } else {
	        System.out.println("Heartbeat sem usuário logado");
	    }
	}

}
