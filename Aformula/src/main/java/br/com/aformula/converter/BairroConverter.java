package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.aformula.dao.BairroDAO;
import br.com.aformula.domain.Bairro;

@FacesConverter("bairroConverter")
public class BairroConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			BairroDAO bairroDAO = new BairroDAO();
			Bairro bairro = bairroDAO.buscarPorCodigo(id);
			
			return bairro;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Bairro bairro = (Bairro) objeto;
			
			Integer id = bairro.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
