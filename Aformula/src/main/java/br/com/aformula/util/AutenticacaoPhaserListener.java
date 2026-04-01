package br.com.aformula.util;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.aformula.bean.AutenticacaoBean;
import br.com.aformula.domain.Funcionario;

@SuppressWarnings("serial")
public class AutenticacaoPhaserListener implements PhaseListener{

	@Override
	public void afterPhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		FacesContext facesContext = event.getFacesContext();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		
		String paginaAtual = uiViewRoot.getViewId();
		String paginaAtual2 = uiViewRoot.getViewId();
		
		boolean ehPaginaAutenticacao = paginaAtual.contains("autenticacao.xhtml");
		boolean ehPaginaPrimeiroAcesso = paginaAtual2.contains("primeiroAcesso.xhtml");

		if((!ehPaginaAutenticacao) && (!ehPaginaPrimeiroAcesso)) {
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> mapa = externalContext.getSessionMap();
			AutenticacaoBean autenticacaoBean = (AutenticacaoBean) mapa.get("autenticacaoBean");
			Funcionario funcionario = autenticacaoBean.getFuncionarioLogado();
				
			if(funcionario.getFuncao() == null) {
				FacesUtil.adicionarMsgErro("Funcionário não autenticado.");
					
				Application application = facesContext.getApplication();
				NavigationHandler navigationHandler = application.getNavigationHandler();
				navigationHandler.handleNavigation(facesContext, null, "/pages/autenticacao.xhtml?faces-redirect=true");
				
			}
				
		}
	}	

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		
		return PhaseId.RESTORE_VIEW;
	}

}
