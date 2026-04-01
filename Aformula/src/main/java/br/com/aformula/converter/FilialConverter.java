package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.aformula.dao.FilialDAO;
import br.com.aformula.domain.Filial;

@FacesConverter("filialConverter")
public class FilialConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			FilialDAO filialDAO = new FilialDAO();
			Filial filial = filialDAO.buscarPorCodigo(id);
			
			return filial;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Filial filial = (Filial) objeto;
			
			Long id = filial.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
