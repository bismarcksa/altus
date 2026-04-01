package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.aformula.dao.CidadeDAO;
import br.com.aformula.domain.Cidade;

@FacesConverter("cidadeConverter")
public class CidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			Cidade cidade = cidadeDAO.buscarPorCodigo(id);
			
			return cidade;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Cidade cidade = (Cidade) objeto;
			
			Long id = cidade.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
