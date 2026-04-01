package br.com.aformula.util;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class FacesUtil {
	public static void adicionarMsgInfo(String mensagem) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
//		ExternalContext externalContext = facesContext.getExternalContext();
		
//		Flash flash = externalContext.getFlash();
//		flash.setKeepMessages(true);
		
		facesContext.addMessage(null, facesMessage);
	}
	
	public static void adicionarMsgErro(String mensagem) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
//		ExternalContext externalContext = facesContext.getExternalContext();
		
//		Flash flash = externalContext.getFlash();
//		flash.setKeepMessages(true);
		
		facesContext.addMessage(null, facesMessage);
	}
	
	public static void adicionarMsgAlerta(String mensagem) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
//		ExternalContext externalContext = facesContext.getExternalContext();
		
//		Flash flash = externalContext.getFlash();
//		flash.setKeepMessages(true);
		
		facesContext.addMessage(null, facesMessage);
	}
	
	public static String getParam(String nome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		ExternalContext externalContext = facesContext.getExternalContext();
		
		Map<String, String> parametros = externalContext.getRequestParameterMap();
		
		String valor = parametros.get(nome);
		
		return valor;
	}

}
