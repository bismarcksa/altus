package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.aformula.dao.OcorrenciaDAO;
import br.com.aformula.domain.Ocorrencia;

@FacesConverter("ocorrenciaConverter")
public class OcorrenciaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();
			Ocorrencia ocorrencia = ocorrenciaDAO.buscarPorCodigo(id);
			
			return ocorrencia;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Ocorrencia ocorrencia = (Ocorrencia) objeto;
			
			Integer id = ocorrencia.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
