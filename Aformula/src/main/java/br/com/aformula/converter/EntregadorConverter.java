package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.aformula.dao.EntregadorDAO;
import br.com.aformula.domain.Entregador;

@FacesConverter("entregadorConverter")
public class EntregadorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			EntregadorDAO entregadorDAO = new EntregadorDAO();
			Entregador entregador = entregadorDAO.buscarPorCodigo(id);
			
			return entregador;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Entregador entregador = (Entregador) objeto;
			
			Long id = entregador.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
